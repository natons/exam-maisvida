import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

/**
 * Generated class for the DoctorDatailsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
@Component({
  selector: 'page-doctor-details',
  templateUrl: 'doctor-details.html',
})
export class DoctorDetailsPage {

  doctor;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.doctor = this.navParams.get("doctor");
  }

}
