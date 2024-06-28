package outfoot.outfootserver.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.friend.domain.Friend;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findById(Long member_id);
}
