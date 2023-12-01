package app.api.user;

import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.api.user.request.UserLoginDto;
import app.api.user.request.UserSignDto;
import app.core.rds.entity.user.type.UserSearchType;
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
public class UserApi {
    private final UserApiService apiService;

    /** [Fix] 회원 세션 */
    @GetMapping(value = {"/api/users/session"})
    public ResponseEntity<?> getUserSession() {
        var result = SessionUtils.getAttributeBy(SessionKey.USER);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 회원 검색 (ex: /pageApi/users/search?type=email&value=user1@test.com) */
    @GetMapping(value = {"/api/users/search"})
    public ResponseEntity<?> getUserSearch(
        @RequestParam(value = "value", required = false) String userSearch,
        @RequestParam(value = "type", required = false) UserSearchType userSearchType
    ) {
        var result = apiService.searchUserBy(userSearch, userSearchType);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 회원 가입 */
    @PostMapping(value = {"/api/users/sign"})
    public ResponseEntity<?> createUser(
        @RequestBody UserSignDto userDto
    ) {
        var result = apiService.createUserBy(userDto);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 회원 로그인 */
    @PostMapping(value = {"/api/users/login"})
    public ResponseEntity<?> authenticateUser(
        @RequestBody UserLoginDto userDto
    ) {
        var result = apiService.authenticateUserBy(userDto);
        SessionUtils.setUserBy(result);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 회원 로그아웃 */
    @PostMapping(value = {"/api/users/logout"})
    public ResponseEntity<?> deauthenticateUser() {
        SessionUtils.unsetAttributeBy(SessionKey.USER);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK);
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