package app.core.rds.entity.file;

import app.core.rds.entity.place.Place;
import app.core.rds.AbstractEntity;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.promotion.Promotion;
import app.core.rds.entity.file.type.FileGroupSubtype;
import app.core.rds.entity.file.type.FileGroupType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 파일
 */
@Getter
@Entity
@Table(
    name = "tb_file",
    indexes = {
        // @Index(name = "tb_file_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_file_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 파일 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Place place; // 장소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Promotion promotion; // 프로모션

    @Column(nullable = false)
    private String path; // 파일 경로

    @Enumerated(value = EnumType.STRING)
    private FileGroupType groupType; // 파일 그룹 유형

    @Enumerated(value = EnumType.STRING)
    private FileGroupSubtype groupSubtype; // 파일 그룹 유형

    @Builder(access = AccessLevel.PUBLIC)
    public File(Long id, Place place, Product product, Promotion promotion, String path, FileGroupType groupType, FileGroupSubtype groupSubtype) {
        this.id = id;
        this.place = place;
        this.product = product;
        this.promotion = promotion;
        this.path = path;
        this.groupType = groupType;
        this.groupSubtype = groupSubtype;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static File of(Place place, String path, FileGroupType groupType) {
        return File.builder()
                .place(place)
                .path(path)
                .groupType(groupType)
                .groupSubtype(FileGroupSubtype.PLACE)
                .build();
    }

    public static File of(Product product, String path, FileGroupType groupType) {
        return File.builder()
                .product(product)
                .path(path)
                .groupType(groupType)
                .groupSubtype(FileGroupSubtype.PRODUCT)
                .build();
    }

    public static File of(Promotion promotion, String path, FileGroupType groupType) {
        return File.builder()
                .promotion(promotion)
                .path(path)
                .groupType(groupType)
                .groupSubtype(FileGroupSubtype.PROMOTION)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public Boolean equals(FileGroupType groupType) {
        return Objects.equals(this.groupType, groupType);
    }

    public Boolean equals(FileGroupSubtype groupSubtype) {
        return Objects.equals(this.groupSubtype, groupSubtype);
    }

    public Boolean equals(FileGroupType groupType, FileGroupSubtype groupSubtype) {
        if (!equals(groupType)) return false;
        if (!equals(groupSubtype)) return false;

        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------
}