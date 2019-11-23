import {Component, OnInit} from '@angular/core';
import {UserService} from "./user/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ui';


  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.load()
      .subscribe(
        u => console.log("User loaded", u),
        (err) => {
          if (err.status == 401) {
            window.location.href = "/oauth2/authorization/google"
          } else {
            alert("Something went wrong") //todo
          }
        }
      )


  }


}
