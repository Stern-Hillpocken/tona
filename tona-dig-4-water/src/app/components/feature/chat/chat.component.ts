import { Component } from '@angular/core';
import { ChatMessage } from 'src/app/models/chat-message.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent {

  chatMessageList: ChatMessage[] = [];

  constructor(
    private expeditionService: ExpeditionService
  ){}

  ngOnInit(): void {
    this.expeditionService.getAllChatMessages().subscribe((messages: any) => {
      console.log(messages)
      this.chatMessageList = messages;
    });
  }

  onMessageReceive(message: string): void {
    this.expeditionService.sendMessage(message).subscribe();
  }
}
