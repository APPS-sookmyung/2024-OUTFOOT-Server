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
import outfoot.outfootserver.member.dto.MyPageRequest;

import java.util.ArrayList;
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

    //@NotNull
    @Column(unique = true)
    private String nickname;

    //@NotNull
    @Column
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

    @Column
    private String myIntro;

    private String imageUrl;


    @OneToMany(mappedBy = "fromMember")
    private List<Friend> fromMember = new ArrayList<>();

    @OneToMany(mappedBy = "toMember")
    private List<Friend> toMember = new ArrayList<>();

    @Builder
    public Member(UUID username, String nickname, String email, String myIntro, String password, String provider, String providerId, String code, String imageUrl) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.myIntro = myIntro;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.code = code;
        this.imageUrl = imageUrl;
    }

    public void updateMember(MyPageRequest dto, String imageUrl){
        if ( dto.nickname() != null ) {
            this.nickname = dto.nickname();
        }
        if ( dto.email() != null ) {
            this.email = dto.email();
        }
        if ( dto.myIntro() != null ) {
            this.myIntro = dto.myIntro();
        }
        if ( dto.password() != null ) {
            this.password = dto.password();
        }
        if ( imageUrl != null ) {
            this.imageUrl = imageUrl;
        }
    }
}
