package outfoot.outfootserver.freind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.freind.domain.Friend;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByNickname(String nickname);
}
