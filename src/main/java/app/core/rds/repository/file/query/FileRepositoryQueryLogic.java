package app.core.rds.repository.file.query;

import app.core.rds.entity.file.File;
import app.core.rds.entity.file.QFile;
import app.core.rds.entity.place.QPlace;
import app.core.rds.entity.product.QProduct;
import app.core.rds.entity.promotion.QPromotion;
import app.share.app.utility.JpaUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileRepositoryQueryLogic implements FileRepositoryQuery {
    private final JPAQueryFactory query;
    private final QFile file = QFile.file;
    private final QPlace place = QPlace.place;
    private final QProduct product = QProduct.product;
    private final QPromotion promotion = QPromotion.promotion;

    @Override
    @Transactional(readOnly = true)
    public List<File> getAllByPlaceId(Long placeId) {
        var fileGets = query.selectFrom(file)
                // .join(place).on(place.id.eq(file.place.id))
                .join(file.place, place)
                .where(file.place.id.eq(placeId))
                .fetch();

        return JpaUtils.getOrEmpty(fileGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<File> getAllByPlaceIds(List<Long> placeIds) {
        if (CollectionUtils.isEmpty(placeIds)) return new ArrayList<>();

        var fileGets = query.selectFrom(file)
                // .join(place).on(place.id.eq(file.place.id))
                .join(file.place, place)
                .where(file.place.id.in(placeIds))
                .fetch();

        return JpaUtils.getOrEmpty(fileGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<File> getAllByProductId(Long productId) {
        var fileGets = query.selectFrom(file)
                .join(file.product, product)
                .where(file.product.id.eq(productId))
                .fetch();

        return JpaUtils.getOrEmpty(fileGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<File> getAllByProductIds(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) return new ArrayList<>();

        var fileGets = query.selectFrom(file)
                .join(file.product, product)
                .where(file.product.id.in(productIds))
                .fetch();

        return JpaUtils.getOrEmpty(fileGets);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<File> getAllByPromotionId(Long promotionId) {
//        var fileGets = query.selectFrom(file)
//                .join(file.promotion, promotion)
//                .where(file.promotion.id.eq(promotionId))
//                .fetch();
//
//        return JpaUtils.getOrEmpty(fileGets);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<File> getAllByPromotionIds(List<Long> promotionIds) {
//        if (CollectionUtils.isEmpty(promotionIds)) return new ArrayList<>();
//
//        var fileGets = query.selectFrom(file)
//                .join(file.promotion, promotion)
//                .where(file.promotion.id.in(promotionIds))
//                .fetch();
//
//        return JpaUtils.getOrEmpty(fileGets);
//    }
}