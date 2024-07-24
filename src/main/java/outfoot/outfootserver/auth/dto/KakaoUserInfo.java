package outfoot.outfootserver.auth.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }


    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }
}
