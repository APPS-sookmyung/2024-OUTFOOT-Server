package outfoot.outfootserver.confirm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfirmErrorCode {
    CONFIRM_NOT_FOUND("인증판을 찾을 수 없습니다.");

    private String message;
}
