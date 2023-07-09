import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-profile-image',
  templateUrl: './profile-image.component.html',
  styleUrls: ['./profile-image.component.scss']
})
export class ProfileImageComponent {

  @Input()
  user!: User;

  @Output()
  defaultProfilePictureEmitter: EventEmitter<number> = new EventEmitter();

  onClickProfilePicture(value: number): void {
    this.defaultProfilePictureEmitter.emit(value);
  }

}
