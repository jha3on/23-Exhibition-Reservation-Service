package app.api.reservation.request;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationOptionCreateDto implements Serializable {
    private Long productOptionId;
    private Integer productOptionQuantity;
}