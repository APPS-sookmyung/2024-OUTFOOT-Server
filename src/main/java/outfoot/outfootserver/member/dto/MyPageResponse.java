package outfoot.outfootserver.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {
    // 이름, 소개, 이메일, 비밀번호(***로 나오게) -> 비밀번호 전달하면 안 됨
    private String username;
    private String myInfo;
    private String email;
}
