package outfoot.outfootserver.checkpage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckPageErrorCode {
    CHECKPAGE_DUPLICATION("칭찬판 목표가 이미 존재합니다."),
    ANIMAL_NOT_FOUND("도장 메이트 번호를 찾을 수 없습니다."),
    CHECKPAGE_NOT_FOUND("도장판을 찾을 수 없습니다.");

    private String message;
}
