package app.share.app.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AppExceptionType {
    // -----------------------------------------------------------------------------------------------------------------
    USER_ERROR("-200", "회원 요청을 수행할 수 없습니다."),
    USER_SIGN_ERROR("-201", "회원 가입 정보를 확인해주세요."),
    USER_LOGIN_ERROR("-202", "회원 로그인 정보를 확인해주세요."),
    USER_LOGOUT_ERROR("-203", "회원 로그아웃을 수행할 수 없습니다."),
    USER_GET_ERROR("-204", "회원을 조회할 수 없습니다."),
    USER_SESSION_ERROR("-205", "회원 세션을 수행할 수 없습니다."),
    USER_SESSION_GET_ERROR("-206", "회원 세션을 조회할 수 없습니다."),
    // -----------------------------------------------------------------------------------------------------------------
    PLACE_ERROR("-300", "장소 요청을 수행할 수 없습니다."),
    PLACE_GET_ERROR("-301", "장소를 조회할 수 없습니다."),
    // -----------------------------------------------------------------------------------------------------------------
    PRODUCT_ERROR("-400", ""),
    PRODUCT_GET_ERROR("-401", "상품을 조회할 수 없습니다."),
    PRODUCT_OPTION_GET_ERROR("-402", "상품 옵션을 조회할 수 없습니다."),
    PRODUCT_OPTION_STOCK_ERROR("-403", "상품 옵션 재고가 부족합니다."),
    // -----------------------------------------------------------------------------------------------------------------
    PROMOTION_ERROR("-500", ""),
    PROMOTION_GET_EMPTY("-501", "프로모션을 조회할 수 없습니다."),
    // -----------------------------------------------------------------------------------------------------------------
    RESERVATION_ERROR("-600", ""),
    RESERVATION_GET_ERROR("-601", "예매를 조회할 수 없습니다."),
    RESERVATION_STATUS_UPDATE_ERROR("-602", "예매 상태를 수정할 수 없습니다."),
    // -----------------------------------------------------------------------------------------------------------------
    REVIEW_ERROR("-700", ""),
    REVIEW_GET_ERROR("-701", "리뷰를 조회할 수 없습니다."),
    REVIEW_SUM_GET_ERROR("-702", "리뷰 점수 합계를 조회할 수 없습니다."),
    // -----------------------------------------------------------------------------------------------------------------
    FILE_ERROR("", ""),
    REDISSON_TRANSACTION_LOCK_ERROR("", ""),
    ;

    private final String code;
    private final String message;
}