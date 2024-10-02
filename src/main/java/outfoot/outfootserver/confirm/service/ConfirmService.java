package outfoot.outfootserver.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.*;
import outfoot.outfootserver.confirm.exception.ConfirmErrorCode;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;
import outfoot.outfootserver.files.TestFileUploader;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;

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

        String imageUrl = uploadImage(dto.image());
        Confirm confirm = confirmRepository.save(ConfirmRequest.toConfirm(dto, checkPage, imageUrl, member));
        return ConfirmResponse.toConfirm(confirm, confirm.getLikeCount(), confirm.getDisLikeCount(), imageUrl);
    }

    @Transactional
    public ConfirmUpdateResponse updateImage(Long confirmId, UpdateConfirmImageRequest dto, Member member) {
        Confirm confirm = findById(confirmId);

        if (!confirm.getMember().equals(member)) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }

        if (confirm.getImageUrl() != null) {
            fileUploader.deleteFile(confirm.getImageUrl(), "confirm");
        }
        String imageUrl = uploadImage(dto.image());
        confirm.updateImage(imageUrl);

        Confirm updatedConfirm = confirmRepository.save(confirm);
        return ConfirmUpdateResponse.toConfirm(updatedConfirm);
    }

    @Transactional
    public ConfirmUpdateResponse updateMemo(Long confirmId, UpdateConfirmRequest dto, Member member) {
        Confirm confirm = findById(confirmId);

        if (!confirm.getMember().equals(member)) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }

        confirm.updateMemo(dto.title(), dto.content());
        Confirm updatedConfirm = confirmRepository.save(confirm);
        return ConfirmUpdateResponse.toConfirm(updatedConfirm);
    }

    @Transactional
    public void deleteConfirm(Long confirmId, Member member) {
        Confirm confirm = findById(confirmId);

        if (!confirm.getMember().equals(member)) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }

        if (confirm.getImageUrl() != null) {
            fileUploader.deleteFile(confirm.getImageUrl(), "confirm");
        }
        if (!confirm.getMember().equals(member)) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }
        confirmRepository.delete(confirm);
    }


    public ConfirmResponse findConfirm(Long id, Member member) {
        Confirm confirm = findById(id);
        return ConfirmResponse.toConfirm(confirm, confirm.getLikeCount(), confirm.getDisLikeCount(), confirm.getImageUrl());
    }

    public Confirm findById(Long confirmId) {
        return confirmRepository.findById(confirmId)
                .orElseThrow(() -> new ConfirmException(ConfirmErrorCode.CONFIRM_NOT_FOUND));
    }

    public String uploadImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new AuthException(AuthErrorCode.FILE_NOT_FOUND);
        }
        return fileUploader.uploadFile(image, path);
    }
}
