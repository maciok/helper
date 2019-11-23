package pl.thecode.helper.chat;

import lombok.Value;

@Value
class MessageResponse {

  private ChatUser owner;
  private long clientTimestamp;
  private String content;
}
