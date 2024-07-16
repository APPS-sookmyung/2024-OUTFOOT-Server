package outfoot.outfootserver.emotion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmotionErrorCode {
    LIKE_ALREADY_PRESSED("인정 버튼을 중복으로 누를 수 없습니다."),
    LIKE_NOT_PRESSED("인정 버튼이 눌려있지 않아 취소가 불가능합니다."),

    DISLIKE_ALREADY_PRESSED("부정 버튼을 중복으로 누를 수 없습니다."),
    DISLIKE_NOT_PRESSED("부정 버튼이 눌려있지 않아 취소가 불가능합니다.");

    private String message;
}
