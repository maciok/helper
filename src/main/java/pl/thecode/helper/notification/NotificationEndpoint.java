package pl.thecode.helper.notification;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE)
class NotificationEndpoint {


    private final NotificationService notificationService;

    @PostMapping("/api/subscription")
    public void subscribe(@RequestBody WebPushSubscription subscription, @AuthenticationPrincipal OAuth2User userInfo) {
        var uuid = (String) userInfo.getAttribute("sub");

        notificationService.subscribe(uuid, subscription);
    }

    @PostMapping("/notify")
    public WebPushMessage notifyAll(@RequestBody WebPushMessage message) {
        notificationService.notifyAll(message);
        return message;
    }

}
