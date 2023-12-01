package app.api.reservation.response;

import app.core.rds.entity.reservation.ReservationOption;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ReservationOptionResultDto implements Serializable {
    private Long reservationOptionId;
    private String reservationOptionName;
    private Integer reservationOptionPrice;
    private Integer reservationOptionQuantity;

    public static ReservationOptionResultDto of(ReservationOption reservationOption) {
        var productOption = reservationOption.getProductOption();

        return ReservationOptionResultDto.builder()
                .reservationOptionId(reservationOption.getId())
                .reservationOptionName(productOption.getName())
                .reservationOptionPrice(reservationOption.getPrice())
                .reservationOptionQuantity(reservationOption.getQuantity())
                .build();
    }

    public static List<ReservationOptionResultDto> of(List<ReservationOption> reservationOptions) {
        return reservationOptions.stream()
                .filter(reservationOption -> reservationOption.getQuantity() > 0)
                .map(ReservationOptionResultDto::of)
                .collect(Collectors.toList());
    }
}