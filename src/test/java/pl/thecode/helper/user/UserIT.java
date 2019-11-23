package pl.thecode.helper.user;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static pl.thecode.helper.OauthUtil.oauth;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("local")
class UserIT {

  @Autowired
  WebApplicationContext context;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void registerUser() {
    given()

      .auth()
      .authentication(oauth())
      .body(registrationBody())
      .contentType(JSON)

      .when()
      .post("/api/user")

      .then()
      .log().ifValidationFails()
      .statusCode(201);

    var maybeUser = userRepository.findByUuid("1234567");
    assertThat(maybeUser).isPresent();

    // after
    userRepository.deleteById(maybeUser.get().getId());
  }

  @Test
  void getRegisteredUser() {
    var userId = createUser();

    given()

      .auth()
      .authentication(oauth())
      .body(registrationBody())
      .contentType(JSON)

      .when()
      .get("/api/user")

      .then()
      .log().ifValidationFails()
      .statusCode(200)
      .body("givenName", equalTo("Imie"));


    // after
    userRepository.deleteById(userId);
  }

  private Long createUser() {
    var user = new UserEntity(null, "1234567", 23, "NECESSITOUS", "VISION_IMPAIRMENT");
    return userRepository.save(user).getId();
  }

  private Map<String, Object> registrationBody() {
    return Map.of(
      "age", 23,
      "disabilities", List.of("VISION_IMPAIRMENT"),
      "roles", List.of("NECESSITOUS")
    );
  }


}
