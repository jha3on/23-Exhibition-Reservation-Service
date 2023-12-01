package app.api.user.request;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class UserSignDto implements Serializable {
    private String userName;
    private String userEmail;
    private String userContact;
    private String userPassword;
}