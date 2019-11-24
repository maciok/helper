package pl.thecode.helper.help;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.thecode.helper.OauthUtil.oauth;
import static pl.thecode.helper.help.Category.PHYSICAL_HELP;
import static pl.thecode.helper.help.Category.SUPPORT;
import static pl.thecode.helper.help.State.CLOSED;
import static pl.thecode.helper.help.TimeBox.NOW;
import static pl.thecode.helper.help.TimeBox.URGENT;
import static pl.thecode.helper.user.Disabilities.ALLERGIES;
import static pl.thecode.helper.user.Disabilities.DIABETES;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import pl.thecode.helper.chat.ChatUtils;
import pl.thecode.helper.user.UserUtils;

@SpringBootTest
@ActiveProfiles("local")
class HelpIT {

  static final String SOME_USER_ID = "1234567";
  static final String OTHER_USER_ID = "7654321";

  @Autowired
  WebApplicationContext context;

  @Autowired
  HelpRepository helpRepository;

  @Autowired
  UserUtils userUtils;
  
  @Autowired
  ChatUtils chatUtils;

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

  @Test
  void closeHelpRequest() {
    var helpId = createSingleRequest();
    
    given()

      .auth()
      .authentication(oauth())
      .contentType(JSON)

      .when()
      .delete("/api/help/{helpId}", helpId)

      .then()
      .log().all()
      .statusCode(200);

    var maybeHelp = helpRepository.findById(helpId);
    assertThat(maybeHelp).isPresent();
    assertThat(maybeHelp.get().getState()).isEqualTo(CLOSED);

    // after
    helpRepository.deleteAll();
  }
  
  @Test
  void fetchSingleHelpRequest() {
    var helpId = createSingleRequest();
    
    given()

      .auth()
      .authentication(oauth())
      .contentType(JSON)

      .when()
      .get("/api/help/{helpId}", helpId)

      .then()
      .log().all()
      .statusCode(200);

    // after
    helpRepository.deleteById(helpId);
  }

  @Test
  void assignToHelpRequest() {
    var helpId = createSingleRequest();

    given()

      .auth()
      .authentication(oauth(OTHER_USER_ID))
      .contentType(JSON)

      .when()
      .put("/api/help/{helpId}/assigned", helpId)

      .then()
      .log().all()
      .statusCode(200)
      .body("chatId", Matchers.equalTo(1000));

    // after
    chatUtils.cleanConversations();
    helpRepository.deleteById(helpId);
  }

  private long createSingleRequest() {
    var someHelp = new HelpEntity(
      SOME_USER_ID,
      List.of(ALLERGIES),
      "123456",
      URGENT,
      PHYSICAL_HELP,
      "Nothing to add"
    );

    return helpRepository.save(someHelp).getId();

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
