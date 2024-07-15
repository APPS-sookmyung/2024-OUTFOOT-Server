package outfoot.outfootserver.like.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.like.dto.LikeRequest;
import outfoot.outfootserver.like.service.LikeManageService;
import outfoot.outfootserver.like.service.LikeService;
import outfoot.outfootserver.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm")
public class LikeController {
    private final LikeService likeService;
    private final LikeManageService likeManageService;

    @PostMapping("/like")
    public BasicResponse<String> addLike(@Valid @RequestBody LikeRequest dto) {
        Member member = likeManageService.loadMember(dto.memberId());
        CheckPage checkPage = likeManageService.loadCheckPage(dto.checkPageId());
        Confirm confirm = likeManageService.loadConfirm(dto.confirmId());
        likeService.addLike(member, checkPage, confirm);
        // 전체 개수 추가 필요
        return ResponseUtil.success("좋아요 누르기에 성공했습니다.");
    }
}
