package app.core.rds.service.user;

import app.api.user.request.UserLoginDto;
import app.api.user.request.UserSignDto;
import app.core.rds.entity.user.User;
import app.core.rds.entity.user.type.UserSearchType;
import app.core.rds.repository.user.UserRepository;
import app.core.rds.repository.user.query.UserRepositoryQuery;
import app.core.rds.service.user.validator.UserValidator;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserRepositoryQuery userRepositoryQuery;

    /**
     * [Fix] User 객체를 저장한다.
     */
    @Transactional
    public User saveBy(UserSignDto userDto) {
        var user = createBy(userDto);

        return userRepository.save(user);
    }

    /**
     * [Fix] User 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public User getBy(Long userId) {
        var userOpt = userRepositoryQuery.getBy(userId);

        if (userOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.USER_GET_ERROR);
        } else return userOpt.get();
    }

    /**
     * [Fix] User 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public User getBy(UserLoginDto userDto) {
        var userOpt = userRepositoryQuery.getBy(userDto.getUserEmail(), userDto.getUserPassword());

        if (userOpt.isEmpty()) {
            throw AppException.of401(AppExceptionType.USER_LOGIN_ERROR);
        } else return userOpt.get();
    }

    /**
     * [Fix] User 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Boolean existsBy(String userSearch, UserSearchType userSearchType) {
        return userRepositoryQuery.existsBy(userSearch, userSearchType);
    }

    private User createBy(UserSignDto userDto) {
        return User.of(
                userDto.getUserName(),
                userDto.getUserEmail(),
                userDto.getUserContact(),
                userDto.getUserPassword()
        );
    }
}