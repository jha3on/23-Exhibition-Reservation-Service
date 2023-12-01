package app.api.product.response;

import app.core.rds.entity.file.File;
import app.core.rds.entity.file.type.FileGroupSubtype;
import app.core.rds.entity.file.type.FileGroupType;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ProductFileResultDto implements Serializable {
    private Long productFileId; // 상품 파일 ID
    private String productFilePath; // 상품 파일 경로
    private FileGroupType productFileGroupType; // 상품 파일 그룹 유형
    private FileGroupSubtype productFileGroupSubtype; // 상품 파일 그룹 유형

    public static ProductFileResultDto of(File file) {
        return ProductFileResultDto.builder()
                .productFileId(file.getId())
                .productFilePath(file.getPath())
                .productFileGroupType(file.getGroupType())
                .productFileGroupSubtype(file.getGroupSubtype())
                .build();
    }

    public static List<ProductFileResultDto> of(List<File> files) {
        return files.stream()
                .map(ProductFileResultDto::of)
                .collect(Collectors.toList());
    }
}