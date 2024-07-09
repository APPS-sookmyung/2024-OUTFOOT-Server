package outfoot.outfootserver.friend.dto;

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
    private Member fromMember;
    private Member toMember;

    public Friend toFriend(){
        return Friend.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .nickname(toMember.getNickname())
                .build();
    }
}
