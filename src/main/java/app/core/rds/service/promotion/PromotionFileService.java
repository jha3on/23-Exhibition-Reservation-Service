package app.core.rds.service.promotion;

import app.core.rds.entity.file.File;
import app.core.rds.repository.file.query.FileRepositoryQuery;
import app.share.storage.FileService;
import app.core.rds.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionFileService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FileRepositoryQuery fileRepositoryQuery;

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(Long promotionProductId) {
        return fileRepositoryQuery.getAllByProductId(promotionProductId);
    }

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(List<Long> promotionProductIds) {
        return fileRepositoryQuery.getAllByProductIds(promotionProductIds);
    }

//    /**
//     * [Fix] File 객체를 저장한다.
//     */
//    @Transactional
//    public File saveBy(Promotion promotion, MultipartFile file) {
//        var promotionFileDto = fileService.saveBy(file, "src/main/resources/static/image/promotion");
//        var promotionFiles = createBy(promotion, promotionFileDto);
//
//        return fileRepository.save(promotionFiles);
//    }
//
//    /**
//     * [Fix] File 컬렉션을 저장한다.
//     */
//    @Transactional
//    public List<File> saveAllBy(Promotion promotion, List<MultipartFile> files) {
//        var promotionFileDtos = fileService.saveAllBy(files, "src/main/resources/static/image/promotion");
//        var promotionFiles = createAllBy(promotion, promotionFileDtos);
//
//        return fileRepository.saveAll(promotionFiles);
//    }
//
//    /**
//     * [Fix] File 컬렉션을 조회한다.
//     */
//    @Transactional(readOnly = true)
//    public List<File> getAllBy(Long promotionId) {
//        return fileRepositoryQuery.getAllByPromotionId(promotionId);
//    }
//
//    /**
//     * [Fix] File 컬렉션을 조회한다.
//     */
//    @Transactional(readOnly = true)
//    public List<File> getAllBy(List<Long> promotionIds) {
//        return fileRepositoryQuery.getAllByPromotionIds(promotionIds);
//    }
//
//    private File createBy(Promotion promotion, FileDto fileDto) {
//        return File.of(
//                promotion,
//                fileDto.getFilePath(),
//                fileDto.getFileGroupType()
//        );
//    }
//
//    private List<File> createAllBy(Promotion promotion, List<FileDto> fileDtos) {
//        return fileDtos.stream()
//                .map(fileDto -> createBy(promotion, fileDto))
//                .collect(Collectors.toList());
//    }
}