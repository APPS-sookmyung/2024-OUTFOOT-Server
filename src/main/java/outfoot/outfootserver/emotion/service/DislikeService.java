package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.emotion.domain.Dislike;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.exception.EmotionErrorCode;
import outfoot.outfootserver.emotion.exception.EmotionException;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.emotion.repository.LikeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;

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
    }

    @Transactional
    public void cancelDislike(EmotionRequest dto) {
        dislikeRepository.findByDislike(dto.member(), dto.checkPage(), dto.confirm())
                .ifPresentOrElse(dislikeRepository::delete , () -> {
                    throw new EmotionException(EmotionErrorCode.DISLIKE_NOT_PRESSED);
                });
    }
}
