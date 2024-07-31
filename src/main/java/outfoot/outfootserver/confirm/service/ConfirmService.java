package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
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
import outfoot.outfootserver.emotion.domain.Like;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.emotion.repository.LikeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final ConfirmRepository confirmRepository;
    private final CheckPageRepository checkPageRepository;
    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public ConfirmResponse saveConfirm(Long checkPageId, ConfirmRequest dto) {
        CheckPage checkPage = checkPageRepository.findById(checkPageId)
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startOfDay = now.toLocalDate().atStartOfDay().format(formatter);
        String endOfDay = now.toLocalDate().atTime(23,59,59).format(formatter);

//      하나의 체크페이지에 하루에 한 번 인증 가능
        List<Confirm> dailyConfirms = confirmRepository.findByCheckPageIdAndCreatedAtBetween(checkPageId, startOfDay, endOfDay);
        if (dailyConfirms.size() >= 1){
            throw new ConfirmException(ConfirmErrorCode.CONFIRM_DAILY_LIMIT_EXCEEDED);
        }

//      한 도장판에 30번의 인증판만 존재할 수 있음.
        List<Confirm> confirmList = confirmRepository.findByCheckPageId(checkPageId);
        long order = confirmList.size() + 1;
        if(order >= 30){
            throw new ConfirmException(ConfirmErrorCode.CONFIRM_LIMIT_EXCEEDED);
        }

        Confirm confirm = dto.toConfirm(dto, order, checkPage);
        Confirm saveConfirm = confirmRepository.save(confirm);

        long likeCount = confirm.getLikeCount();
        long dislikeCount = confirm.getDisLikeCount();

        return ConfirmResponse.toConfirm(saveConfirm, likeCount, dislikeCount);
    }

    @Transactional
    public ConfirmResponse updateMemo(Long checkPageId, Long order, String memo) {
        Confirm confirm = findByCheckPageIdAndOrder(checkPageId, order);
        confirm.updateMemo(memo);
        Confirm updatedConfirm = confirmRepository.save(confirm);

        long likeCount = confirm.getLikeCount();
        long dislikeCount = confirm.getDisLikeCount();

        return ConfirmResponse.toConfirm(updatedConfirm, likeCount, dislikeCount);
    }

    @Transactional
    public void deleteConfirm(Long checkPageId, Long order) {
        Confirm confirm = findByCheckPageIdAndOrder(checkPageId, order);
        confirmRepository.delete(confirm);
    }

    public ConfirmResponse findConfirm(Long checkPageId, Long order){
        Confirm confirm = findByCheckPageIdAndOrder(checkPageId, order);
        long likeCount = confirm.getLikeCount();
        long dislikeCount = confirm.getDisLikeCount();
        return ConfirmResponse.toConfirm(confirm, likeCount, dislikeCount);
    }


    public Confirm findByCheckPageIdAndOrder (Long checkPageId, Long order) {
        return confirmRepository.findByCheckPageIdAndOrder(checkPageId, order)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }

    public Confirm findById (Long confirmId) {
        return confirmRepository.findById(confirmId)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }
}
