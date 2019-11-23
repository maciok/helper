import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, tap} from "rxjs/operators";
import {User} from "../model/user.model";
import {Observable, of} from "rxjs";
import {UserResponse} from "./user.response";
import {UserWarning} from "../model/user-warning.model";

@Injectable()
export class UserService {

  user: User = null;

  constructor(private http: HttpClient) {
  }

  load(): Observable<User> {
    if (this.user == null) {
      return this.http.get("/api/user")
        .pipe(
          map((r: UserResponse) => UserService.userFromResponse(r)),
          tap((user: User) => this.user = user)
        )
    } else {
      return of(this.user);
    }
  }

  reload(): Observable<User> {
    console.log("Reloading user");
    this.user = null;
    return this.load();
  }


  private static userFromResponse(response: UserResponse): User {
    return {
      firstName: response.givenName,
      lastName: response.familyName,
      roles: response.roles,
      disabilities: null,
      age: response.age,
      warnings: response.warnings.map(w => UserWarning[w]),
    }
  }
}
