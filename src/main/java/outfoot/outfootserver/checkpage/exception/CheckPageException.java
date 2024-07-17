package outfoot.outfootserver.checkpage.exception;

import lombok.Getter;

@Getter
public class CheckPageException extends RuntimeException {

    private CheckPageErrorCode code;
    private String message;

    public CheckPageException(CheckPageErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
