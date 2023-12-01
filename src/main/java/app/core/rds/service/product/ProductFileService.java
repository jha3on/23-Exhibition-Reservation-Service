package app.core.rds.service.product;

import app.core.rds.entity.file.File;
import app.share.storage.FileService;
import app.share.storage.payload.FileDto;
import app.core.rds.entity.product.Product;
import app.core.rds.repository.file.FileRepository;
import app.core.rds.repository.file.query.FileRepositoryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFileService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FileRepositoryQuery fileRepositoryQuery;

    /**
     * [Fix] File 객체를 저장한다.
     */
    @Transactional
    public File saveBy(Product product, MultipartFile file) {
        var productFileDto = fileService.saveBy(file, "src/main/resources/static/image/product");
        var productFile = createBy(product, productFileDto);

        return fileRepository.save(productFile);
    }

    /**
     * [Fix] File 컬렉션을 저장한다.
     */
    @Transactional
    public List<File> saveAllBy(Product product, List<MultipartFile> files) {
        var productFileDtos = fileService.saveAllBy(files, "src/main/resources/static/image/product");
        var productFiles = createAllBy(product, productFileDtos);

        return fileRepository.saveAll(productFiles);
    }

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(Long productId) {
        return fileRepositoryQuery.getAllByProductId(productId);
    }

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(List<Long> productIds) {
        return fileRepositoryQuery.getAllByProductIds(productIds);
    }

    private File createBy(Product product, FileDto fileDto) {
        return File.of(
                product,
                fileDto.getFilePath(),
                fileDto.getFileGroupType()
        );
    }

    private List<File> createAllBy(Product product, List<FileDto> fileDtos) {
        return fileDtos.stream()
                .map(fileDto -> createBy(product, fileDto))
                .collect(Collectors.toList());
    }
}