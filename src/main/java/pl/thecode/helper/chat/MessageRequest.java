package pl.thecode.helper.chat;

import lombok.Value;

@Value
class MessageRequest {

  private long clientTimestamp;
  private String content;
}
