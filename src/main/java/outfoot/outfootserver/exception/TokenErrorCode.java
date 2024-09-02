package outfoot.outfootserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode {

    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레쉬 토큰입니다.");

    private final String message;
}
