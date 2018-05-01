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
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  logout(): void{
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
            this.user = undefined;
            localStorage.removeItem("user");
            this.navCtrl.setRoot(LoginPage);
          }
        }
      ]
    });
    alert.present();
  }

}
