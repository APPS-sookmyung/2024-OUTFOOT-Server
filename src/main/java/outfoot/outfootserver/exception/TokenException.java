package outfoot.outfootserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import outfoot.outfootserver.member.exception.AuthErrorCode;

@Getter
public class TokenException extends RuntimeException {
    private TokenErrorCode code;
    private String message;

    public TokenException(TokenErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
