package app.api.place.request;

import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceCreateDto implements Serializable {
    private String placeName; // 장소 이름
    private String placeAddress; // 장소 주소
    private String placeContact; // 장소 연락처
    private PlaceGroupType placeGroupType; // 장소 그룹 유형
    private PlaceGroupSubtype placeGroupSubtype; // 장소 그룹 유형
}