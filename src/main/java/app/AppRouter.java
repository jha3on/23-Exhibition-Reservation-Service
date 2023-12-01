package app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class AppRouter {

    /** [Fix] 메인 페이지 */
    @GetMapping(value = {"/"})
    public String appPage() {
        return "/page/pageHome";
    }

    /** [Fix] 회원 페이지 */
    @GetMapping(value = {"/user"})
    public String userPage() {
        return "/page/pageUser";
    }

    /** [Fix] 회원 가입 페이지 */
    @GetMapping(value = {"/user/sign"})
    public String userSignPage() {
        return "/page/pageUserSign";
    }

    /** [Fix] 회원 로그인 페이지 */
    @GetMapping(value = {"/user/login"})
    public String userLoginPage() {
        return "/page/pageUserLogin";
    }

    /** [Fix] 상품 페이지 */
    @GetMapping(value = {"/product/{id}"})
    public String productPage(@PathVariable Long id) { // Product ID
        return "/page/pageProduct";
    }

    /** [Fix] 상품 목록 페이지 */
    @GetMapping(value = {"/product/list"})
    public String productListPage() {
        return "/page/pageProductList";
    }

    /** [Fix] 예매 페이지 */
    @GetMapping(value = {"/reservation/{id}"})
    public String reservationPage(@PathVariable Long id) { // Product ID
        return "/page/pageReservation";
    }

    /** [Fix] 예매 목록 페이지 */
    @GetMapping(value = {"/reservation/list"})
    public String reservationListPage() {
        return "/page/pageReservationList";
    }

    /** [Fix] 리뷰 목록 페이지 */
    @GetMapping(value = {"/review/list"})
    public String reviewListPage() {
        return "/page/pageReviewList";
    }

    /** [Fix] 검색 페이지 */
    @GetMapping(value = {"/search"})
    public String searchPage() {
        return "/page/pageSearch";
    }

    /** [Fix] 오류 페이지 */
    @GetMapping(value = {"/error"})
    public String errorPage() {
        return "/page/pageError";
    }
}