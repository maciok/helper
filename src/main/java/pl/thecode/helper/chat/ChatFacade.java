package pl.thecode.helper.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatFacade {

  private final ConversationRepository conversationRepository;
  
  public ChatDto createNewChat(String needyId, String volunteerId) {
    return conversationRepository.save(Conversation.createNew(needyId, volunteerId))
                                 .toDto();
  }
}
