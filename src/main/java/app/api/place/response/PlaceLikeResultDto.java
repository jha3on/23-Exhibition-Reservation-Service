package app.api.place.response;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class PlaceLikeResultDto implements Serializable {
    private Boolean placeResult;

    public static PlaceLikeResultDto of(Boolean placeResult) {
        return PlaceLikeResultDto.builder()
                .placeResult(placeResult)
                .build();
    }
}