package app.api.reservation.request;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationCreateDto implements Serializable {
    private List<ReservationOptionCreateDto> reservationOptions;
}