package outfoot.outfootserver.confirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.List;
import java.util.Optional;

public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
    List<Confirm> findByCheckPageId(Long checkPageId);

//    Optional<Confirm> findByCheckPageId(Long checkPageId);

    List<Confirm> findByCheckPageIdAndCreatedAtBetween(Long checkPageId, String start, String end);

}
