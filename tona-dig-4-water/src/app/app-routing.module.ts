import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from './core/user.guard';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { ErrorComponent } from './pages/error/error.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { HubComponent } from './pages/hub/hub.component';
import { CommunityComponent } from './pages/community/community.component';
import { HelpComponent } from './pages/help/help.component';
import { OverviewComponent } from './pages/game/overview/overview.component';
import { MessagesComponent } from './pages/game/messages/messages.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { UserInGameGuard } from './core/user-in-game.guard';
import { InformationsComponent } from './pages/game/informations/informations.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'community', component: CommunityComponent},
  { path: 'help', component: HelpComponent},
  { path: 'hub', component: HubComponent, canActivate: [UserGuard] },
  { path: 'game/overview', component: OverviewComponent, canActivate: [UserInGameGuard] },
  { path: 'game/messages', component: MessagesComponent, canActivate: [UserInGameGuard] },
  { path: 'game/informations', component: InformationsComponent, canActivate: [UserInGameGuard] },
  { path: '**', component: ErrorComponent, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }