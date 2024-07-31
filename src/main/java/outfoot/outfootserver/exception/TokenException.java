package outfoot.outfootserver.exception;

public class TokenException extends RuntimeException {
    private final TokenErrorResult errorResult;

    public TokenException(TokenErrorResult errorResult){
        this.errorResult = errorResult;
    }
}
