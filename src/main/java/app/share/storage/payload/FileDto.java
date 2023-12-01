package app.share.storage.payload;

import app.core.rds.entity.file.type.FileGroupSubtype;
import app.core.rds.entity.file.type.FileGroupType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter @ToString @Builder
public class FileDto implements Serializable {
    private String fileName;
    private String filePath;
    private FileGroupType fileGroupType;
    private FileGroupSubtype fileGroupSubtype;

    public static FileDto of(MultipartFile file, String filePath) {
        var fileName = String.format("%s.jpg", UUID.randomUUID());

        return FileDto.builder()
                .fileName(fileName)
                .filePath(String.format("%s/%s", filePath.replace("src/main/resources/static", ""), fileName))
                .fileGroupType(FileGroupType.from(file.getContentType()))
                .fileGroupSubtype(FileGroupSubtype.from(file.getContentType()))
                .build();
    }
}