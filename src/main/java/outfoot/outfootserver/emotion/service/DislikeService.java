package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;
import outfoot.outfootserver.emotion.domain.Dislike;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.exception.EmotionErrorCode;
import outfoot.outfootserver.emotion.exception.EmotionException;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.emotion.repository.LikeRepository;
import outfoot.outfootserver.member.domain.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;
    private final ConfirmRepository confirmRepository;

    @Transactional
    public void addDislike(EmotionRequest dto) {
        dislikeRepository.findByDislike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DISLIKE_ALREADY_PRESSED);
                        });

        likeRepository.findByLike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DUPLICATED_EMOTION);
                });

        Dislike dislike = Dislike.builder()
                .member(dto.member())
                .checkPage(dto.checkPage())
                .confirm(dto.confirm())
                .build();

        dislikeRepository.save(dislike);
        confirm.getDislikes().add(dislike);
        confirmRepository.save(confirm);
    }

    @Transactional
    public void cancelDislike(Member member, CheckPage checkPage, Confirm confirm) {
        dislikeRepository.findByDislike(member, checkPage, confirm)
                .ifPresentOrElse(dislike -> {
                    dislikeRepository.delete(dislike);

                    confirm.getDislikes().remove(dislike);
                    confirmRepository.save(confirm);
                } , () -> {});
    }

}
