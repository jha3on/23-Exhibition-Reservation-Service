package app.api.promotion;

import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import app.share.app.payload.ApiResponse;
import app.share.app.payload.ApiResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionApi {
    private final PromotionApiService apiService;

    /** [Fix] 프로모션 목록 필터링 조회 (ex: /api/promotions/filter?type1={value}) */
    @GetMapping(value = {"/api/promotions/filter"})
    public ResponseEntity<?> getPromotions(
        @RequestParam(value = "type1", required = false) PromotionGroupType promotionGroupType
    ) {
        var result = apiService.getPromotionsBy(promotionGroupType);

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