package outfoot.outfootserver.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.like.domain.Like;
import outfoot.outfootserver.like.repository.LikeRepository;
import outfoot.outfootserver.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public void addLike(Member member, CheckPage checkPage, Confirm confirm) {
        Like like = Like.builder()
                .member(member)
                .checkPage(checkPage)
                .confirm(confirm)
                .build();

        likeRepository.save(like);
    }
}
