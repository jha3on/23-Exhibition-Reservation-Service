package app.api.user.request;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class UserLoginDto implements Serializable {
    private String userEmail;
    private String userPassword;
}