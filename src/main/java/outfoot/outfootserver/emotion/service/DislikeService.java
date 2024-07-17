package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.domain.Dislike;
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

    @Transactional
    public void addDislike(Member member, CheckPage checkPage, Confirm confirm) {
        dislikeRepository.findByDislike(member, checkPage, confirm)
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DISLIKE_ALREADY_PRESSED);
                        });

        likeRepository.findByLike(member, checkPage, confirm)
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DUPLICATED_EMOTION);
                });

        Dislike dislike = Dislike.builder()
                .member(member)
                .checkPage(checkPage)
                .confirm(confirm)
                .build();

        dislikeRepository.save(dislike);
    }

    @Transactional
    public void cancelDislike(Member member, CheckPage checkPage, Confirm confirm) {
        dislikeRepository.findByDislike(member, checkPage, confirm)
                .ifPresentOrElse(dislikeRepository::delete , () -> {
                    throw new EmotionException(EmotionErrorCode.DISLIKE_NOT_PRESSED);
                });
    }
}
