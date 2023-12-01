package app.share.storage;

import app.core.rds.entity.file.type.FileGroupType;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import app.share.storage.payload.FileDto;
import app.share.storage.payload.FileOriginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileService {

    @Transactional
    public MultipartFile createBy(String fileName, String fileContent, String fileContentType) {
        log.info("fileName: {}", fileName);
        log.info("fileContent: {}", fileContent);
        log.info("fileContentType: {}", fileContentType);
        var file = new File(fileContent);

        try (var fileStream =  new FileInputStream(file)) {
            return new FileOriginDto(fileName, fileName, fileContentType, fileStream.readAllBytes());
        } catch (IOException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public FileDto saveBy(MultipartFile file, String fileSavePath) {
        var fileGroupType = FileGroupType.from(file.getContentType());

        return switch (fileGroupType) {
            case IMAGE -> transferBy(file, fileSavePath);
        };
    }

    @Transactional
    public List<FileDto> saveAllBy(List<MultipartFile> files, String fileSavePath) {
        return files.stream()
                .map(file -> saveBy(file, fileSavePath))
                .collect(Collectors.toList());
    }

    @Transactional
    public FileDto transferBy(MultipartFile file, String fileSavePath) {
        log.info("fileName: {}", file.getName());
        log.info("fileContentType: {}", file.getContentType());
        log.info("fileSavePath: {}", fileSavePath);
        var fileDto = FileDto.of(file, fileSavePath);
        log.info("fileDto: {}", fileDto.toString());

        try {
            file.transferTo(new File(String.format("%s/%s", fileSavePath, fileDto.getFileName())));
        } catch (IOException e) {
            throw AppException.of500(AppExceptionType.FILE_ERROR);
        }

        return fileDto;
    }
}