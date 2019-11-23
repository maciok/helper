package pl.thecode.helper.chat;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.thecode.helper.OauthUtil.oauth;
import static pl.thecode.helper.chat.ChatUser.NEEDY;
import static pl.thecode.helper.chat.ChatUser.VOLUNTEER;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("local")
class ChatIT {

  static String NEEDY_ID = "1234567";
  static String VOLUNTEER_ID = "7654321";

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Autowired
  ConversationRepository conversationRepository;


  @Test
  @WithMockUser()
  void fetchMessage() {
    long conversationId = createConversation();
    long clientTimestamp = 10;

    given()

      .auth()
      .authentication(oauth())
      
      .when()
      .get("/api/chat/{conversationId}/{clientTimestamp}", conversationId, clientTimestamp)

      .then()
      .log().ifValidationFails()
      .statusCode(200);

    // after
    conversationRepository.deleteById(conversationId);
  }

  @Test
  void postMessage() {
    long conversationId = createConversation();

    given()
      .auth()
      .authentication(oauth())
      
      .body(newMessageBody())
      .contentType(JSON)

      .when()
      .put("/api/chat/{conversationId}", conversationId)

      .then()
      .log().ifValidationFails()
      .statusCode(200);

    var maybeConversation = conversationRepository.findById(conversationId);
    assertThat(maybeConversation).isPresent();
    assertThat(maybeConversation.get().getMessages()).hasSize(4);

    // after
    conversationRepository.deleteById(conversationId);

  }

  private Map<String, Object> newMessageBody() {
    return Map.of(
      "clientTimestamp", 40,
      "content", "some message"
    );
  }

  private long createConversation() {

    var messages = List.of(
      new Message(NEEDY, 10, "starter message"),
      new Message(VOLUNTEER, 20, "middle message"),
      new Message(NEEDY, 30, "ending message")
    );
    var conversation = new Conversation(NEEDY_ID, VOLUNTEER_ID, messages);
    var newConversation = conversationRepository.save(conversation);
    return newConversation.getId();
  }
}
