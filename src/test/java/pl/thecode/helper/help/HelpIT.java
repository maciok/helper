package pl.thecode.helper.help;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static pl.thecode.helper.OauthUtil.oauth;
import static pl.thecode.helper.help.Category.PHYSICAL_HELP;
import static pl.thecode.helper.help.Category.SUPPORT;
import static pl.thecode.helper.help.TimeBox.NOW;
import static pl.thecode.helper.help.TimeBox.URGENT;
import static pl.thecode.helper.user.Disabilities.ALLERGIES;
import static pl.thecode.helper.user.Disabilities.DIABETES;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import pl.thecode.helper.user.UserUtils;

@SpringBootTest
@ActiveProfiles("local")
class HelpIT {

  public static final String SOME_USER_ID = "1234567";
  public static final String OTHER_USER_ID = "7654321";
  @Autowired
  WebApplicationContext context;

  @Autowired
  HelpRepository helpRepository;

  @Autowired
  UserUtils userUtils;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void pushHelpRequest() {
    createUser();

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
    userUtils.cleanUsers();
    helpRepository.deleteAll();
  }


  @Test
  void fetchHelpRequests() {
    createRequests();

    given()

      .auth()
      .authentication(oauth())
      .contentType(JSON)

      .when()
      .get("/api/help")

      .then()
      .log().all()
      .statusCode(200);

    // after
    helpRepository.deleteAll();
  }


  private void createUser() {
    userUtils.createUser(SOME_USER_ID);
    userUtils.createUser(OTHER_USER_ID);
  }

  private void createRequests() {
    var someHelp = new HelpEntity(
      SOME_USER_ID,
      List.of(ALLERGIES),
      "123456",
      URGENT,
      PHYSICAL_HELP,
      "Nothing to add"
    );

    var otherHelp = new HelpEntity(
      OTHER_USER_ID,
      List.of(DIABETES),
      "987654",
      NOW,
      SUPPORT,
      "Talk to me"
    );

    helpRepository.save(someHelp);
    helpRepository.save(otherHelp);

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
