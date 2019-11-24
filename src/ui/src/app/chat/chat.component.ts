import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ChatService} from "./chat.service";
import {Message} from "./message";
import {Observable, of} from "rxjs";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ChatComponent implements OnInit {

  messages: Observable<Message[]>;
  mockedMessages: Message[] = [];
  form: FormGroup;
  conversationId: number;

  constructor(private service: ChatService, private fb: FormBuilder, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.conversationId = +this.route.snapshot.queryParamMap.get('id');
    console.log(this.conversationId);
    this.form = this.fb.group({
      newMessage: this.fb.control('')
    });
    // this.messages = this.service.getConversation(this.conversationId);
    this.messages = of([]);
  }

  onSubmit() {
    if (this.form.valid) {
      const msg = this.messageControl.value as string;

      this.mockedMessages.push({content: msg, owner: "VOLUNTEER"});
      this.messages = of(this.mockedMessages);
    }
  }

  get messageControl(): FormControl {
    return this.form.get('newMessage') as FormControl;
  }

}
