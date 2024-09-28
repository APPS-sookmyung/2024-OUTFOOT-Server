package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.member.domain.Member;

public record MyPageRequest(
        @Schema(description = "닉네임", example = "ajung") String nickname,
        @Schema(description = "한 줄 소개", example = "안녕하세요") String myIntro,
        @Schema(description = "이메일", example = "ajung7038@naver.com") @Email String email
        ) {
}
