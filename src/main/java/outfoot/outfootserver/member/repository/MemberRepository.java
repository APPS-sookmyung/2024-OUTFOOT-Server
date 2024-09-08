package outfoot.outfootserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import outfoot.outfootserver.member.domain.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT u FROM Member u WHERE u.username = :username")
    Optional<Member> findByUsername(UUID username);
    Optional<Member> findByNickname(String nickname);

    Member findByProviderId(String providerId);
    Optional<Member> findByCode(String code);

}
