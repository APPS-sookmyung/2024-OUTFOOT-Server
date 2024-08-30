package outfoot.outfootserver.confirm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfirmErrorCode {
    CONFIRM_NOT_FOUND("인증판을 찾을 수 없습니다."),
    CONFIRM_DUPLICATION("인증판이 이미 존재합니다."),
    CONFIRM_DAILY_LIMIT_EXCEEDED("하루 최대 인증판 개수를 초과하였습니다.");

    private final String message;
}
