import { Component } from '@angular/core';
import { UserAuth } from 'src/app/models/user-auth.model';

@Component({
  selector: 'app-login-container',
  templateUrl: './login-container.component.html',
  styleUrls: ['./login-container.component.scss']
})
export class LoginContainerComponent {

  onReceive(user: UserAuth): void {
    console.log(user)
  }
}
