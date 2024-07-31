package outfoot.outfootserver.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.domain.Like;
import outfoot.outfootserver.member.domain.Member;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.member = :member and l.confirm = :confirm")
    Optional<Like> findByLike(@Param("member") Member member, @Param("confirm") Confirm confirm);
}
