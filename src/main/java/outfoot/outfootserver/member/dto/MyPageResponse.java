package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {
    // 이름, 소개, 이메일, 비밀번호(***로 나오게) -> 비밀번호 전달하면 안 됨
    @Schema(description = "닉네임", example = "ajung")
    private String nickname;

    @Schema(description = "한 줄 소개", example = "안녕하세요")
    private String myIntro;

    @Schema(description = "이메일", example = "ajung7038@naver.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "프로필 이미지 url", example = "img/png")
    private String imageUrl;

    public static MyPageResponse toMyPage(Member member, String imageUrl) {
        return new MyPageResponse(
                member.getNickname(),
                member.getMyIntro(),
                member.getEmail(),
                imageUrl
        );
    }
}
