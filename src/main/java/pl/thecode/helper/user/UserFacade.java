package pl.thecode.helper.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserFacade {

  private final UserRepository userRepository;
  
  public List<Disabilities> getUserDisabilities(String userId) {
    return userRepository.findByUuid(userId)
                  .map(UserEntity::getDisabilities)
                  .orElseThrow(() -> new IllegalArgumentException(format("Cannot find user with id='%s'", userId)));
  }


  public List<String> getNearbyUsersUuidExcept(String uuid) {
    return userRepository.findAll().stream()
            .filter(u -> !u.getUuid().equals(uuid))
            .map(UserEntity::getUuid)
            .collect(Collectors.toList());
  }
}
