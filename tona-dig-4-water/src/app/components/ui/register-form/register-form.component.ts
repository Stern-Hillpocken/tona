import { Component, EventEmitter, Output } from '@angular/core';
import { UserRegister } from 'src/app/models/user-register.model';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent {

  @Output()
  formEmitter: EventEmitter<UserRegister> = new EventEmitter();

  userRegister: UserRegister = new UserRegister('','','');

  onSubmit(): void {
    this.formEmitter.emit(this.userRegister);
  }
}
