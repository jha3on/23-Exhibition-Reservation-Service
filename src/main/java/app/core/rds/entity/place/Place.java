package app.core.rds.entity.place;

import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 장소
 */
@Getter
@Entity
@Table(
    name = "tb_place",
    indexes = {
        // @Index(name = "tb_place_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_place_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 장소 ID

    @Column(nullable = false)
    private String name; // 장소 이름

    @Column(nullable = false)
    private String address; // 장소 주소

    @Column(nullable = false)
    private String contact; // 장소 연락처

    @Enumerated(value = EnumType.STRING)
    private PlaceGroupType groupType; // 장소 그룹 유형

    @Enumerated(value = EnumType.STRING)
    private PlaceGroupSubtype groupSubtype; // 장소 그룹 유형

    @Builder(access = AccessLevel.PUBLIC)
    public Place(Long id, String name, String address, String contact, PlaceGroupType groupType, PlaceGroupSubtype groupSubtype) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.groupType = groupType;
        this.groupSubtype = groupSubtype;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Place of(String name, String address, String contact, PlaceGroupType groupType, PlaceGroupSubtype groupSubtype) {
        return Place.builder()
                .name(name)
                .address(address)
                .contact(contact)
                .groupType(groupType)
                .groupSubtype(groupSubtype)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}