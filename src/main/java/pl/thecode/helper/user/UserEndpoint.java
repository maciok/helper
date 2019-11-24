package pl.thecode.helper.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
class UserEndpoint {

    private UserRepository userRepository;

    @GetMapping("/api/user")
    ResponseEntity<UserResponse> userInfo(@AuthenticationPrincipal OAuth2User userInfo) {
        if (userInfo == null) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        } else {
            var uuid = (String) userInfo.getAttribute("sub");
            var givenName = (String) userInfo.getAttribute("given_name");
            var familyName = (String) userInfo.getAttribute("family_name");
            var picture = (String) userInfo.getAttribute("picture");

            var userResponse =
              userRepository.findByUuid(uuid)
                .map(userEntity -> userEntity.createDto(givenName, familyName, picture))
                .orElseGet(() -> UserResponse.notRegistered(givenName, familyName, picture));


            return ResponseEntity.ok(userResponse);
        }
    }


    @PostMapping(value = "/api/user", consumes = APPLICATION_JSON_VALUE )
    ResponseEntity register(@RequestBody RegistrationRequest registrationRequest, @AuthenticationPrincipal OAuth2User userInfo) {
        var uuid = (String) userInfo.getAttribute("sub");

        var user = UserEntity.create(uuid, registrationRequest.getAge(), registrationRequest.getRoles(), registrationRequest.getDisabilities());

        userRepository.save(user);

        return ResponseEntity.status(CREATED).build();
    }
}
