import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Help, HelpResponse} from "../model/help-request.model";
import {Timebox} from "../model/timebox.model";
import {HelpCategory} from "../model/help-category.model";

@Injectable()
export class HelpService {

  constructor(private http: HttpClient) {
  }


  public load(): Observable<Help[]> {
    return this.http.get("/api/help")
      .pipe(
        map((h: HelpResponse[]) => h.map(help => HelpService.helpFromResponse(help)))
      )
  }

  private static helpFromResponse(h: HelpResponse): Help {
    return {
      id: h.id,
      helpTimeBox: Timebox[h.timeBox],
      category: HelpCategory[h.category],
      description: h.description,
      state: h.state
    }
  }
}
