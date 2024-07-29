package outfoot.outfootserver.confirm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.BaseTimeEntity;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "confirm")
public class Confirm extends BaseTimeEntity {

    @Id @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    private Date date;

    private int order;

    @ManyToOne
    @JoinColumn(name = "check_page_id") //id
    private CheckPage checkPage; //객체

    @Builder
    public Confirm(String memo, Date date, int order, CheckPage checkPage) {
        this.memo = memo;
        this.date = date;
        this.order = order;
        this.checkPage = checkPage;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
