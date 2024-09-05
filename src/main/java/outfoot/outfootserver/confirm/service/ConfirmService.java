package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.dto.ConfirmUpdateResponse;
import outfoot.outfootserver.confirm.dto.UpdateConfirmRequest;
import outfoot.outfootserver.confirm.exception.ConfirmErrorCode;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;
import outfoot.outfootserver.files.TestFileUploader;
import outfoot.outfootserver.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final ConfirmRepository confirmRepository;
    private final TestFileUploader fileUploader;
    private final String path = "confirm/";
    @Transactional
    public ConfirmResponse saveConfirm(CheckPage checkPage, ConfirmRequest dto, Member member) {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        String startOfDay = now.toLocalDate().atStartOfDay().format(formatter);
//        String endOfDay = now.toLocalDate().atTime(23,59,59).format(formatter);

//      하나의 체크페이지에 하루에 한 번 인증 가능
//        List<Confirm> dailyConfirms = confirmRepository.findByCheckPageIdAndCreatedAtBetween(checkPageId, startOfDay, endOfDay);
//        if (dailyConfirms.size() >= 1){
//            throw new ConfirmException(ConfirmErrorCode.CONFIRM_DAILY_LIMIT_EXCEEDED);
//        }

        String imageUrl = null;
        if (dto.image() != null && !dto.image().isEmpty()) {
            MultipartFile image = dto.image();
            imageUrl = fileUploader.uploadFile(image, path);
        }

        Confirm confirm = confirmRepository.save(ConfirmRequest.toConfirm(dto, checkPage, imageUrl, member));

        long likeCount = confirm.getLikeCount();
        long dislikeCount = confirm.getDisLikeCount();

        return ConfirmResponse.toConfirm(confirm, likeCount, dislikeCount, imageUrl);
    }

    @Transactional
    public ConfirmUpdateResponse updateConfirm(Long id, UpdateConfirmRequest dto, Member member) {
        Confirm confirm = findByIdAndMember(id, member);

        String imageUrl = null;
        if (dto.image() != null && !dto.image().isEmpty()){
            if (confirm.getImageUrl() != null){
                fileUploader.deleteFile(confirm.getImageUrl(), path);
            }
            imageUrl = fileUploader.uploadFile(dto.image(), path);
        }

        confirm.updateConfirm(dto.title(), dto.content(), imageUrl);
        Confirm updatedConfirm = confirmRepository.save(confirm);

        return ConfirmUpdateResponse.toConfirm(updatedConfirm);
    }

    @Transactional
    public void deleteConfirm(Long confirmId, Member member) {
        Confirm confirm = findByIdAndMember(confirmId, member);

        if (confirm.getImageUrl() != null) {
            fileUploader.deleteFile(confirm.getImageUrl(), "confirm");
        }
        confirmRepository.delete(confirm);
    }


    public ConfirmResponse findConfirm(Long id, Member member){
        Confirm confirm = findByIdAndMember(id, member);
        long likeCount = confirm.getLikeCount();
        long dislikeCount = confirm.getDisLikeCount();
        String imageUrl = confirm.getImageUrl();
        return ConfirmResponse.toConfirm(confirm, likeCount, dislikeCount, imageUrl);
    }

    public Confirm findByIdAndMember (Long confirmId, Member member) {
        return confirmRepository.findByIdAndMember(confirmId, member)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }

    public Confirm findById (Long confirmId) {
        return confirmRepository.findById(confirmId)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));

    }
}
