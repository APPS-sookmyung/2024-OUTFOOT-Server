package outfoot.outfootserver.emotion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.service.DislikeService;
import outfoot.outfootserver.emotion.service.EmotionManageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dislike")
public class DislikeController {

    private final DislikeService dislikeService;
    private final EmotionManageService emotionManageService;

    @GetMapping("/{member_id}/{check_page_id}/{confirm_id}")
    public BasicResponse<String> addDislike(@PathVariable Long member_id, @PathVariable Long check_page_id, @PathVariable Long confirm_id) {
        EmotionRequest dto = emotionManageService.loadLikeInfo(member_id, check_page_id, confirm_id);
        dislikeService.addDislike(dto);
        return ResponseUtil.success("부정 추가에 성공하였습니다. Confirm id = " + confirm_id);
    }

    @DeleteMapping("/{member_id}/{check_page_id}/{confirm_id}")
    public BasicResponse<String> deleteDislike(@PathVariable Long member_id, @PathVariable Long check_page_id, @PathVariable Long confirm_id) {
        EmotionRequest dto = emotionManageService.loadLikeInfo(member_id, check_page_id, confirm_id);
        dislikeService.cancelDislike(dto);
        return ResponseUtil.success("부정 취소에 성공하였습니다. Confirm id = " + confirm_id);
    }
}
