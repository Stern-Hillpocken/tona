import { Component } from '@angular/core';
import { UserRegister } from 'src/app/models/user-register.model';

@Component({
  selector: 'app-register-container',
  templateUrl: './register-container.component.html',
  styleUrls: ['./register-container.component.scss']
})
export class RegisterContainerComponent {

  onReceive(user: UserRegister): void{
    console.log(user)
  }
}
