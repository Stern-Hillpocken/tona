import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-chat-form',
  templateUrl: './chat-form.component.html',
  styleUrls: ['./chat-form.component.scss']
})
export class ChatFormComponent {

  message: string = "";

  @Output()
  messageEmitter: EventEmitter<string> = new EventEmitter;

  onSubmit(): void {
    this.messageEmitter.emit(this.message);
  }
}
