package app.core.rds.service.place;

import app.core.rds.entity.file.File;
import app.core.rds.entity.place.Place;
import app.core.rds.repository.file.query.FileRepositoryQuery;
import app.share.storage.FileService;
import app.share.storage.payload.FileDto;
import app.core.rds.repository.file.FileRepository;
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
public class PlaceFileService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FileRepositoryQuery fileRepositoryQuery;

    /**
     * [Fix] File 객체를 저장한다.
     */
    @Transactional
    public File saveBy(Place place, MultipartFile file) {
        var placeFileDto = fileService.saveBy(file, "src/main/resources/static/image/place");
        var placeFile = createBy(place, placeFileDto);

        return fileRepository.save(placeFile);
    }

    /**
     * [Fix] File 컬렉션을 저장한다.
     */
    @Transactional
    public List<File> saveAllBy(Place place, List<MultipartFile> files) {
        var placeFileDtos = fileService.saveAllBy(files, "src/main/resources/static/image/place");
        var placeFiles = createAllBy(place, placeFileDtos);

        return fileRepository.saveAll(placeFiles);
    }

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(Long placeId) {
        return fileRepositoryQuery.getAllByPlaceId(placeId);
    }

    /**
     * [Fix] File 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<File> getAllBy(List<Long> placeIds) {
        return fileRepositoryQuery.getAllByPlaceIds(placeIds);
    }

    private File createBy(Place place, FileDto fileDto) {
        return File.of(
                place,
                fileDto.getFilePath(),
                fileDto.getFileGroupType()
        );
    }

    private List<File> createAllBy(Place place, List<FileDto> fileDtos) {
        return fileDtos.stream()
                .map(fileDto -> createBy(place, fileDto))
                .collect(Collectors.toList());
    }
}