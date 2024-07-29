package outfoot.outfootserver.auth.dto;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();


    String getName();
}
