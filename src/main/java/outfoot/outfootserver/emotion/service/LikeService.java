package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.domain.Like;
import outfoot.outfootserver.emotion.exception.EmotionErrorCode;
import outfoot.outfootserver.emotion.exception.EmotionException;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.emotion.repository.LikeRepository;
import outfoot.outfootserver.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    @Transactional
    public void addLike(Member member, Confirm confirm) {
        likeRepository.findByLike(member, confirm)
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.LIKE_ALREADY_PRESSED);
                });

        dislikeRepository.findByDislike(member, confirm)
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DUPLICATED_EMOTION);
                });

        Like like = Like.builder()
                .member(member)
                .confirm(confirm)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void cancelLike(Member member, Confirm confirm) {
        likeRepository.findByLike(member, confirm)
                .ifPresentOrElse(likeRepository::delete, () -> {
                    throw new EmotionException(EmotionErrorCode.LIKE_NOT_PRESSED);
                });
    }
}
