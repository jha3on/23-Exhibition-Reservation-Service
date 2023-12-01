package app.core.rds.service.place;

import app.api.place.request.PlaceCreateDto;
import app.core.rds.entity.place.Place;
import app.core.rds.repository.place.PlaceRepository;
import app.core.rds.repository.place.query.PlaceRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceRepositoryQuery placeRepositoryQuery;

    /**
     * [Fix] Place 객체를 저장한다.
     */
    @Transactional
    public Place saveBy(PlaceCreateDto placeDto) {
        var place = createBy(placeDto);

        return placeRepository.save(place);
    }

    /**
     * [Fix] Place 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Place getBy(Long placeId) {
        var placeOpt = placeRepositoryQuery.getByPlaceId(placeId);

        if (placeOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.PLACE_GET_ERROR);
        } else return placeOpt.get();
    }

    /**
     * [Fix] Place 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Place getByProductId(Long productId) {
        var placeOpt = placeRepositoryQuery.getByProductId(productId);

        if (placeOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.PLACE_GET_ERROR);
        } else return placeOpt.get();
    }

    /**
     * [Fix] Place 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Place> getAllBy() {
        return placeRepositoryQuery.getAllBy();
    }

    /**
     * [Fix] Place 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Place> getAllBy(List<Long> productIds) {
        return placeRepositoryQuery.getAllByProductIds(productIds);
    }

    private Place createBy(PlaceCreateDto placeDto) {
        return Place.of(
                placeDto.getPlaceName(),
                placeDto.getPlaceAddress(),
                placeDto.getPlaceContact(),
                placeDto.getPlaceGroupType(),
                placeDto.getPlaceGroupSubtype()
        );
    }
}