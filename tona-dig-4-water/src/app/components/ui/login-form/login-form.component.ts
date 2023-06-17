import { Component, EventEmitter, Output } from '@angular/core';
import { UserAuth } from 'src/app/models/user-auth.model';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent {

  @Output()
  formEmitter: EventEmitter<UserAuth> = new EventEmitter();

  userAuth: UserAuth = new UserAuth('','');

  onSubmit(): void {
    this.formEmitter.emit(this.userAuth);
  }
}
