package app.api.reservation.response;

import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString @Builder
public class ReservationCountResultDto implements Serializable {
    private Integer reservationCount;
    private ReservationStatusType reservationStatusType;

    public static ReservationCountResultDto of(List<Reservation> reservations, ReservationStatusType reservationStatusType) {
        var count = 0;

        for (var reservation : reservations) {
            if (Objects.equals(reservationStatusType, reservation.getStatusType())) {
                count += 1;
            }
        }

        return ReservationCountResultDto.builder()
                .reservationCount(count)
                .reservationStatusType(reservationStatusType)
                .build();
    }

    public static List<ReservationCountResultDto> of(List<Reservation> reservations, List<ReservationStatusType> reservationStatusTypes) {
        var results = new ArrayList<ReservationCountResultDto>();

        for (var reservationStateType : reservationStatusTypes) {
            results.add(of(reservations, reservationStateType));
        }

        return results;
    }
}