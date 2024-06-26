package outfoot.outfootserver.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    MEMBER_DUPLICATED("이미 존재하는 회원입니다.");

    private final String message;
}
