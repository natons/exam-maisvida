import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { RestProvider } from '../../providers/rest/rest';
import { DoctorDetailsPage } from '../doctor-details/doctor-details';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  doctors = [];
  error: any;
  private access_token;

  constructor(public navCtrl: NavController, public restProvider: RestProvider,
    private alertCtrl: AlertController) {
  }

  showDetails(doctor){
    this.navCtrl.push(DoctorDetailsPage, {
      doctor: doctor
    });
  }

  ionViewWillEnter(){
    this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
      console.log("token "+access_token);
      this.access_token = access_token;
      this.getDoctors();
    }, err => {
    });
  }

  getDoctors(){
    this.restProvider.getDoctors(this.access_token)
        .then((data: any) => {
          this.doctors = data;
          console.log("Aqui vem os dados");
          console.log(data);
        }, err => {
          console.log(err)
          this.error = err;
        });
  }

  presentConfirm(id, name): void {
    let alert = this.alertCtrl.create({
      title: 'Confirmar Exclusão',
      message: 'Deseja Excluir ' + name + '?',
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
            this.deleteDoctor(id);
          }
        }
      ]
    });
    alert.present();
  }


  deleteDoctor(id): void {
    this.restProvider.deleteDoctor(id, this.access_token).then((data: any) => {
      this.getDoctors();
    }, err =>{

    });
  }
}
