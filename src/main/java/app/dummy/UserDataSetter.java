package app.dummy;

import app.api.user.UserApiService;
import app.api.user.request.UserSignDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UserDataSetter {
    private final UserApiService apiService;

    // -----------------------------------------------------------------------------------------------------------------

    @Order(value = 1)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createUsers() {
        var maps = createUserData();

        for (HashMap<String, Object> map : maps) {
            var user = createUserSignDto(map);

            apiService.createUserBy(user);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private UserSignDto createUserSignDto(HashMap<String, Object> map) {
        return UserSignDto.builder()
                .userName(String.valueOf(map.get(USER_NAME)))
                .userEmail(String.valueOf(map.get(USER_EMAIL)))
                .userContact(String.valueOf(map.get(USER_CONTACT)))
                .userPassword(String.valueOf(map.get(USER_PASSWORD)))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createUserData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(USER_NAME, "사용자");
            put(USER_EMAIL, "user1@naver.com");
            put(USER_CONTACT, "010-0000-0001");
            put(USER_PASSWORD, "user1");
        }});

        data.add(new HashMap<>() {{
            put(USER_NAME, "사용자");
            put(USER_EMAIL, "user2@naver.com");
            put(USER_CONTACT, "010-0000-0002");
            put(USER_PASSWORD, "user2");
        }});

        data.add(new HashMap<>() {{
            put(USER_NAME, "사용자");
            put(USER_EMAIL, "user3@naver.com");
            put(USER_CONTACT, "010-0000-0003");
            put(USER_PASSWORD, "user3");
        }});

        data.add(new HashMap<>() {{
            put(USER_NAME, "사용자");
            put(USER_EMAIL, "user4@naver.com");
            put(USER_CONTACT, "010-0000-0004");
            put(USER_PASSWORD, "user4");
        }});

        data.add(new HashMap<>() {{
            put(USER_NAME, "사용자");
            put(USER_EMAIL, "user5@naver.com");
            put(USER_CONTACT, "010-0000-0005");
            put(USER_PASSWORD, "user5");
        }});

        return data;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String USER_NAME = "user_name";
    private final String USER_EMAIL = "user_email";
    private final String USER_CONTACT = "user_contact";
    private final String USER_PASSWORD = "user_password";

    // -----------------------------------------------------------------------------------------------------------------
}