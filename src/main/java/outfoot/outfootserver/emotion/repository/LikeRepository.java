package outfoot.outfootserver.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.emotion.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
