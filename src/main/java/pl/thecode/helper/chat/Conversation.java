package pl.thecode.helper.chat;

import static javax.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PRIVATE;
import static pl.thecode.helper.chat.ChatUser.NEEDY;
import static pl.thecode.helper.chat.ChatUser.VOLUNTEER;
import static pl.thecode.helper.chat.Conversation.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = TABLE_NAME)
@Access(AccessType.FIELD)
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = PRIVATE)
class Conversation {

  public static final String TABLE_NAME = "conversation";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "conversation_gen")
  @SequenceGenerator(name = "conversation_gen", sequenceName = "conversation_seq", allocationSize = 1, initialValue = 100)
  private long id;
  private String needyId;
  private String volunteerId;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = EAGER)
  @JoinColumn(name = "conversationId")
  private List<Message> messages = new ArrayList<>();

  Conversation(String needyId, String volunteerId, List<Message> messages) {
    this.needyId = needyId;
    this.volunteerId = volunteerId;
    this.messages = messages;
  }

  ConversationResponse cutOffMessagesOlderThan(long timestamp) {
    var newMessages = messages.stream()
      .filter(message -> message.getClientTimestamp() < timestamp)
      .map(Message::toResponse)
      .collect(Collectors.toUnmodifiableList());
    return new ConversationResponse(id, newMessages);
  }

  void addMessage(MessageRequest message, String userId) {
    ChatUser user = needyId.equals(userId) ? NEEDY : VOLUNTEER;
    var newMessage = new Message(user, message.getClientTimestamp(), message.getContent());
    messages.add(newMessage);
  }
}
