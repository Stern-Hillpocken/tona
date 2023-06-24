import { Component } from '@angular/core';
import { UserRegister } from 'src/app/models/user-register.model';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-register-container',
  templateUrl: './register-container.component.html',
  styleUrls: ['./register-container.component.scss']
})
export class RegisterContainerComponent {

  constructor(private authService: AuthService){}

  onReceive(user: UserRegister): void{
    this.authService.register(user);
  }
}
