package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.emotion.domain.Like;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.exception.EmotionErrorCode;
import outfoot.outfootserver.emotion.exception.EmotionException;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.emotion.repository.LikeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    @Transactional
    public void addLike(EmotionRequest dto) {
        likeRepository.findByLike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.LIKE_ALREADY_PRESSED);
                });

        dislikeRepository.findByDislike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresent(e -> {
                    throw new EmotionException(EmotionErrorCode.DUPLICATED_EMOTION);
                });

        Like like = Like.builder()
                .member(dto.member())
                .checkPage(dto.checkPage())
                .confirm(dto.confirm())
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void cancelLike(EmotionRequest dto) {
        likeRepository.findByLike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresentOrElse(likeRepository::delete, () -> {
                    throw new EmotionException(EmotionErrorCode.LIKE_NOT_PRESSED);
                });
    }
}
