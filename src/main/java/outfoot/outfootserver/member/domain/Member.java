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
import outfoot.outfootserver.friend.domain.Friend;

import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {


    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", columnDefinition = "BINARY(16)", unique = true)
    private UUID username;

    @NotNull @Column
    private String nickname;

    //@NotNull
    @Column(unique = true)
    private String email;

    // @NotNull
    private String password;

     @Column(name = "provider", nullable = false, length = 10)
    private String provider;

//    @Column(name = "provider_id", nullable = false, length = 50)
    @Column(name = "provider_id", length = 50)
    private String providerId;

//    @NotNull
    @Column(unique = true)
    private String code;

    private String myIntro;

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    private List<Friend> fromMember;

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    private List<Friend> toMember;

    @Builder
    public Member(UUID username, String nickname, String provider, String providerId, String code) {
        this.username = username;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.code = code;
    }
}
