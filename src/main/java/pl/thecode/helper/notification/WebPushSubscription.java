package pl.thecode.helper.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class WebPushSubscription {


    private String endpoint;

    private String expirationTime;

    private Keys keys;


    @JsonCreator
    public WebPushSubscription(
            @JsonProperty("endpoint") String endpoint,
            @JsonProperty("expirationTime") String expirationTime,
            @JsonProperty("keys") Keys keys) {
        this.endpoint = endpoint;
        this.expirationTime = expirationTime;
        this.keys = keys;
    }

    @Data
    public static class Keys {


        private String auth;

        private String p256dh;

        @JsonCreator
        public Keys(
                @JsonProperty("auth")
                        String auth,
                @JsonProperty("p256dh")
                        String p256dh) {
            this.auth = auth;
            this.p256dh = p256dh;
        }
    }

}

