package outfoot.outfootserver.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record FriendListResponse(
        @Schema(description = "친구 신청을 건 멤버 (본인)", example = "메모") Long fromMember,
        @Schema(description = "친구 신청을 받은 멤버 (친구)", example = "메모") Long toMember,
        @Schema(description = "친구 닉네임", example = "정정") String nickname) {

    public static FriendListResponse toFriendList(Friend friend){
        return FriendListResponse.builder()
                .fromMember(friend.getFromMember().getId())
                .toMember(friend.getToMember().getId())
                .nickname(friend.getNickname())
                .build();
    }

}
