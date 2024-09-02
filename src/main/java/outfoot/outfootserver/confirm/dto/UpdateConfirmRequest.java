package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record UpdateConfirmRequest(
        @Schema(description = "메모 (제목)", example = "오늘도 뿌듯한 하루 ~~") String title,
        @Schema(description = "메모 (내용)", example = "마치 하마가 된 거 같고, 뿌듯함ㅋㅋ") String content,
        @Schema(description = "이미지", example = "image/png")MultipartFile image) {
}
