package outfoot.outfootserver.friend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    FRIEND_DUPLICATED("이미 존재하는 친구입니다."),
    FRIEND_NOT_FOUND("존재하지 않는 친구입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
    FRIEND_MEMBER_NOT_FOUND("존재하지 않는 회원의 친구입니다.");

    private final String message;
}
