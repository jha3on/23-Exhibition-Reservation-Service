package app.share.app.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {
    public static String emailMasking(String email) {
        var emailSplit = email.split("@");

        return String.format("%s***@%s", emailSplit[0].substring(0, 2), emailSplit[1].substring(0, 2));
    }
}