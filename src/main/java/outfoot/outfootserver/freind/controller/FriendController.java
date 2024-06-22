package outfoot.outfootserver.freind.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.freind.domain.Friend;
import outfoot.outfootserver.freind.dto.AddFriendRequest;
import outfoot.outfootserver.freind.service.FriendService;

@RestController
@RequestMapping //요청에 대해 어떤 Controller, 메소드가 처리할지 맵핑
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/addfriend")
    public BasicResponse<String> AddFriend (@Valid @RequestBody AddFriendRequest dto){
        Friend friend = AddFriendRequest.toFriend(dto); //친구추가 요청을 Friend로 변환
        long friendId = friendService.save(friend); // Friend 엔티티 저장
        return ResponseUtil.success("친구 추가 성공" + friendId);
    }
}
