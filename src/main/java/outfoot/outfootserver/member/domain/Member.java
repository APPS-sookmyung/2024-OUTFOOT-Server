package outfoot.outfootserver.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.friend.domain.Friend;

import java.util.List;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {


    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(unique = true)
    private String username;

    @NotNull @Column(unique = true)
    private String nickname;

    @NotNull @Column(unique = true)
    private String email;

    @NotNull
    private String password;

//    @NotNull
    @Column(unique = true)
    private String code;

    private String myIntro;

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    private List<Friend> fromMember;

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    private List<Friend> toMember;

    @Builder
    public Member(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
