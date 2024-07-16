package outfoot.outfootserver.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.emotion.domain.Dislike;

public interface DislikeRepository extends JpaRepository<Dislike, Long> {
}
