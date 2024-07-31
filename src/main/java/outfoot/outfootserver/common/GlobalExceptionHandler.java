package outfoot.outfootserver.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ErrorEntity;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.exception.ConfirmException;
import outfoot.outfootserver.emotion.exception.EmotionException;
import outfoot.outfootserver.member.exception.AuthException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BasicResponse<ErrorEntity> authException(AuthException e) {
        log.error("Auth Exception({})={}", e.getCode(), e.getMessage());
        return ResponseUtil.error(new ErrorEntity(e.getCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> validationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        log.error("Dto Validation Exception({}): {}", "BAD_INPUT", errors);
        return ResponseUtil.error(new ErrorEntity("BAD_INPUT", "입력이 올바르지 않습니다.", errors));
    }

    @ExceptionHandler(CheckPageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> checkPageBadRequestException(CheckPageException e) {
        log.error("CheckPage Not Found({})={}", e.getCode(), e.getMessage());
        return ResponseUtil.error(new ErrorEntity(e.getCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(ConfirmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> ConfirmBadRequestException(ConfirmException e){
        log.error("Confirm Not Found({})={}", e.getCode(), e.getMessage());
        return ResponseUtil.error(new ErrorEntity(e.getCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(EmotionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> EmotionInvalidRequestException(EmotionException e){
        log.error("Emotion Invalid Request({})={}", e.getCode(), e.getMessage());
        return ResponseUtil.error(new ErrorEntity(e.getCode().toString(), e.getMessage()));
    }
}
