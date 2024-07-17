package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.exception.ConfirmErrorCode;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final ConfirmRepository confirmRepository;

    @Transactional
    public Confirm save() {
        // 수정 필요 -> 일단 하드코딩으로 박아둠...
        return confirmRepository.save(Confirm.builder().build());
    }

    public Confirm findById (Long confirm_id) {
        return confirmRepository.findById(confirm_id)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }
}
