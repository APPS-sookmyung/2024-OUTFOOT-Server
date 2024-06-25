package outfoot.outfootserver.friend.exception;

public class AuthException extends RuntimeException {
    private AuthErrorCode code;
    private String message;

    public AuthException(AuthErrorCode code){
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
