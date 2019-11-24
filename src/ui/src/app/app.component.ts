import {Component, OnInit} from '@angular/core';
import {UserService} from "./user/user.service";
import {Router} from "@angular/router";
import {UserWarning} from "./model/user-warning.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(
    private userService: UserService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    /*this.userService.load()
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
      )*/


  }


}
