package app.share.session;

import app.api.user.response.UserResultDto;
import app.share.session.payload.SessionUserDto;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

    /**
     * 세션 값을 생성한다.
     */
    public static void setUserBy(UserResultDto userDto) {
        var httpSession = SessionUtils.getHttpSession();

        httpSession.setAttribute(SessionKey.USER, SessionUserDto.of(userDto));
    }

    /**
     * 세션 값을 조회한다.
     */
    public static SessionUserDto getAttributeBy(String key) {
        var httpSession = SessionUtils.getHttpSession();
        if (Objects.isNull(httpSession.getAttribute(key))) {
            throw AppException.of401(AppExceptionType.USER_SESSION_GET_ERROR);
        }

        return (SessionUserDto) httpSession.getAttribute(key);
    }

    /**
     * 세션 값을 조회한다.
     */
    public static Optional<SessionUserDto> getAttributeOptBy(String key) {
        var httpSession = SessionUtils.getHttpSession();

        return Optional.ofNullable((SessionUserDto) httpSession.getAttribute(key));
    }

    /**
     * 세션 값을 확인한다.
     */
    public static Boolean hasAttributeBy(String key) {
        var httpSession = SessionUtils.getHttpSession();

        return !Objects.isNull(httpSession.getAttribute(key));
    }

    /**
     * 세션 값을 삭제한다.
     */
    public static void unsetAttributeBy(String key) {
        var httpSession = SessionUtils.getHttpSession();

        httpSession.removeAttribute(key);
    }

    public static HttpSession getHttpSession() {
        var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        return attributes.getRequest().getSession();
    }

    public static HttpServletRequest getHttpRequest() {
        var attributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());

        return attributes.getRequest();
    }
}