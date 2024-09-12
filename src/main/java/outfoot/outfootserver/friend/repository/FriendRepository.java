package outfoot.outfootserver.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("select f from Friend f where f.fromMember = :from and f.toMember = :to")
    Optional<Friend> findFriend(@Param("from") Member fromMember, @Param("to") Member toMember);

    @Query("select f from Friend f where f.fromMember.id = :memberId")
    List<Friend> findByMemberId(@Param("memberId") Long memberId);

    @Query("select f from Friend f where f.fromMember = :memberId and f.toMember.id = :friendId")
    Optional<Friend> findByFromMemberAndId(@Param("memberId") Member fromMember, @Param("friendId") Long friendId);
}
