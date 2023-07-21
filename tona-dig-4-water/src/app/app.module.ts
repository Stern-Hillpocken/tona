import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TokenInterceptor } from './core/token.interceptor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { RegisterComponent } from './pages/register/register.component';
import { HeaderComponent } from './components/feature/header/header.component';
import { FormsModule } from '@angular/forms';
import { HubComponent } from './pages/hub/hub.component';
import { ErrorComponent } from './pages/error/error.component';
import { LoginComponent } from './pages/login/login.component';
import { PresentationComponent } from './components/feature/presentation/presentation.component';
import { NavBarComponent } from './components/feature/nav-bar/nav-bar.component';
import { HeroComponent } from './components/ui/hero/hero.component';
import { RegisterFormComponent } from './components/ui/register-form/register-form.component';
import { LoginFormComponent } from './components/ui/login-form/login-form.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HelpComponent } from './pages/help/help.component';
import { CommunityComponent } from './pages/community/community.component';
import { LoginContainerComponent } from './components/feature/login-container/login-container.component';
import { RegisterContainerComponent } from './components/feature/register-container/register-container.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { PopupComponent } from './components/ui/popup/popup.component';
import { HubSetupComponent } from './components/feature/hub-setup/hub-setup.component';
import { HubPodCreationComponent } from './components/ui/hub-pod-creation/hub-pod-creation.component';
import { HubPodAvailableComponent } from './components/ui/hub-pod-available/hub-pod-available.component';
import { PodRegisterCardComponent } from './components/ui/pod-register-card/pod-register-card.component';
import { OverviewComponent } from './pages/game/overview/overview.component';
import { OverviewPodComponent } from './components/feature/overview-pod/overview-pod.component';
import { OverviewPodMapComponent } from './components/ui/overview-pod-map/overview-pod-map.component';
import { MessagesComponent } from './pages/game/messages/messages.component';
import { ChatComponent } from './components/feature/chat/chat.component';
import { ChatFormComponent } from './components/ui/chat-form/chat-form.component';
import { ChatMessagesComponent } from './components/ui/chat-messages/chat-messages.component';
import { OverviewPodDiceComponent } from './components/ui/overview-pod-dice/overview-pod-dice.component';
import { ProfileContainerComponent } from './components/feature/profile-container/profile-container.component';
import { ProfileImageComponent } from './components/ui/profile-image/profile-image.component';
import { InformationsComponent } from './pages/game/informations/informations.component';
import { InformationsContainerComponent } from './components/feature/informations-container/informations-container.component';
import { InformationsExpeditionComponent } from './components/ui/informations-expedition/informations-expedition.component';
import { OverviewPodBagComponent } from './components/ui/overview-pod-bag/overview-pod-bag.component';
import { OverviewPodRoomActionsComponent } from './components/ui/overview-pod-room-actions/overview-pod-room-actions.component';

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
    LoginContainerComponent,
    RegisterContainerComponent,
    PopupComponent,
    HubSetupComponent,
    HubPodCreationComponent,
    HubPodAvailableComponent,
    PodRegisterCardComponent,
    OverviewComponent,
    OverviewPodComponent,
    OverviewPodMapComponent,
    MessagesComponent,
    ChatComponent,
    ChatFormComponent,
    ChatMessagesComponent,
    OverviewPodDiceComponent,
    ProfileContainerComponent,
    ProfileImageComponent,
    InformationsComponent,
    InformationsContainerComponent,
    InformationsExpeditionComponent,
    OverviewPodBagComponent,
    OverviewPodRoomActionsComponent
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
