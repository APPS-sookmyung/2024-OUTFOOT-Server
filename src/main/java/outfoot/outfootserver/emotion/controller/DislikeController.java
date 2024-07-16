package outfoot.outfootserver.emotion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.service.DislikeService;
import outfoot.outfootserver.emotion.service.EmotionManageService;
import outfoot.outfootserver.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm")
public class DislikeController {

    private final DislikeService dislikeService;
    private final EmotionManageService emotionManageService;

    @PostMapping("/dislike")
    public BasicResponse<String> addDislike(@Valid @RequestBody EmotionRequest dto) {
        Member member = emotionManageService.loadMember(dto.memberId());
        CheckPage checkPage = emotionManageService.loadCheckPage(dto.checkPageId());
        Confirm confirm = emotionManageService.loadConfirm(dto.confirmId());
        dislikeService.addDislike(member, checkPage, confirm);
        return ResponseUtil.success("싫어요 누르기에 성공하였습니다. Confirm id = " + dto.confirmId());
    }
}
