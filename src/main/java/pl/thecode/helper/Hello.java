package pl.thecode.helper;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class Hello {

  @GetMapping("/api/hello")
  String hello() {
    return "Hello World!";
  }


  @GetMapping("/api/user")
  ResponseEntity userInfo(@AuthenticationPrincipal OAuth2AuthenticationToken userInfo) {
    var givenName = userInfo.getPrincipal().getAttribute("given_name");
    var familyName = userInfo.getPrincipal().getAttribute("family_name");
    var picture = userInfo.getPrincipal().getAttribute("picture");

    return ResponseEntity.ok(Map.of("givenName", givenName, "familyName", familyName, "picture", picture));
  }

}
