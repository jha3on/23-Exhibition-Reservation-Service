package app.core.rds.repository.reservation.query;

import app.core.rds.entity.product.QProduct;
import app.core.rds.entity.reservation.QReservation;
import app.core.rds.entity.reservation.QReservationOption;
import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.ReservationOption;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import app.core.rds.entity.user.QUser;
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
public class ReservationRepositoryQueryLogic implements ReservationRepositoryQuery {
    private final JPAQueryFactory query;
    private final QUser user = QUser.user;
    private final QProduct product = QProduct.product;
    private final QReservation reservation = QReservation.reservation;
    private final QReservationOption reservationOption = QReservationOption.reservationOption;

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getByUserProduct(Long userId, Long productId) {
        var reservationGet = query.selectFrom(reservation)
                .join(reservation.user, user)
                .join(reservation.product, product)
                .where(user.id.eq(userId),
                        product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(reservationGet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getByUserReservation(Long userId, Long reservationId) {
        var reservationGet = query.selectFrom(reservation)
                .join(reservation.user, user)
                .join(reservation.product, product)
                .where(user.id.eq(userId),
                        reservation.id.eq(reservationId))
                .fetchOne();

        return Optional.ofNullable(reservationGet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllBy(Long userId) {
        var reservationGets = query.selectFrom(reservation)
                .join(reservation.user, user)
                .where(user.id.eq(userId))
                .fetch();

        return JpaUtils.getOrEmpty(reservationGets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllBy(Long userId, ReservationStatusType reservationStatusType) {
        var reservationGets = query.selectFrom(reservation)
                .join(reservation.user, user)
                .where(user.id.eq(userId),
                        eqReservationStatusType(reservationStatusType))
                .fetch();

        return JpaUtils.getOrEmpty(reservationGets);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ReservationOption> getAllBy(List<Long> reservationIds) {
        if (CollectionUtils.isEmpty(reservationIds)) return new ArrayList<>();

        var reservationOptionGets = query.selectFrom(reservationOption)
                .join(reservationOption.reservation, reservation)
                .where(reservation.id.in(reservationIds))
                .orderBy(reservation.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(reservationOptionGets);
    }

    private BooleanExpression eqReservationStatusType(ReservationStatusType reservationStatusType) {
        if (Objects.isNull(reservationStatusType)) return null;
        return reservation.statusType.eq(reservationStatusType);
    }
}