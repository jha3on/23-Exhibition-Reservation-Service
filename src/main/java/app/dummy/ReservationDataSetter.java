package app.dummy;

import app.api.reservation.ReservationApiService;
import app.api.reservation.request.ReservationCreateDto;
import app.api.reservation.request.ReservationOptionCreateDto;
import app.share.app.utility.WebMapperUtils;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class ReservationDataSetter {
    private final ReservationApiService apiService;

    // -----------------------------------------------------------------------------------------------------------------

    @Order(value = 5)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createReservations() {
        var maps = createReservationData();

        for (HashMap<String, Object> outerMap : maps) {
            var user = createUserId(outerMap);
            var product = createProductId(outerMap);
            var reservation = createReservationDto(outerMap);
            var reservationOptions = new ArrayList<ReservationOptionCreateDto>();

            if (outerMap.containsKey(RESERVATION_OPTIONS)) {
                var innerList = outerMap.get(RESERVATION_OPTIONS);
                var innerListItemType = new TypeReference<ArrayList<HashMap<String, Object>>>() {};

                for (HashMap<String, Object> innerMap : WebMapperUtils.convert(innerList, innerListItemType)) {
                    reservationOptions.add(createReservationOptionDto(innerMap));
                }
            }

            reservation.setReservationOptions(reservationOptions);
            apiService.createReservationBy(user, product, reservation);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private Long createUserId(HashMap<String, Object> map) {
        return Long.valueOf(String.valueOf(map.get(USER_ID)));
    }

    private Long createProductId(HashMap<String, Object> map) {
        return Long.valueOf(String.valueOf(map.get(PRODUCT_ID)));
    }

    private ReservationCreateDto createReservationDto(HashMap<String, Object> map) {
        return ReservationCreateDto.builder()
                .build();
    }

    private ReservationOptionCreateDto createReservationOptionDto(HashMap<String, Object> map) {
        return ReservationOptionCreateDto.builder()
                .productOptionId(Long.valueOf(String.valueOf(map.get(PRODUCT_OPTION_ID))))
                .productOptionQuantity(Integer.valueOf(String.valueOf(map.get(PRODUCT_OPTION_QUANTITY))))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createReservationData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(USER_ID, "1");
            put(PRODUCT_ID, "1");
            put(RESERVATION_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "1");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
            }});
        }}); // user1

        data.add(new HashMap<>() {{
            put(USER_ID, "1");
            put(PRODUCT_ID, "2");
            put(RESERVATION_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "4");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
            }});
        }}); // user1

        data.add(new HashMap<>() {{
            put(USER_ID, "2");
            put(PRODUCT_ID, "1");
            put(RESERVATION_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "2");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
            }});
        }}); // user2

        data.add(new HashMap<>() {{
            put(USER_ID, "3");
            put(PRODUCT_ID, "1");
            put(RESERVATION_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "1");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "3");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
            }});
        }}); // user3

        data.add(new HashMap<>() {{
            put(USER_ID, "4");
            put(PRODUCT_ID, "2");
            put(RESERVATION_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "4");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_ID, "5");
                    put(PRODUCT_OPTION_QUANTITY, "2");
                }});
            }});
        }}); // user4

        return data;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String USER_ID = "user_id";
    private final String PRODUCT_ID = "product_id";
    private final String PRODUCT_OPTION_ID = "product_option_id";
    private final String RESERVATION_OPTIONS = "reservation_options";
    private final String PRODUCT_OPTION_QUANTITY = "reservation_option_quantity";

    // -----------------------------------------------------------------------------------------------------------------
}