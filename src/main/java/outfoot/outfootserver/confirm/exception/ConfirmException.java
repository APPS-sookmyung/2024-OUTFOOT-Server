package outfoot.outfootserver.confirm.exception;

import lombok.Getter;

@Getter
public class ConfirmException extends RuntimeException {

    private ConfirmErrorCode code;
    private String message;

    public ConfirmException(ConfirmErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
