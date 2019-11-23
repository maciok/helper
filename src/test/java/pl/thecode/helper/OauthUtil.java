package pl.thecode.helper;

import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

public class OauthUtil {

  public static org.springframework.security.core.Authentication oauth() {
    Map<String, Object> attributes = Map.of(
      "sub", "1234567",
      "given_name", "Imie",
      "family_name", "Nazwisko",
      "picture", "Obrazek"
    );
    var oAuth2UserAuthority = new OAuth2UserAuthority(attributes);
    var principal = new DefaultOAuth2User(List.of(oAuth2UserAuthority), attributes, "sub");
    return new OAuth2AuthenticationToken(principal, principal.getAuthorities(), "google");
  }

}
