package app.share.session.payload;

import app.api.user.response.UserResultDto;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class SessionUserDto implements Serializable {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userContact;

    public static SessionUserDto of(UserResultDto userDto) {
        return SessionUserDto.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUserName())
                .userEmail(userDto.getUserEmail())
                .userContact(userDto.getUserContact())
                .build();
    }
}