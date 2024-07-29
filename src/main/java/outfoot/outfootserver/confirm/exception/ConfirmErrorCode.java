package outfoot.outfootserver.confirm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfirmErrorCode {
    CONFIRM_NOT_FOUND("인증판을 찾을 수 없습니다."),
    CONFIRM_DUPLICATION("인증판이 이미 존재합니다."),
    CONFIRM_LIMIT_EXCEEDED("인증판의 개수가 초과되었습니다.");

    private String message;
}
