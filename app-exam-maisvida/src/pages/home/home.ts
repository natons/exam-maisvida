import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { RestProvider, GET_DOCTORS, DELETE_DOCTOR } from '../../providers/rest/rest';
import { DoctorDetailsPage } from '../doctor-details/doctor-details';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  isUser = true;

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
      this.access_token = access_token;
      this.getDoctors();
    }, err => {
    });
    this.isUser = this.getRole("ROLE_USER");
  }

  private getRole(roleName): boolean{
    let user = JSON.parse(localStorage.getItem('user'));
    let role = false;
    user.roles.forEach(element => {
      if(element.name === roleName)
        role = true
    });
    return role;
  }

  getDoctors(){
    this.restProvider.get(GET_DOCTORS, this.access_token)
        .then((data: any) => {
          this.doctors = data;
        }, err => {
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
    this.restProvider.delete(DELETE_DOCTOR, this.access_token, id).then((data: any) => {
      this.getDoctors();
    }, err =>{
    });
  }
}
