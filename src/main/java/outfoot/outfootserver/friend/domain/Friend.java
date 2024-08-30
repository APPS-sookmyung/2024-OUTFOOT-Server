package outfoot.outfootserver.friend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.member.domain.Member;

@Entity
@Getter
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Friend extends BaseTimeEntity {

    @Id @Column(name = "friend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;


    @Column(unique = true)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id", nullable = false)
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id", nullable = false)
    private Member toMember;

    @Builder
    public Friend(Member fromMember, Member toMember, String nickname){
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.nickname = nickname;
    }
}
