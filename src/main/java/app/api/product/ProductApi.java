package app.api.product;

import app.api.place.PlaceApiService;
import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.entity.product.type.ProductGroupType;
import app.share.app.payload.ApiResponse;
import app.share.app.payload.ApiResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductApi {
    private final PlaceApiService placeApiService;
    private final ProductApiService apiService;

    /** 상품 조회 */
    @GetMapping(value = {"/api/products/{productId}"})
    public ResponseEntity<?> getProduct(
        @PathVariable Long productId
    ) {
        var result = apiService.getProductBy(getCurrentUserBy(), productId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 상품 장소 조회 */
    @GetMapping(value = {"/api/products/{productId}/place"})
    public ResponseEntity<?> getProductPlace(
        @PathVariable Long productId
    ) {
        var result = placeApiService.getProductPlaceBy(productId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 상품 옵션 조회 */
    @GetMapping(value = {"/api/products/{productId}/options"})
    public ResponseEntity<?> getProductOptions(
        @PathVariable Long productId
    ) {
        var result = apiService.getProductOptionsBy(productId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 상품 목록 검색 조회 (ex: /api/products/search?query={value}) */
    @GetMapping(value = {"/api/products/search"})
    public ResponseEntity<?> getProducts(
        @RequestParam(value = "query", required = false) String productName
    ) {
        var result = apiService.getProductsBy(productName);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 상품 목록 필터링 조회 (ex: /api/products/filter?type1={value}&type2={value}&type3={value}) */
    @GetMapping(value = {"/api/products/filter"})
    public ResponseEntity<?> getProducts(
        @RequestParam(value = "type1", required = false) PlaceGroupType placeGroupType,
        @RequestParam(value = "type2", required = false) PlaceGroupSubtype placeGroupSubtype,
        @RequestParam(value = "type3", required = false) ProductGroupType productGroupType
    ) {
        var result = apiService.getProductsBy(placeGroupType, placeGroupSubtype, productGroupType);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 관심 상품 목록 조회 */
    @GetMapping(value = {"/api/products/likes"})
    public ResponseEntity<?> getProductLikes() {
        var result = apiService.getProductLikesBy(getCurrentUserBy());

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 관심 상품 토글 */
    @PostMapping(value = {"/api/products/{productId}/like"})
    public ResponseEntity<?> toggleProductLike(
        @PathVariable Long productId
    ) {
        var result = apiService.toggleProductLikeBy(getCurrentUserBy(), productId);

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