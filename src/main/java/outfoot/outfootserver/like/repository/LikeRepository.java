package outfoot.outfootserver.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.like.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
