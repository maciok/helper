package pl.thecode.helper.user;

import lombok.Value;

import java.util.List;

@Value
class UserResponse {
    String givenName;
    String familyName;
    String picture;
    List<Role> roles;
    Integer age;
    List<Warning> warnings;
}
