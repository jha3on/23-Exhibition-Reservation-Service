package app.api.user.response;

import app.core.rds.entity.user.User;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class UserResultDto implements Serializable {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userContact;

    public static UserResultDto of(User user) {
        return UserResultDto.builder()
                .userId(user.getId())
                .userName(user.getName())
                .userEmail(user.getEmail())
                .userContact(user.getContact())
                .build();
    }
}