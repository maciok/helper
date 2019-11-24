package pl.thecode.helper.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatUtils {

  ConversationRepository conversationRepository;
  
  public void cleanConversations() {
    conversationRepository.deleteAll();
  }
}
