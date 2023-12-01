package app.share.app.payload;

import app.share.app.exception.AppExceptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String code; // 응답 코드
    private String message; // 응답 메시지
    private T result; // 응답 결과

    // -----------------------------------------------------------------------------------------------------------------

    protected static ApiResponse<?> create(ApiResponseType responseType) {
        return ApiResponse.builder()
                .code(responseType.getCode())
                .message(responseType.getMessage())
                .result(null)
                .build();
    }

    protected static <T> ApiResponse<?> create(ApiResponseType responseType, T result) {
        return ApiResponse.builder()
                .code(responseType.getCode())
                .message(responseType.getMessage())
                .result(result)
                .build();
    }

    protected static <T> ApiResponse<?> create(ApiResponseType responseType, String message, T result) {
        return ApiResponse.builder()
                .code(responseType.getCode())
                .message(message)
                .result(result)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected static ApiResponse<?> create(AppExceptionType exceptionType) {
        return ApiResponse.builder()
                .code(exceptionType.getCode())
                .message(exceptionType.getMessage())
                .result(null)
                .build();
    }

    protected static <T> ApiResponse<?> create(AppExceptionType exceptionType, T result) {
        return ApiResponse.builder()
                .code(exceptionType.getCode())
                .message(exceptionType.getMessage())
                .result(result)
                .build();
    }

    protected static <T> ApiResponse<?> create(AppExceptionType exceptionType, String message, T result) {
        return ApiResponse.builder()
                .code(exceptionType.getCode())
                .message(message)
                .result(result)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 응답 객체를 생성한다.
     */
    public static ResponseEntity<?> of(HttpStatus status, ApiResponseType responseType) {
        var response = ApiResponse.create(responseType);

        return new ResponseEntity<>(response, status);
    }

    /**
     * 응답 객체를 생성한다.
     */
    public static <T> ResponseEntity<?> of(HttpStatus status, ApiResponseType responseType, T result) {
        var response = ApiResponse.create(responseType, result);

        return new ResponseEntity<>(response, status);
    }

    /**
     * 응답 객체를 생성한다.
     */
    public static <T> ResponseEntity<?> of(HttpStatus status, ApiResponseType responseType, String message, T result) {
        var response = ApiResponse.create(responseType, message, result);

        return new ResponseEntity<>(response, status);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 응답 객체를 생성한다.
     */
    public static ResponseEntity<?> of(HttpStatus status, AppExceptionType exceptionType) {
        var response = ApiResponse.create(exceptionType);

        return new ResponseEntity<>(response, status);
    }

    /**
     * 응답 객체를 생성한다.
     */
    public static <T> ResponseEntity<?> of(HttpStatus status, AppExceptionType exceptionType, T result) {
        var response = ApiResponse.create(exceptionType, result);

        return new ResponseEntity<>(response, status);
    }

    /**
     * 응답 객체를 생성한다.
     */
    public static <T> ResponseEntity<?> of(HttpStatus status, AppExceptionType exceptionType, String message, T result) {
        var response = ApiResponse.create(exceptionType, message, result);

        return new ResponseEntity<>(response, status);
    }

    // -----------------------------------------------------------------------------------------------------------------
}