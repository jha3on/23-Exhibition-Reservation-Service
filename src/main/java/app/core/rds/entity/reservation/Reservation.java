package app.core.rds.entity.reservation;

import app.core.rds.entity.user.User;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.reservation.type.ReservationStatusType;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 예매
 */
@Getter
@Entity
@Table(
    name = "tb_reservation",
    indexes = {
        // @Index(name = "tb_reservation_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_reservation_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예매 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @Enumerated(value = EnumType.STRING)
    private ReservationStatusType statusType; // 예매 상태 유형

    @Builder(access = AccessLevel.PUBLIC)
    public Reservation(Long id, User user, Product product, ReservationStatusType statusType) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.statusType = statusType;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Reservation of(User user, Product product) {
        return Reservation.builder()
                .user(user)
                .product(product)
                .statusType(ReservationStatusType.READY)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public Reservation updateStatusType(ReservationStatusType statusType) {
        this.statusType = statusType;
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
}