package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.exception.ConfirmErrorCode;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final ConfirmRepository confirmRepository;
    private final CheckPageRepository checkPageRepository;

    @Transactional
    public ConfirmResponse saveConfirm(ConfirmRequest dto) {
        CheckPage checkPage = checkPageRepository.findById(dto.checkPageId())
                .orElseThrow(()->new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));

        Confirm confirm = confirmRepository.save(ConfirmRequest.toConfirm(dto, checkPage));

        return ConfirmResponse.toConfirm(confirm);
    }

    @Transactional
    public ConfirmResponse updateMemo(Long confirmId, String memo) {
        Confirm confirm = findById(confirmId);
        confirm.updateMemo(memo);
        Confirm updatedConfirm = confirmRepository.save(confirm);
        return ConfirmResponse.toConfirm(updatedConfirm);
    }

    @Transactional
    public void deleteConfirm(Long confirm_id) {
        Confirm confirm = findById(confirm_id);
        confirmRepository.delete(confirm);
    }

    public ConfirmResponse findConfirm(Long confirmId){
        Confirm confirm = findById(confirmId);
        return ConfirmResponse.toConfirm(confirm);
    }

    public Confirm findById (Long confirm_id) {
        return confirmRepository.findById(confirm_id)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }
}
