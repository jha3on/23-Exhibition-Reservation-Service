package app.core.rds.entity.product;

import app.core.rds.entity.place.Place;
import app.core.rds.entity.product.type.ProductGroupSubtype;
import app.core.rds.entity.product.type.ProductGroupType;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 상품
 */
@Getter
@Entity
@Table(
    name = "tb_product",
    indexes = {
        // @Index(name = "tb_product_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_product_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Place place; // 장소

    @Column(nullable = false)
    private String name; // 상품 이름

    @Column(nullable = false)
    private LocalDateTime dateTo; // 상품 기간

    @Column(nullable = false)
    private LocalDateTime dateFrom; // 상품 기간

    @Enumerated(value = EnumType.STRING)
    private ProductGroupType groupType; // 상품 그룹 유형

    @Enumerated(value = EnumType.STRING)
    private ProductGroupSubtype groupSubtype; // 상품 그룹 유형

    @Builder(access = AccessLevel.PUBLIC)
    public Product(Long id, Place place, String name, LocalDateTime dateTo, LocalDateTime dateFrom, ProductGroupType groupType, ProductGroupSubtype groupSubtype) {
        this.id = id;
        this.place = place;
        this.name = name;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.groupType = groupType;
        this.groupSubtype = groupSubtype;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Product of(Place place, String name, LocalDateTime dateTo, LocalDateTime dateFrom, ProductGroupType groupType, ProductGroupSubtype groupSubtype) {
        return Product.builder()
                .place(place)
                .name(name)
                .dateTo(dateTo)
                .dateFrom(dateFrom)
                .groupType(groupType)
                .groupSubtype(groupSubtype)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}