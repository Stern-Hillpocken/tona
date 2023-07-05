import { Component, Input } from '@angular/core';
import { ChatMessage } from 'src/app/models/chat-message.model';

@Component({
  selector: 'app-chat-messages',
  templateUrl: './chat-messages.component.html',
  styleUrls: ['./chat-messages.component.scss']
})
export class ChatMessagesComponent {

  @Input()
  chatMessageList!: ChatMessage[];

  ngAfterViewInit(): void {
    console.log(document.getElementById('messages-container'))
    //document.getElementsByTagName("main")[0].scrollTop = 0;
  }

  formatedDate(date: string): string {
    return date.substring(11);
  }
}
