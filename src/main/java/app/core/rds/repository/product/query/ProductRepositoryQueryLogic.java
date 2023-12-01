package app.core.rds.repository.product.query;

import app.core.rds.entity.place.QPlace;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.entity.product.*;
import app.core.rds.entity.user.QUser;
import app.core.rds.entity.product.type.ProductGroupType;
import app.share.app.utility.JpaUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryQueryLogic implements ProductRepositoryQuery {
    private final JPAQueryFactory query;
    private final QUser user = QUser.user;
    private final QPlace place = QPlace.place;
    private final QProduct product = QProduct.product;
    private final QProductOption productOption = QProductOption.productOption;
    private final QProductLike productLike = QProductLike.productLike;

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getByProductId(Long productId) {
        var productGet = query.selectFrom(product)
                .where(product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(productGet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllByProductName(String productName) {
        var productGets = query.selectFrom(product)
                .join(product.place, place)
                .where(product.name.contains(productName))
                .orderBy(product.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(productGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllByFilterTypes(PlaceGroupType placeGroupType, PlaceGroupSubtype placeGroupSubtype, ProductGroupType productGroupType) {
        var productGets = query.selectFrom(product)
                .join(product.place, place)
                .where(eqPlaceGroupType(placeGroupType),
                        eqPlaceGroupSubtype(placeGroupSubtype),
                        eqProductGroupType(productGroupType))
                .orderBy(product.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(productGets);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOption> getByProductOptionId(Long productOptionId) {
        var productPriceGet = query.selectFrom(productOption)
                .join(productOption.product, product)
                .where(productOption.id.eq(productOptionId))
                .fetchOne();

        return Optional.ofNullable(productPriceGet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOption> getAllByProductId(Long productId) {
        var productPriceGets = query.selectFrom(productOption)
                .join(productOption.product, product)
                .where(productOption.product.id.eq(productId))
                .fetch();

        return JpaUtils.getOrEmpty(productPriceGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOption> getAllByProductIds(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) return new ArrayList<>();

        var productPriceGets = query.selectFrom(productOption)
                .join(productOption.product, product)
                .where(productOption.product.id.in(productIds))
                .fetch();

        return JpaUtils.getOrEmpty(productPriceGets);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsBy(Long userId, Long productId) {
        var productLikeGet = query.selectOne().from(productLike)
                .join(productLike.user, user)
                .join(productLike.product, product)
                .where(user.id.eq(userId),
                        product.id.eq(productId))
                .fetchFirst();

        return productLikeGet != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductLike> getBy(Long userId, Long productId) {
        var productLikeGet = query.selectFrom(productLike)
                .join(productLike.user, user)
                .join(productLike.product, product)
                .where(user.id.eq(userId),
                        product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(productLikeGet);
    }

    private BooleanExpression eqPlaceGroupType(PlaceGroupType placeGroupType) {
        if (Objects.isNull(placeGroupType)) return null;
        return place.groupType.eq(placeGroupType);
    }

    private BooleanExpression eqPlaceGroupSubtype(PlaceGroupSubtype placeGroupSubtype) {
        if (Objects.isNull(placeGroupSubtype)) return null;
        return place.groupSubtype.eq(placeGroupSubtype);
    }

    private BooleanExpression eqProductGroupType(ProductGroupType productGroupType) {
        if (Objects.isNull(productGroupType)) return null;
        return product.groupType.eq(productGroupType);
    }
}