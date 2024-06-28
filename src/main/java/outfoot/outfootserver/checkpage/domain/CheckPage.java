package outfoot.outfootserver.checkpage.domain;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "check_page")
public class CheckPage extends BaseTimeEntity {

    @Id @Column(name = "check_page_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;

    @NotBlank @Column(unique = true)
    private String title;

    private String intro;

    private int animal_position = 1;

    @NotNull
    private String animal;

    @Builder
//    public CheckPage(Member member, String title, String intro, Animal animal) {
    public CheckPage(String title, String intro, String animal) {
//        this.member = member;
        this.title = title;
        this.intro = intro;
        this.animal = animal;
    }
}
