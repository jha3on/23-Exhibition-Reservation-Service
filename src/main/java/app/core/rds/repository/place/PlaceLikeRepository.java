package app.core.rds.repository.place;

import app.core.rds.entity.place.PlaceLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long> {
    // ...
}