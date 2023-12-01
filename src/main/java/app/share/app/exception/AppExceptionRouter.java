package app.share.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class AppExceptionRouter implements ErrorController {

    @RequestMapping(value = {"/error"})
    public String handleException() {
        return String.format("%s", "/pageError");
    }
}