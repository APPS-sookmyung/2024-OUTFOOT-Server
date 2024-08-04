package outfoot.outfootserver.confirm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.BaseTimeEntity;
import outfoot.outfootserver.emotion.domain.Dislike;
import outfoot.outfootserver.emotion.domain.Like;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "confirm")
public class Confirm extends BaseTimeEntity {

    @Id @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    private String imageUrl;

    @Column(name = "orders")
    private Long order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_page_id") //id
    private CheckPage checkPage; //객체

    @OneToMany(mappedBy = "confirm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "confirm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dislike> dislikes = new HashSet<>();

    public long getLikeCount(){
        return likes.size();
    }

    public long getDisLikeCount(){
        return dislikes.size();
    }

    @Builder
    public Confirm(String memo, String imageUrl, Long order, CheckPage checkPage) {
        this.memo = memo;
        this.imageUrl = imageUrl;
        this.order = order;
        this.checkPage = checkPage;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
    public void updateImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
