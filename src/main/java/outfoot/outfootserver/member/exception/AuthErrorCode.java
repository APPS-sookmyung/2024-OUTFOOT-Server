package outfoot.outfootserver.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    MEMBER_DUPLICATED("이미 존재하는 회원입니다."),
    NICKNAME_DUPLICATED("사용 중인 닉네임입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
    UUID_CREATE_ERROR("UUID를 생성하지 못했습니다."),
    FILE_NOT_FOUND("이미지 파일이 존재하지 않습니다."),
    UNAUTHORIZED_USER("인증된 사용자가 아닙니다.");

    private final String message;
}
