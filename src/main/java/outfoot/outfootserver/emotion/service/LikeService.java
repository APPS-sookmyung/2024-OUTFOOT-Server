package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;
import outfoot.outfootserver.emotion.domain.Like;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
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
    private final ConfirmRepository confirmRepository;

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

        confirm.getLikes().add(like);
        confirmRepository.save(confirm);
    }

    @Transactional

    public void cancelLike(Member member, CheckPage checkPage, Confirm confirm) {
        likeRepository.findByLike(member, checkPage, confirm)
                .ifPresentOrElse(like -> {
                    likeRepository.delete(like);
                    confirm.getLikes().remove(like);
                    confirmRepository.save(confirm);
                }, () -> {});
    }
}
