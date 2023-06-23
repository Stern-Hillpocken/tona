import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TokenInterceptor } from './core/token.interceptor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './components/pages/welcome/welcome.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { HeaderComponent } from './components/feature/header/header.component';
import { FormsModule } from '@angular/forms';
import { HubComponent } from './components/pages/hub/hub.component';
import { ErrorComponent } from './components/pages/error/error.component';
import { LoginComponent } from './components/pages/login/login.component';
import { PresentationComponent } from './components/feature/presentation/presentation.component';
import { NavBarComponent } from './components/feature/nav-bar/nav-bar.component';
import { HeroComponent } from './components/ui/hero/hero.component';
import { RegisterFormComponent } from './components/ui/register-form/register-form.component';
import { LoginFormComponent } from './components/ui/login-form/login-form.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HelpComponent } from './pages/help/help.component';
import { CommunityComponent } from './pages/community/community.component';
import { GameComponent } from './pages/game/game.component';
import { LoginContainerComponent } from './components/feature/login-container/login-container.component';
import { RegisterContainerComponent } from './components/feature/register-container/register-container.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    RegisterComponent,
    HeaderComponent,
    HubComponent,
    ErrorComponent,
    LoginComponent,
    PresentationComponent,
    NavBarComponent,
    HeroComponent,
    RegisterFormComponent,
    LoginFormComponent,
    ProfileComponent,
    HelpComponent,
    CommunityComponent,
    GameComponent,
    LoginContainerComponent,
    RegisterContainerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
