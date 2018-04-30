import { Component } from '@angular/core';
import { NavController, AlertController, NavParams } from 'ionic-angular';
import { LoginPage } from '../login/login';

@Component({
  selector: 'page-contact',
  templateUrl: 'contact.html'
})
export class ContactPage {

  user;

  constructor(public navCtrl: NavController, private alertCtrl: AlertController, public navParams: NavParams) {
    this.user = sessionStorage.getItem("user");
  }

  logout(): void{
    this.user = undefined;
    sessionStorage.removeItem("user");
    this.presentConfirm();
  }

  presentConfirm(): void {
    let alert = this.alertCtrl.create({
      title: 'Sair',
      message: 'Deseja Sair?',
      buttons: [
        {
          text: 'Não',
          role: 'cancel',
          handler: () => {
            console.log('Cancel clicked');
          }
        },
        {
          text: 'Sim',
          handler: () => {
            this.navCtrl.setRoot(LoginPage);
          }
        }
      ]
    });
    alert.present();
  }

}
