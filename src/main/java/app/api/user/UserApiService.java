package app.api.user;

import app.api.user.request.UserLoginDto;
import app.api.user.request.UserSignDto;
import app.api.user.response.UserResultDto;
import app.api.user.response.UserSearchResultDto;
import app.core.rds.service.user.UserService;
import app.core.rds.entity.user.type.UserSearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserService userService;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * [Fix] 회원을 생성한다.
     */
    @Transactional
    public UserResultDto createUserBy(UserSignDto userDto) {
        var user = userService.saveBy(userDto);

        return UserResultDto.of(user);
    }

    /**
     * [Fix] 회원을 인증한다.
     */
    @Transactional
    public UserResultDto authenticateUserBy(UserLoginDto userDto) {
        var user = userService.getBy(userDto);

        return UserResultDto.of(user);
    }

    /**
     * [Fix] 회원을 조회한다.
     */
    @Transactional(readOnly = true)
    public UserSearchResultDto searchUserBy(String userSearch, UserSearchType userSearchType) {
        var user = userService.existsBy(userSearch, userSearchType);

        return UserSearchResultDto.of(user);
    }

    // -----------------------------------------------------------------------------------------------------------------
}