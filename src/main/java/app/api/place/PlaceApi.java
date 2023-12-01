package app.api.place;

import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.share.app.payload.ApiResponse;
import app.share.app.payload.ApiResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceApi {
    private final PlaceApiService apiService;

    /** [Fix] 장소 목록 조회 */
    @GetMapping(value = {"/api/places"})
    public ResponseEntity<?> getPlaces() {
        var result = apiService.getPlacesBy();

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 장소 조회 */
    @GetMapping(value = {"/api/places/{placeId}"})
    public ResponseEntity<?> getPlace(
        @PathVariable Long placeId
    ) {
        var result = apiService.getPlaceBy(placeId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 관심 장소 토글 */
    @PostMapping(value = {"/api/places/{placeId}/like"})
    public ResponseEntity<?> togglePlaceLike(
        @PathVariable Long placeId
    ) {
        var result = apiService.togglePlaceLikeBy(getCurrentUserBy(), placeId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 로그인 세션 조회 */
    private Long getCurrentUserBy() {
        return (!hasCurrentUserBy()) ? null : SessionUtils.getAttributeBy(SessionKey.USER).getUserId();
    }

    /** [Fix] 로그인 세션 확인 */
    private Boolean hasCurrentUserBy() {
        return SessionUtils.hasAttributeBy(SessionKey.USER);
    }
}