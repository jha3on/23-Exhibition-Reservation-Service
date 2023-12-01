package app.core.rds.repository.place.query;

import app.core.rds.entity.place.Place;
import app.core.rds.entity.place.PlaceLike;
import app.core.rds.entity.place.QPlace;
import app.core.rds.entity.place.QPlaceLike;
import app.core.rds.entity.product.QProduct;
import app.core.rds.entity.user.QUser;
import app.share.app.utility.JpaUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlaceRepositoryQueryLogic implements PlaceRepositoryQuery {
    private final JPAQueryFactory query;
    private final QUser user = QUser.user;
    private final QPlace place = QPlace.place;
    private final QProduct product = QProduct.product;
    private final QPlaceLike placeLike = QPlaceLike.placeLike;

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public Optional<Place> getByPlaceId(Long placeId) {
        var placeGet = query.selectFrom(place)
                .where(place.id.eq(placeId))
                .fetchOne();

        return Optional.ofNullable(placeGet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Place> getByProductId(Long productId) {
        var placeGet = query.selectFrom(place)
                // .join(product.place, place)
                .join(product).on(product.place.id.eq(place.id))
                .where(product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(placeGet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAllBy() {
        var placeGets = query.selectFrom(place)
                .orderBy(place.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(placeGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAllByProductIds(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) return new ArrayList<>();

        var placeGets = query.selectFrom(place)
                // .join(product.place, place)
                .join(product).on(product.place.id.eq(place.id))
                .where(product.id.in(productIds))
                .orderBy(place.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(placeGets);
    }


    @Override
    @Transactional(readOnly = true)
    public Boolean existsBy(Long userId, Long placeId) {
        var placeLikeGet = query.selectOne().from(placeLike)
                .join(placeLike.user, user)
                .join(placeLike.place, place)
                .where(user.id.eq(userId),
                        place.id.eq(placeId))
                .fetchFirst();

        return placeLikeGet != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceLike> getBy(Long userId, Long placeId) {
        var placeLikeGet = query.selectFrom(placeLike)
                .join(placeLike.user, user)
                .join(placeLike.place, place)
                .where(user.id.eq(userId),
                        place.id.eq(placeId))
                .fetchOne();

        return Optional.ofNullable(placeLikeGet);
    }
}