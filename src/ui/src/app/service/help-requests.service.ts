import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { HelpRequest } from "../model/help-request.model";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HelpRequestsService {

  constructor(private http: HttpClient) {
  }

  save(req: HelpRequest): Observable<any> {
    return this.http.post('/api/help', req);
  }
}
