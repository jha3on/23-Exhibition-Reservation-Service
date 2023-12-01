package app.core.rds.entity.user;

import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 회원
 */
@Getter
@Entity
@Table(
    name = "tb_user",
    indexes = {
        // @Index(name = "tb_user_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "tb_user_uk_1", columnNames = {"email"}),
        @UniqueConstraint(name = "tb_user_uk_2", columnNames = {"contact"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 ID

    @Column(nullable = false)
    private String name; // 회원 이름

    @Column(nullable = false)
    private String email; // 회원 이메일

    @Column(nullable = false)
    private String contact; // 회원 연락처

    @Column(nullable = false)
    private String password; // 회원 비밀번호

    @Builder(access = AccessLevel.PUBLIC)
    public User(Long id, String name, String email, String contact, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static User of(String name, String email, String contact, String password) {
        return User.builder()
                .name(name)
                .email(email)
                .contact(contact)
                .password(password)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}