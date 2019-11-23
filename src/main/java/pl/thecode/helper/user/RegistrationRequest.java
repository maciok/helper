package pl.thecode.helper.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class RegistrationRequest {
    private int age;
    private List<Disabilities> disabilities;
    private List<Role> roles;
}
