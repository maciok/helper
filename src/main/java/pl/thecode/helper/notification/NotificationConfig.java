package pl.thecode.helper.notification;

import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

@Configuration
class NotificationConfig {


    @Bean
    PushService pushService() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }


        try {
            var pushService = new PushService();
            pushService.setPublicKey("BAc7usqOdJwQvcrzd_fj2hhpZFCC3tmnnVmTTU-zp8wBbzPxguWytzIWcvNgAF4M-hVCfLEL6Ay2OxmUuzIF__E");
            pushService.setPrivateKey("WKwSTj2OSVu9C0HuWexXWXOfIbKLzgsaRsUgCW8RmoM");
            return pushService;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
