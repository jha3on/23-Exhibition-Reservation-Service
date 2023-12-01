package app.core.rds.repository.place.query;

import app.core.rds.entity.place.Place;
import app.core.rds.entity.place.PlaceLike;

import java.util.List;
import java.util.Optional;

public interface PlaceRepositoryQuery {

    /** [Fix] Place 객체 조회 */
    Optional<Place> getByPlaceId(Long placeId);

    /** [Fix] Place 객체 조회 */
    Optional<Place> getByProductId(Long productId);

    /** [Fix] Place 컬렉션 조회 */
    List<Place> getAllBy();

    /** [Fix] Place 컬렉션 조회 */
    List<Place> getAllByProductIds(List<Long> productIds);

    /** [Fix] PlaceLike 객체 중복 조회 */
    Boolean existsBy(Long userId, Long placeId);

    /** [Fix] PlaceLike 객체 조회 */
    Optional<PlaceLike> getBy(Long userId, Long placeId);
}