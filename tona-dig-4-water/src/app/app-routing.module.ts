import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from './core/user.guard';
import { WelcomeComponent } from './components/pages/welcome/welcome.component';
import { ErrorComponent } from './components/pages/error/error.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { LoginComponent } from './components/pages/login/login.component';
import { HubComponent } from './components/pages/hub/hub.component';
import { GameComponent } from './pages/game/game.component';
import { CommunityComponent } from './pages/community/community.component';
import { HelpComponent } from './pages/help/help.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: 'community', component: CommunityComponent},
  { path: 'help', component: HelpComponent},
  { path: 'hub', component: HubComponent, canActivate: [UserGuard] },
  { path: 'game', component: GameComponent, canActivate: [UserGuard] },
  { path: '**', component: ErrorComponent, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }