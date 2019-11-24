import {Component, OnInit} from '@angular/core';
import {UserService} from "./user/user.service";
import {Router} from "@angular/router";
import {UserWarning} from "./model/user-warning.model";
import {NotificationService} from "./registration/notification.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ui';


  constructor(
    private userService: UserService,
    private router: Router,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.userService.load()
      .subscribe(
        u => {
          if (u.warnings.includes(UserWarning.NOT_REGISTERED)) {
            this.router.navigate(['/registration'])
          }
        },
        (err) => {
          if (err.status == 401) {
            window.location.href = "/oauth2/authorization/google"
          } else {
            console.log("Something went wrong") //todo
          }
        }
      );

    console.log("Listening to notification actions");
    this.notificationService.onAction().subscribe(
      (action) => {
        console.log("Received notification action", action);
        this.router.navigate(["help-list"]) //todo when list will be available
      }
    )


  }


}
