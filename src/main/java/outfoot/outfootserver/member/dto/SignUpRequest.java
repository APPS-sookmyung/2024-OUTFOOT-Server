package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Schema(description = "아이디", example = "ajung7038")
    @NotBlank
    private UUID username;

    @Schema(description = "닉네임", example = "ajung")
    private String nickname;

    @Schema(description = "이메일", example = "ajung7038@naver.com")
    @NotBlank @Email
    private String email;

    @Schema(description = "한 줄 소개", example = "안녕하세요")
    private String myIntro;

    public static Member toMember(SignUpRequest dto, String friendCode) {
        return Member.builder()
                .username(dto.username)
                .nickname(dto.nickname)
                .email(dto.email)
                .myIntro(dto.myIntro)
                .code(friendCode)
                .build();
    }
}
