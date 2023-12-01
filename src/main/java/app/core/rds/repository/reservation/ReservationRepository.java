package app.core.rds.repository.reservation;

import app.core.rds.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // ...
}