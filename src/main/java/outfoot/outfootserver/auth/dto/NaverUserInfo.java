package outfoot.outfootserver.auth.dto;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }


    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
