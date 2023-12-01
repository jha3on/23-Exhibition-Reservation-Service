package app.api.place.response;

import app.core.rds.entity.file.File;
import app.core.rds.entity.place.Place;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class PlaceResultDto implements Serializable {
    private Long placeId; // 장소 ID
    private String placeName; // 장소 이름
    private String placeAddress; // 장소 주소
    private String placeContact; // 장소 연락처
    private PlaceGroupType placeGroupType; // 장소 그룹 유형
    private PlaceGroupSubtype placeGroupSubtype; // 장소 그룹 유형
    private List<PlaceFileResultDto> placeFiles; // 장소 파일

    public static PlaceResultDto of(Place place) {
        return PlaceResultDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .placeAddress(place.getAddress())
                .placeContact(place.getContact())
                .placeGroupType(place.getGroupType())
                .placeGroupSubtype(place.getGroupSubtype())
                .build();
    }

    public static PlaceResultDto of(Place place, List<File> placeFiles) {
        return PlaceResultDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .placeAddress(place.getAddress())
                .placeContact(place.getContact())
                .placeGroupType(place.getGroupType())
                .placeGroupSubtype(place.getGroupSubtype())
                .placeFiles(PlaceFileResultDto.of(placeFiles))
                .build();
    }

    public static List<PlaceResultDto> of(List<Place> places, List<File> placeFiles) {
        var results = new ArrayList<PlaceResultDto>();

        for (var targetPlace : places) {
            var targetPlaceFiles = placeFiles.stream()
                    .filter(e -> e.getPlace().getId().equals(targetPlace.getId()))
                    .collect(Collectors.toList());

            results.add(of(targetPlace, targetPlaceFiles));
        }

        return results;
    }
}