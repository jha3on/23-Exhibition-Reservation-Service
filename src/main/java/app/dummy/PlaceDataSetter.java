package app.dummy;

import app.share.storage.FileService;
import app.api.place.PlaceApiService;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.api.place.request.PlaceCreateDto;
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
public class PlaceDataSetter {
    private final FileService fileService;
    private final PlaceApiService apiService;

    // -----------------------------------------------------------------------------------------------------------------

    @Order(value = 2)
    @EventListener(value = ApplicationReadyEvent.class)
    public void createPlaces() {
        System.out.println("asdasdasdasd");
        var maps = createPlaceData();

        for (HashMap<String, Object> outerMap : maps) {
            var place = createPlaceDto(outerMap);
            var placeFiles = new ArrayList<MultipartFile>();

            if (outerMap.containsKey(PLACE_FILES)) {
                var innerList = outerMap.get(PLACE_FILES);
                var innerListItemType = new TypeReference<ArrayList<HashMap<String, Object>>>() {};

                for (HashMap<String, Object> innerMap : WebMapperUtils.convert(innerList, innerListItemType)) {
                    placeFiles.add(createPlaceFileData(innerMap));
                }
            }

            apiService.createPlaceBy(place, placeFiles);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private PlaceCreateDto createPlaceDto(HashMap<String, Object> map) {
        return PlaceCreateDto.builder()
                .placeName(String.valueOf(map.get(PLACE_NAME)))
                .placeContact(String.valueOf(map.get(PLACE_CONTACT)))
                .placeAddress(String.valueOf(map.get(PLACE_ADDRESS)))
                .placeGroupType(PlaceGroupType.of(String.valueOf(map.get(PLACE_GROUP_TYPE))))
                .placeGroupSubtype(PlaceGroupSubtype.of(String.valueOf(map.get(PLACE_GROUP_SUBTYPE))))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ArrayList<HashMap<String, Object>> createPlaceData() {
        var data = new ArrayList<HashMap<String, Object>>();

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "예술의전당 한가람미술관");
            put(PLACE_CONTACT, "1668-1352");
            put(PLACE_ADDRESS, "서울특별시 서초구 남부순환로 2406");
            put(PLACE_GROUP_TYPE, "MUSEUM");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 1

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "아라아트센터");
            put(PLACE_CONTACT, "02-733-1981");
            put(PLACE_ADDRESS, "서울특별시 종로구 인사동9길 26");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 2

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "라이크디즈1601");
            put(PLACE_CONTACT, "02-3280-1404");
            put(PLACE_ADDRESS, "서울특별시 중구 서소문로 116");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 3

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "헤이리스");
            put(PLACE_CONTACT, "070-4136-4300");
            put(PLACE_ADDRESS, "경기 파주시 탄현면 헤이리마을길 7");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "GYEONGGI");
        }}); // 4

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "어반베이스 동탄");
            put(PLACE_CONTACT, "070-4173-6750");
            put(PLACE_ADDRESS, "경기 용인시 처인구 남사읍 봉무로153번길 79");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "GYEONGGI");
        }}); // 5

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "매직플로우");
            put(PLACE_CONTACT, "031-5173-3457");
            put(PLACE_ADDRESS, "경기 고양시 덕양구 고양대로 1955");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "GYEONGGI");
        }}); // 6

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "ELIGERE GALLERY");
            put(PLACE_CONTACT, "02-518-4287");
            put(PLACE_ADDRESS, "서울특별시 강남구 압구정로79길 55");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 7

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "피크닉");
            put(PLACE_CONTACT, "02-318-3233");
            put(PLACE_ADDRESS, "서울 중구 퇴계로6가길 30");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 8

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "띠아트");
            put(PLACE_CONTACT, "0507-1354-9894");
            put(PLACE_ADDRESS, "서울 마포구 와우산로21길 20-11");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 9

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "그라운드시소 성수");
            put(PLACE_CONTACT, "070-4473-9742");
            put(PLACE_ADDRESS, "서울 성동구 아차산로17길 49");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 10

        data.add(new HashMap<>() {{
            put(PLACE_NAME, "띠아트");
            put(PLACE_CONTACT, "0507-1354-9894");
            put(PLACE_ADDRESS, "서울 마포구 와우산로21길 20-11");
            put(PLACE_GROUP_TYPE, "GALLERY");
            put(PLACE_GROUP_SUBTYPE, "SEOUL");
        }}); // 11

        return data;
    }

    private MultipartFile createPlaceFileData(HashMap<String, Object> map) {
        var filePath = "src/main/resources/data/%s";
        var fileContentType = String.valueOf(map.get(PLACE_FILE_CONTENT_TYPE)); // HTML input 태그의 name 값
        var fileName = String.valueOf(map.get(PLACE_FILE_NAME));

        return fileService.createBy(fileName, String.format(filePath, fileName), fileContentType);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private final String PLACE_NAME = "place_name";
    private final String PLACE_CONTACT = "place_contact";
    private final String PLACE_ADDRESS = "place_address";
    private final String PLACE_GROUP_TYPE = "place_group_type";
    private final String PLACE_GROUP_SUBTYPE = "place_group_subtype";

    private final String PLACE_FILES = "place_files";
    private final String PLACE_FILE_NAME = "place_file_name";
    private final String PLACE_FILE_CONTENT_TYPE = "place_file_content_type";

    // -----------------------------------------------------------------------------------------------------------------
}