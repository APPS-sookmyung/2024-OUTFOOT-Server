package outfoot.outfootserver.checkpage.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "check_page")
public class CheckPage extends BaseTimeEntity {

    @Id @Column(name = "check_page_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String intro;

    @NotNull
    private String animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "checkPage", cascade = CascadeType.REMOVE)
    private List<Confirm> confirms = new ArrayList<>();

    @Builder
    public CheckPage(String title, String intro, String animal, Member member) {
        this.title = title;
        this.intro = intro;
        this.animal = animal;
        this.member = member;
    }
}
