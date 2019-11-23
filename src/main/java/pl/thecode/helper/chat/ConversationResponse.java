package pl.thecode.helper.chat;

import java.util.List;
import lombok.Value;

@Value
class ConversationResponse {

  private long id;
  private List<MessageResponse> messages;
}
