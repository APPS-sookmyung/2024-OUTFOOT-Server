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
@RequestMapping("/confirm/like")
public class LikeController {
    private final LikeService likeService;
    private final EmotionManageService emotionManageService;

    @PostMapping
    public BasicResponse<String> addLike(@Valid @RequestBody EmotionRequest dto) {
        Member member = emotionManageService.loadMember(dto.memberId());
        CheckPage checkPage = emotionManageService.loadCheckPage(dto.checkPageId());
        Confirm confirm = emotionManageService.loadConfirm(dto.confirmId());
        likeService.addLike(member, checkPage, confirm);
        return ResponseUtil.success("인정 추가에 성공하였습니다. Confirm id = " + dto.confirmId());
    }
    
    @DeleteMapping
    public BasicResponse<String> deleteLike(@Valid @RequestBody EmotionRequest dto) {
        Member member = emotionManageService.loadMember(dto.memberId());
        CheckPage checkPage = emotionManageService.loadCheckPage(dto.checkPageId());
        Confirm confirm = emotionManageService.loadConfirm(dto.confirmId());

        likeService.cancelLike(member, checkPage, confirm);
        return ResponseUtil.success("인정 취소에 성공하였습니다. Confirm id = " + dto.confirmId());
    }
}
