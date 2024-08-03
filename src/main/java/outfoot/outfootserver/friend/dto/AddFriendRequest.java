package outfoot.outfootserver.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.domain.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendRequest {
    @Schema(description = "친구 신청을 건 멤버 (본인)", example = "메모")
    private Member fromMember;

    @Schema(description = "친구 신청을 받은 멤버 (친구)", example = "메모")
    private Member toMember;

    public Friend toFriend(){
        return Friend.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .nickname(toMember.getNickname())
                .build();
    }
}
