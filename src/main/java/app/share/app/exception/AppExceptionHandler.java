package app.share.app.exception;

import app.share.app.payload.ApiResponse;
import app.share.app.payload.ApiResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<?> handleAppException(AppException e) {
        var status = e.getStatus();
        var statusType = e.getStatusType();

        e.printStackTrace();
        log.info("[INFO] status: {}, statusType: {}", status, statusType);
        return ApiResponse.of(status, statusType);
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var statusType = ApiResponseType.API_ERROR;

        e.printStackTrace();
        log.info("[INFO] status: {}, statusType: {}", status, statusType);
        return ApiResponse.of(status, statusType);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var statusType = ApiResponseType.API_ERROR;

        e.printStackTrace();
        log.info("[INFO] status: {}, statusType: {}", status, statusType);
        return ApiResponse.of(status, statusType);
    }
}