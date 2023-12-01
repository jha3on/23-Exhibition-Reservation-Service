package app.dummy;

import app.share.storage.FileService;
import app.api.product.ProductApiService;
import app.core.rds.entity.product.type.ProductGroupSubtype;
import app.core.rds.entity.product.type.ProductGroupType;
import app.api.product.request.ProductCreateDto;
import app.api.product.request.ProductOptionCreateDto;
import app.share.app.utility.WebDateUtils;
import app.share.app.utility.WebMapperUtils;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class ProductDataSetter {
    private final FileService fileService;
    private final ProductApiService apiService;

    // -----------------------------------------------------------------------------------------------------------------

    @Order(value = 3)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createProducts() {
        var maps = createProductData();

        for (HashMap<String, Object> outerMap : maps) {
            var product = createProductDto(outerMap);
            var productFiles = new ArrayList<MultipartFile>();
            var productOptions = new ArrayList<ProductOptionCreateDto>();

            if (outerMap.containsKey(PRODUCT_OPTIONS)) {
                var innerList = outerMap.get(PRODUCT_OPTIONS);
                var innerListItemType = new TypeReference<ArrayList<HashMap<String, Object>>>() {};

                for (HashMap<String, Object> innerMap : WebMapperUtils.convert(innerList, innerListItemType)) {
                    productOptions.add(createProductOptionDto(innerMap));
                }
            }

            if (outerMap.containsKey(PRODUCT_FILES)) {
                var innerList = outerMap.get(PRODUCT_FILES);
                var innerListItemType = new TypeReference<ArrayList<HashMap<String, Object>>>() {};

                for (HashMap<String, Object> innerMap : WebMapperUtils.convert(innerList, innerListItemType)) {
                    productFiles.add(createProductFileData(innerMap));
                }
            }

            product.setProductOptions(productOptions);
            apiService.createProductBy(product, productFiles);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ProductCreateDto createProductDto(HashMap<String, Object> map) {
        return ProductCreateDto.builder()
                .placeId(Long.valueOf((String) map.get(PLACE_ID)))
                .productName(String.valueOf(map.get(PRODUCT_NAME)))
                .productDateTo(WebDateUtils.toDateTime(String.valueOf(map.get(PRODUCT_DATE_TO))))
                .productDateFrom(WebDateUtils.toDateTime(String.valueOf(map.get(PRODUCT_DATE_FROM))))
                .productGroupType(ProductGroupType.of(String.valueOf(map.get(PRODUCT_GROUP_TYPE))))
                .productGroupSubtype(ProductGroupSubtype.of(String.valueOf(map.get(PRODUCT_GROUP_SUBTYPE))))
                .build();
    }

    private ProductOptionCreateDto createProductOptionDto(HashMap<String, Object> map) {
        return ProductOptionCreateDto.builder()
                .productOptionName(String.valueOf(map.get(PRODUCT_OPTION_NAME)))
                .productOptionPrice(Integer.valueOf(String.valueOf(map.get(PRODUCT_OPTION_PRICE))))
                .productOptionQuantity(Integer.valueOf(String.valueOf(map.get(PRODUCT_OPTION_QUANTITY))))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createProductData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(PLACE_ID, "1");
            put(PRODUCT_NAME, "아야코 록카쿠, 꿈꾸는 손");
            put(PRODUCT_DATE_TO, "2024.03.24 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.12.02 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_1.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 성인 1인권");
                    put(PRODUCT_OPTION_PRICE, "20000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 청소년 1인권");
                    put(PRODUCT_OPTION_PRICE, "16000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 어린이 1인권");
                    put(PRODUCT_OPTION_PRICE, "12000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 1

        data.add(new HashMap<>() {{
            put(PLACE_ID, "2");
            put(PRODUCT_NAME, "미구엘 슈발리에 디지털 뷰티 SEASON2");
            put(PRODUCT_DATE_TO, "2024.02.12 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.08.01 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PERMANENT");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_2.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 성인 1인권");
                    put(PRODUCT_OPTION_PRICE, "20000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 청소년 1인권");
                    put(PRODUCT_OPTION_PRICE, "15000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 어린이 1인권");
                    put(PRODUCT_OPTION_PRICE, "13000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 2

        data.add(new HashMap<>() {{
            put(PLACE_ID, "3");
            put(PRODUCT_NAME, "Gee Fan Eng 개인전: EYE JUICE");
            put(PRODUCT_DATE_TO, "2023.12.12 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.11.08 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_3.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 아트굿즈 통합권");
                    put(PRODUCT_OPTION_PRICE, "10000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 아트굿즈 관람권");
                    put(PRODUCT_OPTION_PRICE, "7000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 3

        data.add(new HashMap<>() {{
            put(PLACE_ID, "4");
            put(PRODUCT_NAME, "모네 향기를 만나다展");
            put(PRODUCT_DATE_TO, "2023.12.31 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.09.19 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_4.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 관람티켓");
                    put(PRODUCT_OPTION_PRICE, "7000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 관람+시향키트");
                    put(PRODUCT_OPTION_PRICE, "10000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 관람+시향키트(섬유향수)");
                    put(PRODUCT_OPTION_PRICE, "22000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 관람+시향키트(디퓨저)");
                    put(PRODUCT_OPTION_PRICE, "25000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 4

        data.add(new HashMap<>() {{
            put(PLACE_ID, "5");
            put(PRODUCT_NAME, "어반베이스展");
            put(PRODUCT_DATE_TO, "2024.08.15 00:00:00");
            put(PRODUCT_DATE_FROM, "2022.12.16 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_5.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 성인 1인권");
                    put(PRODUCT_OPTION_PRICE, "9000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 어린이/청소년 1인권");
                    put(PRODUCT_OPTION_PRICE, "5000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 5

        data.add(new HashMap<>() {{
            put(PLACE_ID, "6");
            put(PRODUCT_NAME, "원더래빗 고양");
            put(PRODUCT_DATE_TO, "2023.11.30 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.10.27 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_6.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 원더래빗 대인 1인권");
                    put(PRODUCT_OPTION_PRICE, "14000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 원더래빗 소인 1인권");
                    put(PRODUCT_OPTION_PRICE, "12000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 6

        data.add(new HashMap<>() {{
            put(PLACE_ID, "7");
            put(PRODUCT_NAME, "오경훈 : 낙원에서의 인사");
            put(PRODUCT_DATE_TO, "2023.11.25 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.10.27 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PERMANENT");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_7.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 성인 1인권");
                    put(PRODUCT_OPTION_PRICE, "10000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 어린이 1인권");
                    put(PRODUCT_OPTION_PRICE, "5000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 7

        data.add(new HashMap<>() {{
            put(PLACE_ID, "8");
            put(PRODUCT_NAME, "회사 만들기 : Entrepreneurship");
            put(PRODUCT_DATE_TO, "2024.02.18 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.10.28 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PERMANENT");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_8.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 성인 1인권");
                    put(PRODUCT_OPTION_PRICE, "12000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 어린이/청소년/대학(원)생 1인권");
                    put(PRODUCT_OPTION_PRICE, "6000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 8

        data.add(new HashMap<>() {{
            put(PLACE_ID, "9");
            put(PRODUCT_NAME, "반고흐 인 서울");
            put(PRODUCT_DATE_TO, "2024.01.07 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.10.07 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_9.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 기본 1인권");
                    put(PRODUCT_OPTION_PRICE, "12000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 9

        data.add(new HashMap<>() {{
            put(PLACE_ID, "10");
            put(PRODUCT_NAME, "스티키몬스터랩: 스틸 라이프");
            put(PRODUCT_DATE_TO, "2024.01.07 00:00:00");
            put(PRODUCT_DATE_FROM, "2023.09.21 00:00:00");
            put(PRODUCT_GROUP_TYPE, "PRODUCTION");
            put(PRODUCT_GROUP_SUBTYPE, "ALL");
            put(PRODUCT_FILES, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_FILE_NAME, "product_10.jpg");
                    put(PRODUCT_FILE_CONTENT_TYPE, "image/product");
                }});
            }});
            put(PRODUCT_OPTIONS, new ArrayList<HashMap<String, Object>>() {{
                add(new HashMap<>() {{
                    put(PRODUCT_OPTION_NAME, "[일반] 기본 1인권");
                    put(PRODUCT_OPTION_PRICE, "15000");
                    put(PRODUCT_OPTION_QUANTITY, "100");
                }});
            }});
        }}); // 10

        return data;
    }

    private MultipartFile createProductFileData(HashMap<String, Object> map) {
        var filePath = "src/main/resources/data/%s";
        var fileContentType = String.valueOf(map.get(PRODUCT_FILE_CONTENT_TYPE)); // HTML input 태그의 name 값
        var fileName = String.valueOf(map.get(PRODUCT_FILE_NAME));

        return fileService.createBy(fileName, String.format(filePath, fileName), fileContentType);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String PLACE_ID = "place_id";

    private final String PRODUCT_NAME = "product_name";
    private final String PRODUCT_DATE_TO = "product_date_to";
    private final String PRODUCT_DATE_FROM = "product_date_from";
    private final String PRODUCT_GROUP_TYPE = "product_group_type";
    private final String PRODUCT_GROUP_SUBTYPE = "product_group_subtype";

    private final String PRODUCT_OPTIONS = "product_options";
    private final String PRODUCT_OPTION_NAME = "product_option_name";
    private final String PRODUCT_OPTION_PRICE = "product_option_price";
    private final String PRODUCT_OPTION_QUANTITY = "product_option_quantity";

    private final String PRODUCT_FILES = "product_files";
    private final String PRODUCT_FILE_NAME = "product_file_name";
    private final String PRODUCT_FILE_CONTENT_TYPE = "product_file_content_type";

    // -----------------------------------------------------------------------------------------------------------------
}