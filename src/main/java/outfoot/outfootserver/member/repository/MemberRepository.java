package outfoot.outfootserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import outfoot.outfootserver.member.domain.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
<<<<<<< HEAD

    @Query("SELECT u FROM Member u WHERE u.userId = :userId")
    Optional<Member> findByUserId(UUID userId);

    Member findByProviderId(String providerId);
=======
    Optional<Member> findByCode(String code);
>>>>>>> a6c141017954791de79d4c8242601f2816e3d073
}
