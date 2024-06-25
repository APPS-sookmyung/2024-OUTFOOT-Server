package outfoot.outfootserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import outfoot.outfootserver.member.domain.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<Member> findByUserId(UUID userId);

    Member findByProviderId(String providerId);
}
