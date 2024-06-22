package outfoot.outfootserver.freind.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    FRIEND_DUPLICATED("이미 존재하는 친구입니다.");
    private final String message;
}
