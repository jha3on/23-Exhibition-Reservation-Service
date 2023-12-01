package app.api.place;

import app.api.place.response.PlaceLikeResultDto;
import app.api.place.response.PlacePathResultDto;
import app.api.place.response.PlaceResultDto;
import app.core.rds.entity.place.Place;
import app.core.rds.service.place.PlaceFileService;
import app.core.rds.service.place.PlaceLikeService;
import app.core.rds.service.place.PlaceService;
import app.api.place.request.PlaceCreateDto;
import app.core.rds.service.user.UserService;
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
public class PlaceApiService {
    private final UserService userService;
    private final PlaceService placeService;
    private final PlaceFileService placeFileService;
    private final PlaceLikeService placeLikeService;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * [Fix] 장소를 생성한다.
     */
    @Transactional
    public PlacePathResultDto createPlaceBy(PlaceCreateDto placeDto, List<MultipartFile> attachments) {
        var place = placeService.saveBy(placeDto);
        var placeFiles = placeFileService.saveAllBy(place, attachments);

        return PlacePathResultDto.of(place);
    }

    /**
     * [Fix] 장소를 조회한다.
     */
    @Transactional(readOnly = true)
    public PlaceResultDto getPlaceBy(Long placeId) {
        var place = placeService.getBy(placeId);
        var placeFiles = placeFileService.getAllBy(place.getId());

        return PlaceResultDto.of(place, placeFiles);
    }

    /**
     * [Fix] 장소를 조회한다.
     */
    @Transactional(readOnly = true)
    public PlaceResultDto getProductPlaceBy(Long productId) {
        var place = placeService.getByProductId(productId);
        var placeFiles = placeFileService.getAllBy(place.getId());

        return PlaceResultDto.of(place, placeFiles);
    }

    /**
     * [Fix] 장소 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<PlaceResultDto> getPlacesBy() {
        var places = placeService.getAllBy();
        var placeFiles = placeFileService.getAllBy(getPlaceIds(places));

        return PlaceResultDto.of(places, placeFiles);
    }

    /**
     * [Fix] 관심 장소를 생성/삭제한다.
     */
    @Transactional
    public PlaceLikeResultDto togglePlaceLikeBy(Long userId, Long placeId) {
        var user = userService.getBy(userId);
        var place = placeService.getBy(placeId);
        var placeLike = placeLikeService.toggleBy(user, place);

        return PlaceLikeResultDto.of(placeLike);
    }

    private List<Long> getPlaceIds(List<Place> places) {
        return places.stream()
                .map(Place::getId)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------------------------------------------------
}