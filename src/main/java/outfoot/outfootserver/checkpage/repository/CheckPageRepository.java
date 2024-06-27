package outfoot.outfootserver.checkpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.checkpage.domain.CheckPage;

import java.util.Optional;

public interface CheckPageRepository extends JpaRepository<CheckPage, Long> {
    Optional<CheckPage> findByTitle(String title);
}
