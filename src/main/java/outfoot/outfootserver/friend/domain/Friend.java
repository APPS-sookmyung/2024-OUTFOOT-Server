package outfoot.outfootserver.friend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.member.domain.Member;

@Entity
@Getter
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseTimeEntity {

    @Id @Column(name = "friend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @Id @Column(name = "member_id")
    private Long memberId;

    @Id @Column(name = "friend_member_id")
    private Long friendMemberId;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    @ManyToOne
    private Member member;

    @Builder
    public Friend(String nickname){
        this.nickname = nickname;
    }
}
