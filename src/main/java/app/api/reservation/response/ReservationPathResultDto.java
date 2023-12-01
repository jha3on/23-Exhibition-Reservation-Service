package app.api.reservation.response;

import app.core.rds.entity.reservation.Reservation;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class ReservationPathResultDto implements Serializable {
    private Long reservationId;

    public static ReservationPathResultDto of(Reservation reservation) {
        return ReservationPathResultDto.builder()
                .reservationId(reservation.getId())
                .build();
    }
}