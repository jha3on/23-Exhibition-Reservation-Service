package app.core.rds.service.place;

import app.core.rds.entity.place.Place;
import app.core.rds.entity.place.PlaceLike;
import app.core.rds.entity.user.User;
import app.core.rds.repository.place.PlaceLikeRepository;
import app.core.rds.repository.place.query.PlaceRepositoryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceLikeService {
    private final PlaceLikeRepository placeLikeRepository;
    private final PlaceRepositoryQuery placeRepositoryQuery;

    /**
     * [Fix] PlaceLike 객체를 조회한다.
     */
    @Transactional
    public Boolean existsBy(Long userId, Long placeId) {
        return placeRepositoryQuery.existsBy(userId, placeId);
    }

    /**
     * [Fix] PlaceLike 객체를 저장/삭제한다.
     */
    @Transactional
    public Boolean toggleBy(User user, Place place) {
        var placeLikeOpt = placeRepositoryQuery.getBy(user.getId(), place.getId());

        if (placeLikeOpt.isEmpty()) {
            placeLikeRepository.save(createBy(user, place));
            return true;
        } else {
            placeLikeRepository.delete(placeLikeOpt.get());
            return false;
        }
    }

    private PlaceLike createBy(User user, Place place) {
        return PlaceLike.of(user, place);
    }
}