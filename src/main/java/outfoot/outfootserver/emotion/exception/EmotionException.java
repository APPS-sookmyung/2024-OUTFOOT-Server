package outfoot.outfootserver.emotion.exception;

import lombok.Getter;

@Getter
public class EmotionException extends RuntimeException {

    private EmotionErrorCode code;
    private String message;

    public EmotionException(EmotionErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
