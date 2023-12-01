package app.core.rds.service.reservation;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import app.core.rds.entity.user.User;
import app.core.rds.repository.reservation.ReservationRepository;
import app.core.rds.repository.reservation.query.ReservationRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationRepositoryQuery reservationRepositoryQuery;

    /**
     * [Fix] Reservation 객체를 저장한다.
     */
    @Transactional
    public Reservation saveBy(User user, Product product) {
        var reservation = createBy(user, product);

        return reservationRepository.save(reservation);
    }

    /**
     * Reservation 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Reservation> getAllBy(Long userId) {
        return reservationRepositoryQuery.getAllBy(userId);
    }

    /**
     * Reservation 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Reservation> getAllBy(Long userId, ReservationStatusType reservationStatusType) {
        return reservationRepositoryQuery.getAllBy(userId, reservationStatusType);
    }

    /**
     * Reservation 객체를 수정한다.
     */
    @Transactional
    public Reservation updateBy(Long userId, Long reservationId, ReservationStatusType reservationStatusType) {
        var reservationOpt = reservationRepositoryQuery.getByUserReservation(userId, reservationId);

        if (reservationOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.RESERVATION_GET_ERROR);
        } else {
            var reservation = reservationOpt.get();
            if (reservation.getStatusType().equals(ReservationStatusType.COMPLETED)) {
                throw AppException.of404(AppExceptionType.RESERVATION_STATUS_UPDATE_ERROR);
            }
            if (reservation.getStatusType().equals(ReservationStatusType.CANCELED)) {
                throw AppException.of404(AppExceptionType.RESERVATION_STATUS_UPDATE_ERROR);
            }
            reservation.updateStatusType(reservationStatusType);
            return reservation;
        }
    }

    /**
     * Reservation 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Reservation getByUserProduct(Long userId, Long productId) {
        var reservationOpt = reservationRepositoryQuery.getByUserProduct(userId, productId);

        if (reservationOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.RESERVATION_GET_ERROR);
        } else return reservationOpt.get();
    }

    /**
     * Reservation 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Reservation getByUserReservation(Long userId, Long reservationId) {
        var reservationOpt = reservationRepositoryQuery.getByUserReservation(userId, reservationId);

        if (reservationOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.RESERVATION_GET_ERROR);
        } else return reservationOpt.get();
    }

    private Reservation createBy(User user, Product product) {
        return Reservation.of(user, product);
    }
}