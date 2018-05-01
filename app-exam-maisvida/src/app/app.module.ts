import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { HomePage } from '../pages/home/home';
import { TabsPage } from '../pages/tabs/tabs';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HttpClientModule } from '@angular/common/http';
import { RestProvider } from '../providers/rest/rest';
import { DoctorDetailsPage } from '../pages/doctor-details/doctor-details';
import { LoginPage } from '../pages/login/login';
import { FormUserComponent } from '../components/form-user/form-user';
import { FormRegionComponent } from '../components/form-region/form-region';
import { FormSpecialtyComponent } from '../components/form-specialty/form-specialty';
import { FormDoctorComponent } from '../components/form-doctor/form-doctor';
import { FormLoginComponent } from '../components/form-login/form-login';

@NgModule({
  declarations: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
    DoctorDetailsPage,
    LoginPage,
    FormUserComponent,
    FormRegionComponent,
    FormSpecialtyComponent,
    FormDoctorComponent,
    FormLoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
    DoctorDetailsPage,
    LoginPage,
    FormUserComponent,
    FormRegionComponent,
    FormSpecialtyComponent,
    FormDoctorComponent,
    FormLoginComponent
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    RestProvider
  ]
})
export class AppModule {}
