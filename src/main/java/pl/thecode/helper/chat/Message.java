package pl.thecode.helper.chat;


import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static pl.thecode.helper.chat.Message.TABLE_NAME;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = TABLE_NAME)
@Access(AccessType.FIELD)
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = PRIVATE)
class Message {

  public static final String TABLE_NAME = "message";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_gen")
  @SequenceGenerator(name = "message_gen", sequenceName = "message_seq", allocationSize = 1, initialValue = 100)
  private long id;

  @Enumerated(STRING)
  @ColumnTransformer(read = "owner::text", write = "?::ChatUser")
  @Column(name = "owner")
  private ChatUser owner;
  private long clientTimestamp;
  private String content;
  
  Message(ChatUser owner, long clientTimestamp, String content) {
    this.owner = owner;
    this.clientTimestamp = clientTimestamp;
    this.content = content;
  }

  MessageResponse toResponse() {
    return new MessageResponse(owner, clientTimestamp, content);
  }
}
