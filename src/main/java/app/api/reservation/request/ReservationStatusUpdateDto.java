package app.api.reservation.request;

import app.core.rds.entity.reservation.type.ReservationStatusType;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationStatusUpdateDto implements Serializable {
    private ReservationStatusType reservationStatusType;
}