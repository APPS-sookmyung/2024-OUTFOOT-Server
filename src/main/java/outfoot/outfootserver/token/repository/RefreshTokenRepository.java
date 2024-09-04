package outfoot.outfootserver.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.token.domain.RefreshToken;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT u FROM RefreshToken u WHERE u.username = :username")
    RefreshToken findByUsername(UUID username);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken u WHERE u.username = :username")
    void deleteByUsername(@Param("username")UUID username);
}
