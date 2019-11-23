package pl.thecode.helper.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static pl.thecode.helper.user.Warning.NOT_REGISTERED;

@AllArgsConstructor
@RestController
class UserEndpoint {

    private UserRepository userRepository;

    @GetMapping("/api/user")
    ResponseEntity userInfo(@AuthenticationPrincipal OAuth2User userInfo) {
        if (userInfo == null) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        } else {
            var uuid = (String) userInfo.getAttribute("sub");
            var givenName = (String) userInfo.getAttribute("given_name");
            var familyName = (String) userInfo.getAttribute("family_name");
            var picture = (String) userInfo.getAttribute("picture");


            var userResponse = userRepository.findByUuid(uuid)
                    .map(userEntity -> new UserResponse(givenName, familyName, picture, List.of(), null, List.of()))
                    .orElseGet(() -> new UserResponse(givenName, familyName, picture, List.of(), null, List.of(NOT_REGISTERED)));


            return ResponseEntity.ok(userResponse);
        }
    }


    @PostMapping("/api/user")
    ResponseEntity register(RegistrationRequest registrationRequest, @AuthenticationPrincipal OAuth2User userInfo) {
        var uuid = (String) userInfo.getAttribute("sub");

        var user = UserEntity.create(uuid, registrationRequest.getAge(), registrationRequest.getRoles());

        userRepository.save(user);

        return ResponseEntity.status(CREATED).build();
    }
}
