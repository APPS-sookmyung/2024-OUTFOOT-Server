package outfoot.outfootserver.member.dto;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.member.domain.Member;


@Builder
public record MemberResponse(String username,@Email String email, String code) {

    public static MemberResponse toMember(Member member){
        return MemberResponse.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .code(member.getCode())
                .build();
    }

}
