package pl.thecode.helper.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = SubscriptionEntity.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
class SubscriptionEntity {

    static final String TABLE_NAME = "app_notification_subscriptions";
    private static final String SEQ_NAME = TABLE_NAME + "_seq";
    private static final String IDENTITY_GENERATOR = SEQ_NAME + "_gen";
    private static final int SEQ_INITIAL_VALUE = 1000;
    private static final int SEQ_INCREMENT_BY_VALUE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IDENTITY_GENERATOR)
    @SequenceGenerator(name = IDENTITY_GENERATOR, sequenceName = SEQ_NAME, allocationSize = SEQ_INCREMENT_BY_VALUE, initialValue = SEQ_INITIAL_VALUE)
    private Long id;

    @Basic
    private String uuid;

    @Basic
    private String subscription;

    public static SubscriptionEntity create(String uuid, String subscription) {
        return new SubscriptionEntity(null, uuid, subscription);
    }
}
