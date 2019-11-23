package pl.thecode.helper.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static pl.thecode.helper.user.Warning.NOT_REGISTERED;

@AllArgsConstructor
@RestController
class UserEndpoint {

    private UserRepository userRepository;

    @GetMapping("/api/user")
    ResponseEntity userInfo(@AuthenticationPrincipal OAuth2AuthenticationToken userInfo) {
        if (userInfo == null) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        } else {
            var uuid = (String) userInfo.getPrincipal().getAttribute("sub");
            var givenName = (String) userInfo.getPrincipal().getAttribute("given_name");
            var familyName = (String) userInfo.getPrincipal().getAttribute("family_name");
            var picture = (String) userInfo.getPrincipal().getAttribute("picture");


            var userResponse = userRepository.findByUuid(uuid)
                    .map(userEntity -> new UserResponse(givenName, familyName, picture, List.of(), null, List.of()))
                    .orElseGet(() -> new UserResponse(givenName, familyName, picture, List.of(), null, List.of(NOT_REGISTERED)));


            return ResponseEntity.ok(userResponse);
        }
    }
}
