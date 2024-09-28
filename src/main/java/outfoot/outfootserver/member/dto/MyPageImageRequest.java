package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

public record MyPageImageRequest(
        @Schema(description = "프로필 이미지", example = "img/png") MultipartFile image) {
}
