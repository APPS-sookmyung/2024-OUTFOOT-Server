package outfoot.outfootserver.friend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.dto.SearchFriendRequest;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.Member;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public BasicResponse<String> addFriends (@Valid @RequestBody AddFriendRequest dto, @RequestParam Long memberId){
        long friendId = friendService.addFriend(dto, memberId); // Friend 엔티티 저장
        return ResponseUtil.success("친구 추가 성공" + friendId);
    }

    @DeleteMapping("/{friend_id}")
    public BasicResponse<String> deleteFriends(@PathVariable Long friend_id) {
        friendService.deleteFriend(friend_id);
        return ResponseUtil.success("친구 삭제 성공" + friend_id);
    }

    @GetMapping("/search")
    public BasicResponse<String> searchFriends(@Valid @RequestBody SearchFriendRequest dto, @RequestParam Long memberId) {
        Friend friend = friendService.searchFriend(dto, memberId);
        return ResponseUtil.success("친구 검색 성공" + friend);
    }
}

