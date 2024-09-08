package outfoot.outfootserver.checkpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface CheckPageRepository extends JpaRepository<CheckPage, Long> {
    Optional<CheckPage> findByTitle(String title);

    @Query("select c from CheckPage c where c.member = :member")
    List<CheckPage> findAllById(Member member);
}
