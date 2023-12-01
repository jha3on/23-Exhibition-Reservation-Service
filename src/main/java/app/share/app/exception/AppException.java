package app.share.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final HttpStatus status;
    private final AppExceptionType statusType;

    protected AppException(HttpStatus status, AppExceptionType statusType) {
        super(statusType.getMessage());
        this.status = status;
        this.statusType = statusType;
    }

    public static AppException of(HttpStatus status, AppExceptionType statusType) {
        return new AppException(status, statusType);
    }

    /**
     * 클라이언트 요청 오류
     * <p> : 잘못된 리소스를 요청하는 경우
     */
    public static AppException of400(AppExceptionType statusType) {
        return new AppException(HttpStatus.BAD_REQUEST, statusType);
    }

    /**
     * 클라이언트 인증 오류
     * <p> : 요청 처리에 필요한 사용자 인증이 없는 경우
     */
    public static AppException of401(AppExceptionType statusType) {
        return new AppException(HttpStatus.UNAUTHORIZED, statusType);
    }

    /**
     * 클라이언트 인가 오류
     * <p> : 요청 처리에 필요한 사용자 권한이 없는 경우
     */
    public static AppException of403(AppExceptionType statusType) {
        return new AppException(HttpStatus.FORBIDDEN, statusType);
    }

    /**
     * 클라이언트 요청 오류
     * <p> : 요청한 리소스가 존재하지 않는 경우
     */
    public static AppException of404(AppExceptionType statusType) {
        return new AppException(HttpStatus.NOT_FOUND, statusType);
    }

    /**
     * 클라이언트 요청 오류
     * <p> : 요청한 리소스가 중복되는 경우
     */
    public static AppException of409(AppExceptionType statusType) {
        return new AppException(HttpStatus.CONFLICT, statusType);
    }

    /**
     * 서버 응답 오류
     * <p> : 요청 처리 중 서버 오류가 발생하는 경우
     */
    public static AppException of500(AppExceptionType statusType) {
        return new AppException(HttpStatus.INTERNAL_SERVER_ERROR, statusType);
    }
}