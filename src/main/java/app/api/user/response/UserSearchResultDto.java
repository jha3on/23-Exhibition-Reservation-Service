package app.api.user.response;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class UserSearchResultDto implements Serializable {
    private Boolean userResult;

    public static UserSearchResultDto of(Boolean userResult) {
        return UserSearchResultDto.builder()
                .userResult(userResult)
                .build();
    }
}