package outfoot.outfootserver.confirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import outfoot.outfootserver.confirm.domain.Confirm;

public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
}
