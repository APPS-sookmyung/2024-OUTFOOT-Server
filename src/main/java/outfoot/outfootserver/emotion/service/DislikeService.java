package outfoot.outfootserver.emotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.domain.Dislike;
import outfoot.outfootserver.emotion.repository.DislikeRepository;
import outfoot.outfootserver.member.domain.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;

    @Transactional
    public void addDislike(Member member, CheckPage checkPage, Confirm confirm) {
        Dislike dislike = Dislike.builder()
                .member(member)
                .checkPage(checkPage)
                .confirm(confirm)
                .build();

        dislikeRepository.save(dislike);
    }
}
