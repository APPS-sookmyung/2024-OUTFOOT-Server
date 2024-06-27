package outfoot.outfootserver.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.Member;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByMemberAndFriendId(Member member, String friendId);
}
