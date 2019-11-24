import {Injectable} from '@angular/core';
import {SwPush} from "@angular/service-worker";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable()
export class NotificationService {

  readonly VAPID_PUBLIC_KEY = "BAc7usqOdJwQvcrzd_fj2hhpZFCC3tmnnVmTTU-zp8wBbzPxguWytzIWcvNgAF4M-hVCfLEL6Ay2OxmUuzIF__E";

  constructor(
    private swPush: SwPush,
    private http: HttpClient
  ) {
  }


  subscribeToNotifications() {
    console.log("will subscribe user");
    this.swPush.requestSubscription({
      serverPublicKey: this.VAPID_PUBLIC_KEY
    })
      .then(sub => this.addPushSubscriber(sub).subscribe())
      .catch(err => console.error("Could not subscribe to notifications", err));
  }

  addPushSubscriber(sub: PushSubscription) {
    console.log("subscribing on a backend");
    return this.http.post("/api/subscription", sub.toJSON())
  }

  onAction() {
    return this.swPush.notificationClicks
      .pipe(
        map(payload => payload.action)
      )
  }
}
