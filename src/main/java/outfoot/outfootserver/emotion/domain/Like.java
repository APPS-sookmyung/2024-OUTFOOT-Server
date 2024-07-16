package outfoot.outfootserver.emotion.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.member.domain.Member;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like extends BaseTimeEntity {

    @Id @Column(name = "likes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_page_id", nullable = false)
    private CheckPage checkPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_id", nullable = false)
    private Confirm confirm;


    @Builder
    public Like(Member member, CheckPage checkPage, Confirm confirm) {
        this.member = member;
        this.checkPage = checkPage;
        this.confirm = confirm;
    }
}
