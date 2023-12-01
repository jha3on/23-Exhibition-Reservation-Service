package app.api.reservation;

import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.api.reservation.request.ReservationCreateDto;
import app.api.reservation.request.ReservationStatusUpdateDto;
import app.core.rds.entity.reservation.type.ReservationStatusType;
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
public class ReservationApi {
    private final ReservationApiService apiService;

    /** 예매 목록 조회 */
    @GetMapping(value = {"/api/reservations"})
    public ResponseEntity<?> getReservations() {
        var result = apiService.getReservationsBy(getCurrentUserBy());

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** 예매 생성 */
    @PostMapping(value = {"/api/reservations/{productId}"})
    public ResponseEntity<?> createReservation(
        @PathVariable Long productId,
        @RequestBody ReservationCreateDto reservationDto
    ) {
        System.out.println(reservationDto);
        var result = apiService.createReservationBy(getCurrentUserBy(), productId, reservationDto);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** 예매 목록 개수 조회 */
    @GetMapping(value = {"/api/reservations/count"})
    public ResponseEntity<?> getReservationCounts() {
        var result = apiService.getReservationCountsBy(getCurrentUserBy());

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** 예매 목록 필터링 조회 (ex: /pageApi/reservations/filter?status={value}) */
    @GetMapping(value = {"/api/reservations/filter"})
    public ResponseEntity<?> getReservations(
        @RequestParam(value = "status", required = false) ReservationStatusType reservationStatusType
    ) {
        var result = apiService.getReservationsBy(getCurrentUserBy(), reservationStatusType);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** 예매 상태 수정 */
    @PutMapping(value = {"/api/reservations/{reservationId}/status"})
    public ResponseEntity<?> updateReservationStatus(
        @PathVariable Long reservationId,
        @RequestBody ReservationStatusUpdateDto reservationDto
    ) {
        var result = apiService.updateReservationStatusBy(getCurrentUserBy(), reservationId, reservationDto);

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