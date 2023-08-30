import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Majagaba } from 'src/app/models/majagaba.model';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-profile-container',
  templateUrl: './profile-container.component.html',
  styleUrls: ['./profile-container.component.scss']
})
export class ProfileContainerComponent {

  user: User = new User("","","",new Majagaba(0,0,0,"",[],[],[],0,"",0,0,0,0,0));

  constructor(
    private userService: UserService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.userService.getMe().subscribe((me: User) => {
      console.log(me)
      this.user = me;
    });
  }

  onDefaultProfilePictureReceive(value: number): void {
    this.userService.changeProfilePicture("assets/images/default-profile/"+value+".jpg").subscribe((e:any) => {
      this.userService.getMe().subscribe((me: User) => {
        this.user = me;
      });
    });
  }

}
