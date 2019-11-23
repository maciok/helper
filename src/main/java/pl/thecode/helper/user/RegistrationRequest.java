package pl.thecode.helper.user;

import lombok.Data;

import java.util.List;

@Data
class RegistrationRequest {

    private Integer age;
    private List<Role> roles;

}
