package pl.thecode.helper.user;

import static java.lang.String.format;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFacade {

  private final UserRepository userRepository;
  
  public List<Disabilities> getUserDisabilities(String userId) {
    return userRepository.findByUuid(userId)
                  .map(UserEntity::getDisabilities)
                  .orElseThrow(() -> new IllegalArgumentException(format("Cannot find user with id='%s'", userId)));
  }
}
