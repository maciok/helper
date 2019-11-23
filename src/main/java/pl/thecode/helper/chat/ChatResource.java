package pl.thecode.helper.chat;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chat")
class ChatResource {

  private final ConversationRepository conversationRepository;

  @GetMapping("/{conversationId}/{clientTimestamp}")
  ResponseEntity<ConversationResponse> fetch(
    @PathVariable("conversationId") long conversationId,
    @PathVariable("clientTimestamp") long clientTimestamp
  ) {
    return conversationRepository.findById(conversationId)
                                 .map(conversation -> conversation.cutOffMessagesOlderThan(clientTimestamp))
                                 .map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());

  }

  @PutMapping(value = "/{conversationId}", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity append(
    @AuthenticationPrincipal OAuth2AuthenticationToken userInfo,
    @PathVariable("conversationId") long conversationId,
    @RequestBody MessageRequest message) {
    var conversation = conversationRepository.findById(conversationId)
                                             .orElseThrow(() -> new IllegalStateException(format("Cannot find conversation for '%d'", conversationId)));

    var uuid = (String) userInfo.getPrincipal().getAttribute("sub");
    conversation.addMessage(message, uuid);
    conversationRepository.save(conversation);
    return ResponseEntity.ok().build();
  }
}
