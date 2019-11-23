package pl.thecode.helper.help;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static pl.thecode.helper.OauthUtil.oauth;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("local")
class HelpIT {

  @Autowired
  WebApplicationContext context;

  @Autowired
  HelpRepository helpRepository;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void pushHelpRequest() {
    given()

      .auth()
      .authentication(oauth())
      .body(helpRequest())
      .contentType(JSON)

      .when()
      .post("/api/help")

      .then()
      .log().ifValidationFails()
      .statusCode(201);
    
    // after
    helpRepository.deleteAll();
  }

  private Map<String, Object> helpRequest() {
    return Map.of(
      "localization", "123456-654321",
      "helpTimeBox", "URGENT",
      "category", "TRANSPORT",
      "description", "Fast! Fast!"
    );
  }

}
