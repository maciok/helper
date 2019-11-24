package pl.thecode.helper.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserUtils {

  UserRepository userRepository;
  
  public long createUser(String userId) {
    var user = new UserEntity(null, userId, 23, "NECESSITOUS", "VISION_IMPAIRMENT");
    return userRepository.save(user).getId();
  }
  
  public void cleanUsers() {
    userRepository.deleteAll();
  }
}
