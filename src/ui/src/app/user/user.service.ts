import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, tap} from "rxjs/operators";
import {User} from "../model/user.model";
import {Observable, of} from "rxjs";

@Injectable()
export class UserService {

  user: User = null;

  constructor(private http: HttpClient) {
  }

  load(): Observable<User> {
    if (this.user == null) {
      return this.http.get("/api/user")
        .pipe(
          map((r: UserResponse) => this.userFromResponse(r)),
          tap((user: User) => this.user = user)
        )
    } else {
      return of(this.user);
    }
  }


  private userFromResponse(response: UserResponse): User {
    return {
      firstName: response.firstName,
      lastName: response.lastName,
      roles: null,
      disabilities: null,
      age: null,
    }
  }
}
