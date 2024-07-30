package outfoot.outfootserver.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotBlank
    private UUID username;

    private String nickname;


    public static Member toMember(SignUpRequest dto, String friendCode) {
        return Member.builder()
                .username(dto.username)
                .nickname(dto.nickname)
                .code(friendCode)
                .build();
    }
}
