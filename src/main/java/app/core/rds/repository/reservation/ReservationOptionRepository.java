package app.core.rds.repository.reservation;

import app.core.rds.entity.reservation.ReservationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOptionRepository extends JpaRepository<ReservationOption, Long> {
    // ...
}