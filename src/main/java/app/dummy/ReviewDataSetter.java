package app.dummy;

import app.api.review.ReviewApiService;
import app.api.review.request.ReviewCreateDto;
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
public class ReviewDataSetter {
    private final ReviewApiService apiService;

    @Order(value = 6)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createReviews() {
        var maps = createReviewData();

        for (HashMap<String, Object> outerMap : maps) {
            var user = createUserId(outerMap);
            var product = createProductId(outerMap);
            var review = createReviewDto(outerMap);

            apiService.createReviewBy(user, product, review);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private Long createUserId(HashMap<String, Object> map) {
        return Long.valueOf(String.valueOf(map.get(USER_ID)));
    }

    private Long createProductId(HashMap<String, Object> map) {
        return Long.valueOf(String.valueOf(map.get(PRODUCT_ID)));
    }

    private ReviewCreateDto createReviewDto(HashMap<String, Object> map) {
        return ReviewCreateDto.builder()
                .reviewScore(Integer.valueOf(String.valueOf(map.get(REVIEW_SCORE))))
                .reviewContent(String.valueOf(map.get(REVIEW_CONTENT)))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createReviewData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(USER_ID, "1");
            put(PRODUCT_ID, "1");
            put(REVIEW_SCORE, "5");
            put(REVIEW_CONTENT, "한곳에 전시된 거장의 그림을 보니 감동입니다~");
        }}); // user1

        data.add(new HashMap<>() {{
            put(USER_ID, "1");
            put(PRODUCT_ID, "2");
            put(REVIEW_SCORE, "3");
            put(REVIEW_CONTENT, "관람 재미있게 잘 했습니다.");
        }}); // user1

        data.add(new HashMap<>() {{
            put(USER_ID, "2");
            put(PRODUCT_ID, "1");
            put(REVIEW_SCORE, "4");
            put(REVIEW_CONTENT, "꼭 봐야할 전시입니다!");
        }}); // user2

        data.add(new HashMap<>() {{
            put(USER_ID, "3");
            put(PRODUCT_ID, "1");
            put(REVIEW_SCORE, "2");
            put(REVIEW_CONTENT, "큰 소리로 이야기하는 관객들에 대해 별다른 제지가 없던것에 의문이었습니다.");
        }}); // user3

        data.add(new HashMap<>() {{
            put(USER_ID, "4");
            put(PRODUCT_ID, "2");
            put(REVIEW_SCORE, "5");
            put(REVIEW_CONTENT, "정말 좋은 전시였습니다!");
        }}); // user4

        return data;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String USER_ID = "user_id";
    private final String PRODUCT_ID = "product_id";
    private final String REVIEW_SCORE = "review_score";
    private final String REVIEW_CONTENT = "review_content";

    // -----------------------------------------------------------------------------------------------------------------
}