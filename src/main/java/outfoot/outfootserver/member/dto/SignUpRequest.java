package outfoot.outfootserver.member.dto;

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

    @NotBlank
    private String username;

    private String nickname;

    @NotBlank
    private String password;

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
