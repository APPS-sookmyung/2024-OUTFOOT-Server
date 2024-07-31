package outfoot.outfootserver.friend.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.dto.FriendListResponse;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@Tag(name="친구 CRUD", description = "Friend API")
public class FriendController {
    private final FriendService friendService;
    private final MemberRepository memberRepository;

    @PostMapping("/{member_id}")
    @Parameters({
            @Parameter(name = "code", description = "친구 코드", example = "ABC")
    })
    public BasicResponse<String> addFriends (@Valid @RequestParam("code") String searchCode, @PathVariable("member_id") Long memberId ){
        //toMember = 친추를 받은(친구) , fromMember = 친추를 한(본인)
        Member toMember = friendService.searchFriend(searchCode);
        Member fromMember = memberRepository.findById(memberId).orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
        friendService.addFriend(fromMember, toMember);
        return ResponseUtil.success("친구 추가 성공: "+fromMember.getId());
    }

//    @DeleteMapping("/{friend_id}")
//    public BasicResponse<String> deleteFriends(@PathVariable Long friendId) {
//        friendService.deleteFriend(friendId);
//        return ResponseUtil.success("친구 삭제 성공");
//    }

//  친구 단일 조회(코드로 조회)
    @GetMapping
    @Parameters({
            @Parameter(name = "code", description = "친구 코드", example = "ABC")
    })
    public BasicResponse<String> searchFriends(@Valid @RequestParam("code") String searchCode) {
        Member member = friendService.searchFriend(searchCode);
        return ResponseUtil.success("친구 검색 성공: "+ member.getId());
    }

//  url로 입력한 member의 친구 목록 조회
    @GetMapping("/{member_id}")
    public BasicResponse<List<FriendListResponse>> findAllFriend(@PathVariable(name = "member_id") Long memberId){
        List<FriendListResponse> friends = friendService.findAllFriend(memberId);
        return ResponseUtil.success(friends);

    }

}

