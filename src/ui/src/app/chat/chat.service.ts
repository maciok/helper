import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Message } from "./message";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {
  }

  getConversation(conversationId: number): Observable<Message[]> {
    return this.http.get(`/api/chat/${conversationId}/0`).pipe(
      map(res => res['messages']),
    );
  }

  sendMessage(conversationId: number, msg: string): Observable<any> {
    return this.http.put(`/api/chat/${conversationId}`, {
      content: msg,
      timestamp: Date.now()
    });
  }
}
