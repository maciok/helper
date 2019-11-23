package pl.thecode.helper.user;

import static pl.thecode.helper.user.Warning.NOT_REGISTERED;

import java.util.List;
import lombok.Value;

@Value
class UserResponse {

    private String givenName;
    private String familyName;
    private String picture;
    private Integer age;

    private List<Role> roles;
    private List<Disabilities> disabilities;
    private List<Warning> warnings;

    static UserResponse notRegistered(String givenName, String familyName, String picture) {
        return new UserResponse(givenName, familyName, picture, null, List.of(), List.of(), List.of(NOT_REGISTERED));
    }
}
