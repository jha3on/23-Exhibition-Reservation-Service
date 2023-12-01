package app.api.reservation.response;

import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.ReservationOption;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ReservationResultDto implements Serializable {
    private Long productId;
    private String productName;
    private LocalDateTime productDateTo;
    private LocalDateTime productDateFrom;
    private Long reservationId;
    private ReservationStatusType reservationStatusType;
    private List<ReservationOptionResultDto> reservationOptions;

    public static ReservationResultDto of(Reservation reservation, List<ReservationOption> reservationOptions) {
        var product = reservation.getProduct();

        return ReservationResultDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDateTo(product.getDateTo())
                .productDateFrom(product.getDateFrom())
                .reservationId(reservation.getId())
                .reservationStatusType(reservation.getStatusType())
                .reservationOptions(ReservationOptionResultDto.of(reservationOptions))
                .build();
    }

    public static List<ReservationResultDto> of(List<Reservation> reservations, List<ReservationOption> reservationOptions) {
        var results = new ArrayList<ReservationResultDto>();

        for (var targetReservation : reservations) {
            var targetReservationOptions = reservationOptions.stream()
                    .filter(e -> e.getReservation().getId().equals(targetReservation.getId()))
                    .collect(Collectors.toList());

            results.add(of(targetReservation, targetReservationOptions));
        }

        return results;
    }
}