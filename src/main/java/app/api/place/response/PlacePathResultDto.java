package app.api.place.response;

import app.core.rds.entity.place.Place;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class PlacePathResultDto implements Serializable {
    private Long placeId; // 장소 ID

    public static PlacePathResultDto of(Place place) {
        return PlacePathResultDto.builder()
                .placeId(place.getId())
                .build();
    }
}