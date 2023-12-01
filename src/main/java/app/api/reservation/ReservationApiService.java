package app.api.reservation;

import app.core.rds.entity.reservation.Reservation;
import app.core.rds.service.product.ProductOptionService;
import app.api.reservation.request.ReservationStatusUpdateDto;
import app.core.rds.service.product.ProductOptionRedissonService;
import app.core.rds.service.product.ProductService;
import app.core.rds.service.reservation.ReservationOptionService;
import app.core.rds.service.reservation.ReservationRedissonService;
import app.core.rds.service.reservation.ReservationService;
import app.api.reservation.request.ReservationCreateDto;
import app.api.reservation.response.ReservationCountResultDto;
import app.api.reservation.response.ReservationPathResultDto;
import app.api.reservation.response.ReservationResultDto;
import app.core.rds.service.user.UserService;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationApiService {
    private final UserService userService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final ProductOptionRedissonService productOptionRedissonService;
    private final ReservationService reservationService;
    private final ReservationOptionService reservationOptionService;
    private final ReservationRedissonService reservationRedissonService;
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 예매를 생성한다.
     */
    @Transactional
    public ReservationPathResultDto createReservationBy(Long userId, Long productId, ReservationCreateDto reservationDto) {
        var user = userService.getBy(userId);
        var product = productService.getBy(productId);
        var productOptions = productOptionService.getAllBy(productId);
//        var reservation = reservationService.saveBy(user, product);
//        var reservationOptions = reservationOptionService.saveAllBy(product, productOptions, reservation, reservationDto.getReservationOptions());
        var reservation = reservationRedissonService.saveReservationBy(user, product);
        var reservationOptions = reservationRedissonService.saveAllReservationOptionBy(product, productOptions, reservation, reservationDto.getReservationOptions());

//        reservationOptions.forEach(reservationOption -> productOptionService.updateBy(reservationOption.getProductOption().getId(), -reservationOption.getQuantity()));
        reservationOptions.forEach(reservationOption -> productOptionRedissonService.updateProductOptionBy(reservationOption.getProductOption().getId(), -reservationOption.getQuantity()));

        return ReservationPathResultDto.of(reservation);
    }

    /**
     * 예매 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ReservationResultDto> getReservationsBy(Long userId) {
        var reservations = reservationService.getAllBy(userId);
        var reservationOptions = reservationOptionService.getAllBy(getReservationIds(reservations));

        return ReservationResultDto.of(reservations, reservationOptions);
    }

    /**
     * 예매 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ReservationResultDto> getReservationsBy(Long userId, ReservationStatusType reservationStatusType) {
        var reservations = reservationService.getAllBy(userId, reservationStatusType);
        var reservationOptions = reservationOptionService.getAllBy(getReservationIds(reservations));

        return ReservationResultDto.of(reservations, reservationOptions);
    }

    /**
     * 예매 목록 개수을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ReservationCountResultDto> getReservationCountsBy(Long userId) {
        var reservations = reservationService.getAllBy(userId);

        return ReservationCountResultDto.of(reservations, ReservationStatusType.getAll());
    }

    /**
     * 예매 상태를 수정한다.
     */
    @Transactional
    public ReservationPathResultDto updateReservationStatusBy(Long userId, Long reservationId, ReservationStatusUpdateDto reservationDto) {
        var user = userService.getBy(userId);
        var reservation = reservationService.updateBy(user.getId(), reservationId, reservationDto.getReservationStatusType());

        return ReservationPathResultDto.of(reservation);
    }

    private List<Long> getReservationIds(List<Reservation> reservations) {
        return reservations.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------------------------------------------------
}