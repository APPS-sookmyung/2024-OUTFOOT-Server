package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record UpdateConfirmRequest(
        @Schema(description = "사진 설명", example = "메모") String memo,
        @Schema(description = "사진", example = "image/png")MultipartFile image) {
}
