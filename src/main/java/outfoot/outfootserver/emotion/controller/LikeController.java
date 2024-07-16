package outfoot.outfootserver.emotion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.service.EmotionManageService;
import outfoot.outfootserver.emotion.service.LikeService;
import outfoot.outfootserver.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm")
public class LikeController {
    private final LikeService likeService;
    private final EmotionManageService emotionManageService;

    @PostMapping("/like")
    public BasicResponse<String> addLike(@Valid @RequestBody EmotionRequest dto) {
        Member member = emotionManageService.loadMember(dto.memberId());
        CheckPage checkPage = emotionManageService.loadCheckPage(dto.checkPageId());
        Confirm confirm = emotionManageService.loadConfirm(dto.confirmId());
        likeService.addLike(member, checkPage, confirm);
        return ResponseUtil.success("좋아요 누르기에 성공하였습니다. Confirm id=" + dto.confirmId());
    }
}
