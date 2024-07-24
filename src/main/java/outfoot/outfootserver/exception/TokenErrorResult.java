package outfoot.outfootserver.exception;

import org.springframework.http.HttpStatus;

public enum TokenErrorResult {
    INVALID_TOKEN,
    INVALID_ACCESS_TOKEN,
    INVALID_REFRESH_TOKEN;
}
