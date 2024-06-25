package outfoot.outfootserver.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import outfoot.outfootserver.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {


    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID userId;

    @NotNull @Column(unique = true)
    private String username;

    @NotNull @Column(unique = true)
    private String nickname;

    @NotNull @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @Column(name = "provider", nullable = false, length = 10)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 50)
    private String providerId;

//    @NotNull
    @Column(unique = true)
    private String code;

    private String myIntro;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @Builder
    public Member(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
