package outfoot.outfootserver.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.domain.Dislike;
import outfoot.outfootserver.member.domain.Member;

import java.util.Optional;

public interface DislikeRepository extends JpaRepository<Dislike, Long> {

    @Query("select d from Dislike d where d.member = :member and d.checkPage = :checkPage and d.confirm = :confirm")
    Optional<Dislike> findByDislike(@Param("member") Member member, @Param("checkPage") CheckPage checkPage, @Param("confirm") Confirm confirm);
}
