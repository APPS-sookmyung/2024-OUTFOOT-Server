package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;


@Builder
public record MemberResponse(
        @Schema(description = "아이디", example = "ajung7038") String username,
        @Schema(description = "이메", example = "ajung7038@naver.com") @Email String email,
        @Schema(description = "친구 신청용 코드", example = "ABCD") String code) {

    public static MemberResponse toMember(Member member){
        return MemberResponse.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .code(member.getCode())
                .build();
    }

}
