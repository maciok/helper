package pl.thecode.helper.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NotificationSubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}
