package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.CheckPageListResponse;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.ConfirmListResponse;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.exception.ConfirmErrorCode;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final ConfirmRepository confirmRepository;
    private final CheckPageRepository checkPageRepository;

    @Transactional
    public ConfirmResponse saveConfirm(Long checkPageId, ConfirmRequest dto) {
        CheckPage checkPage = checkPageRepository.findById(checkPageId)
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));

        List<Confirm> confirmList = confirmRepository.findByCheckPageId(checkPageId);

        int order = confirmList.size() + 1;
        if(order >= 30){
            throw new ConfirmException(ConfirmErrorCode.CONFIRM_LIMIT_EXCEEDED);
        }

        Confirm confirm = dto.toConfirm(dto, order, checkPage);
        Confirm saveConfirm = confirmRepository.save(confirm);

        return ConfirmResponse.toConfirm(saveConfirm);
    }

    @Transactional
    public ConfirmResponse updateMemo(Long confirmId, String memo) {
        Confirm confirm = findById(confirmId);
        confirm.updateMemo(memo);
        Confirm updatedConfirm = confirmRepository.save(confirm);
        return ConfirmResponse.toConfirm(updatedConfirm);
    }

    @Transactional
    public void deleteConfirm(Long checkPageId, int order) {
        Confirm confirm = confirmRepository.findByCheckPageIdAndOrder(checkPageId, order)
                .orElseThrow(()-> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
        confirmRepository.delete(confirm);
    }

    public ConfirmResponse findConfirm(Long checkPageId, int order){
        Confirm confirm = confirmRepository.findByCheckPageIdAndOrder(checkPageId, order)
                .orElseThrow(()-> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
        return ConfirmResponse.toConfirm(confirm);
    }

    public List<ConfirmListResponse> findAllConfirm(){
        List<Confirm> confirmList = confirmRepository.findAll();
        return confirmList.stream()
                .map(ConfirmListResponse::toConfirmList)
                .toList();
    }

    public Confirm findById (Long confirm_id) {
        return confirmRepository.findById(confirm_id)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }
}
