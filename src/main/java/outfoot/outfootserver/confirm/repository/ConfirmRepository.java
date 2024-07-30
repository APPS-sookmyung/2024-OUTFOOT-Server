package outfoot.outfootserver.confirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
    List<Confirm> findByCheckPageId(Long checkPageId);

    Optional<Confirm> findByCheckPageIdAndOrder(Long checkPageId, Long order);
}
