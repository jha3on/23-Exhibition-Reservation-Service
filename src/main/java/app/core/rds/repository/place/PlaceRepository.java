package app.core.rds.repository.place;

import app.core.rds.entity.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // ...
}