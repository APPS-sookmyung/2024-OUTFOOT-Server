package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Schema(description = "아이디", example = "ajung7038")
    @NotBlank
    private String username;

    @Schema(description = "닉네임", example = "ajung")
    private String nickname;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank
    private String password;

    @Schema(description = "이메일", example = "ajung7038@naver.com")
    @NotBlank @Email
    private String email;

    public static Member toMember(SignUpRequest dto, String friendCode) {
        return Member.builder()
                .username(dto.username)
                .password(dto.password)
                .nickname(dto.nickname)
                .email(dto.email)
                .code(friendCode)
                .build();
    }
}
