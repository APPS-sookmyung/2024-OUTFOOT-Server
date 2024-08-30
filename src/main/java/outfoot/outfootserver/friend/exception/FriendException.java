package outfoot.outfootserver.friend.exception;

import lombok.Getter;

@Getter
public class FriendException extends RuntimeException {
    private final FriendErrorCode code;
    private final String message;

    public FriendException(FriendErrorCode code){
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
