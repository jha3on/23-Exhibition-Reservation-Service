package app.api.promotion.response;

import app.core.rds.entity.file.File;
import app.core.rds.entity.file.type.FileGroupSubtype;
import app.core.rds.entity.file.type.FileGroupType;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class PromotionFileResultDto implements Serializable {
    private Long promotionFileId; // 프로모션 파일 ID
    private String promotionFilePath; // 프로모션 파일 경로
    private FileGroupType promotionFileGroupType; // 프로모션 파일 그룹 유형
    private FileGroupSubtype promotionFileGroupSubtype; // 프로모션 파일 그룹 유형

    public static PromotionFileResultDto of(File file) {
        return PromotionFileResultDto.builder()
                .promotionFileId(file.getId())
                .promotionFilePath(file.getPath())
                .promotionFileGroupType(file.getGroupType())
                .promotionFileGroupSubtype(file.getGroupSubtype())
                .build();
    }

    public static List<PromotionFileResultDto> of(List<File> files) {
        return files.stream()
                .map(PromotionFileResultDto::of)
                .collect(Collectors.toList());
    }
}