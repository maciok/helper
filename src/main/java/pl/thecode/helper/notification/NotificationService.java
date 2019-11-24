package pl.thecode.helper.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationService {

    private ObjectMapper objectMapper;

    private PushService pushService;

    private NotificationSubscriptionRepository subscriptionRepository;

    public void subscribe(String uuid, WebPushSubscription subscription) {
        var subscriptionEntity = SubscriptionEntity.create(uuid, toJson(subscription));
        subscriptionRepository.save(subscriptionEntity);
    }

    public void notifyUsers(List<String> uuids, Long helpId) {
        var message = WebPushMessage.helpNeeded(helpId);

        subscriptionRepository.findAll().stream()
                .filter(sub -> uuids.contains(sub.getUuid()))
                .map(SubscriptionEntity::getSubscription)
                .map(this::subscriptionFromJson)
                .map(subscription -> toNotification(message, subscription))
                .forEach(this::send);
    }

    public void notifyAll(WebPushMessage message) {
        subscriptionRepository.findAll().stream()
                .map(SubscriptionEntity::getSubscription)
                .map(this::subscriptionFromJson)
                .map(subscription -> toNotification(message, subscription))
                .forEach(this::send);
    }

    private void send(Notification notification) {
        try {
            pushService.send(notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Notification toNotification(WebPushMessage message, WebPushSubscription subscription) {
        try {
            return new Notification(
                    subscription.getEndpoint(),
                    subscription.getKeys().getP256dh(),
                    subscription.getKeys().getAuth(),
                    objectMapper.writeValueAsBytes(Map.of("notification", message))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WebPushSubscription subscriptionFromJson(String subscription) {
        try {
            return objectMapper.readValue(subscription, WebPushSubscription.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJson(WebPushSubscription subscription) {
        try {
            return objectMapper.writeValueAsString(subscription);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
