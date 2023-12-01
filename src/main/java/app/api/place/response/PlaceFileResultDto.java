package app.api.place.response;

import app.core.rds.entity.file.File;
import app.core.rds.entity.file.type.FileGroupSubtype;
import app.core.rds.entity.file.type.FileGroupType;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class PlaceFileResultDto implements Serializable {
    private Long placeFileId; // 장소 파일 ID
    private String placeFilePath; // 장소 파일 경로
    private FileGroupType placeFileGroupType; // 장소 파일 그룹 유형
    private FileGroupSubtype placeFileGroupSubtype; // 장소 파일 그룹 유형

    public static PlaceFileResultDto of(File file) {
        return PlaceFileResultDto.builder()
                .placeFileId(file.getId())
                .placeFilePath(file.getPath())
                .placeFileGroupType(file.getGroupType())
                .placeFileGroupSubtype(file.getGroupSubtype())
                .build();
    }

    public static List<PlaceFileResultDto> of(List<File> files) {
        return files.stream()
                .map(PlaceFileResultDto::of)
                .collect(Collectors.toList());
    }
}