package outfoot.outfootserver.confirm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.common.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "confirm")
public class Confirm extends BaseTimeEntity {

    @Id @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    @Builder
    public Confirm(String memo) {
        this.memo = memo;
    }
}
