package app.dummy;

import app.api.promotion.PromotionApiService;
import app.api.promotion.request.PromotionCreateDto;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class PromotionDataSetter {
    private final PromotionApiService apiService;

    // -----------------------------------------------------------------------------------------------------------------

    @Order(value = 4)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createPromotions() {
        var maps = createPromotionData();

        for (HashMap<String, Object> outerMap : maps) {
            var promotion = createPromotionDto(outerMap);
            var promotionFiles = new ArrayList<MultipartFile>();

            apiService.createPromotionBy(promotion, promotionFiles);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private PromotionCreateDto createPromotionDto(HashMap<String, Object> map) {
        return PromotionCreateDto.builder()
                .productId(Long.valueOf((String) map.get(PRODUCT_ID)))
                .promotionGroupType(PromotionGroupType.of(String.valueOf(map.get(PROMOTION_GROUP_TYPE))))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createPromotionData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(PRODUCT_ID, "4");
            put(PROMOTION_GROUP_TYPE, "DAILY_BEST");
        }}); // 1

        data.add(new HashMap<>() {{
            put(PRODUCT_ID, "6");
            put(PROMOTION_GROUP_TYPE, "DAILY_BEST");
        }}); // 2

        data.add(new HashMap<>() {{
            put(PRODUCT_ID, "7");
            put(PROMOTION_GROUP_TYPE, "DAILY_BEST");
        }}); // 3

        return data;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String PRODUCT_ID = "product_id";
    private final String PROMOTION_GROUP_TYPE = "promotion_group_type";

    // -----------------------------------------------------------------------------------------------------------------
}