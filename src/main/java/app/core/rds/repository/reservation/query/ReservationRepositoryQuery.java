package app.core.rds.repository.reservation.query;

import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.ReservationOption;
import app.core.rds.entity.reservation.type.ReservationStatusType;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryQuery {

    /** [Fix] Reservation 객체 조회 */
    Optional<Reservation> getByUserProduct(Long userId, Long productId);

    /** [Fix] Reservation 객체 조회 */
    Optional<Reservation> getByUserReservation(Long userId, Long reservationId);

    /** [Fix] Reservation 컬렉션 조회 */
    List<Reservation> getAllBy(Long userId);

    /** [Fix] Reservation 컬렉션 조회 */
    List<Reservation> getAllBy(Long userId, ReservationStatusType reservationStatusType);

    /** [Fix] ReservationOption 컬렉션 조회 */
    List<ReservationOption> getAllBy(List<Long> reservationIds);
}