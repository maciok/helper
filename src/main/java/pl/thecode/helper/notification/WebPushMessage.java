package pl.thecode.helper.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebPushMessage {

    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;
    @JsonProperty("actions")
    public List<PushAction> actions;


    public static WebPushMessage helpNeeded(Long helpId) {
        return new WebPushMessage(
                "Hej, ty",
                "Ktoś w pobliżu potrzebuje twojej pomocy",
                List.of(
                        new PushAction(helpId.toString(), "Sprawdź szczegóły")
                )
        );


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PushAction {
        @JsonProperty("action")
        String action;
        @JsonProperty("title")
        String title;
    }
}

