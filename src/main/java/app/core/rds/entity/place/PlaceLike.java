package app.core.rds.entity.place;

import app.core.rds.AbstractLogEntity;
import app.core.rds.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 관심 장소
 */
@Getter
@Entity
@Table(
    name = "tb_place_like",
    indexes = {
        // @Index(name = "tb_place_like_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_place_like_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceLike extends AbstractLogEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 관심 장소 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Place place; // 장소

    @Builder(access = AccessLevel.PUBLIC)
    public PlaceLike(Long id, User user, Place place) {
        this.id = id;
        this.user = user;
        this.place = place;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static PlaceLike of(User user, Place place) {
        return PlaceLike.builder()
                .user(user)
                .place(place)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}