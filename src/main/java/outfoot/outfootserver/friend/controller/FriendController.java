package outfoot.outfootserver.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.FriendCountListResponse;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@Tag(name="친구 CRUD", description = "Friend API")
public class FriendController {
    private final FriendService friendService;
    private final MemberService memberService;

    @PostMapping("/{member_id}")
    @Operation(summary = "친구 추가")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "code", description = "친구 코드", example = "ABC"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 친구입니다."),
            @ApiResponse(responseCode = "400", description = "자기 자신을 친구로 추가할 수 없습니다."),
    })
    public BasicResponse<String> addFriends (@Valid @RequestParam("code") String searchCode, @PathVariable("member_id") Long memberId ){
        //toMember = 친추를 받은(친구) , fromMember = 친추를 한(본인)
        Member toMember = memberService.searchFriend(searchCode);
        Member fromMember = memberService.loadMember(memberId);
        friendService.addFriend(fromMember, toMember);
        return ResponseUtil.success("친구 추가 성공: " + fromMember.getId() + " -> " + toMember.getId());
    }

    @DeleteMapping("/{friend_id}")
    @Operation(summary = "친구 추가")
    @Parameters({
            @Parameter(name = "friend_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 친구입니다."),
    })
    public BasicResponse<String> deleteFriends(@PathVariable Long friend_id) {
        friendService.deleteFriend(friend_id);
        return ResponseUtil.success("친구 삭제 성공");
    }
    
//    @GetMapping
//    @Operation(summary = "친구 조회")
//    @Parameters({
//            @Parameter(name = "code", description = "친구 코드", example = "ABC")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "친구 검색에 성공하였습니다."),
//            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
//    })
//    public BasicResponse<String> searchFriends(@Valid @RequestParam("code") String searchCode) {
//        Member member = memberService.searchFriend(searchCode);
//        return ResponseUtil.success("친구 검색 성공: "+ member.getId());
//    }

    @Operation(summary = "친구 전체 조회")
    @GetMapping("/{member_id}")
    @Parameters({
            @Parameter(name = "member_id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 전체 조회에 성공하였습니다."),
    })
    public BasicResponse<FriendCountListResponse> findAllFriend(@PathVariable(name = "member_id") Long memberId){
        memberService.loadMember(memberId);
        return ResponseUtil.success(friendService.findAllFriend(memberId));
    }

}

