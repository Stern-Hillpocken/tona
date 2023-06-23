import { Component } from '@angular/core';
import { UserAuth } from 'src/app/models/user-auth.model';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-login-container',
  templateUrl: './login-container.component.html',
  styleUrls: ['./login-container.component.scss']
})
export class LoginContainerComponent {

  constructor(private authService: AuthService){}

  onReceive(user: UserAuth): void {
    this.authService.login(user);
  }
}
