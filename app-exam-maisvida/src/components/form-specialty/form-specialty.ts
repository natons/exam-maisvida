import { Component } from '@angular/core';
import { FormGroup, AbstractControl, Validators, FormBuilder } from '@angular/forms';
import { AlertController, NavController } from 'ionic-angular';
import { RestProvider, SAVE_SPECIALTY } from '../../providers/rest/rest';
import { HomePage } from '../../pages/home/home';

/**
 * Generated class for the FormSpecialtyComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'form-specialty',
  templateUrl: 'form-specialty.html'
})
export class FormSpecialtyComponent {

  private access_token;

  formGroup: FormGroup;
  name: AbstractControl;

  valueName;

  formError = {
    name: {
      active: false,
      message: ""
    }
  } 

  constructor(public formBuilder: FormBuilder, public restProvider: RestProvider, 
    public alertCtrl: AlertController, public navCtrl: NavController) {
    this.buildForm();
    this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
      this.access_token = access_token;
    }, err => {
    });
  }

  private buildForm(){
    this.formGroup = this.formBuilder.group({
      name:['', Validators.required],
    });

    this.name = this.formGroup.controls['name'];
  }

  validateForm() {
    let { name } = this.formGroup.controls;
 
    if (!this.formGroup.valid) {
      if (!name.valid) {
        this.formError.name.active = true;
        this.formError.name.message = "Ops! Nome inválido";
      } else {
        this.formError.name.active = false;
        this.formError.name.message = "";
      }
    } else {
      this.save();
    }
  }

  save(){
    let specialty = {
      name: this.valueName,
    }
    this.restProvider.post(SAVE_SPECIALTY, this.access_token, specialty).then((data) => {
      this.presentAlert("Operação Realizada com sucesso!");
    }, err => {
      this.presentAlert("Operação não realizada!")
    })
  }

  presentAlert(message) {
    let alert = this.alertCtrl.create({
      title: 'Operação',
      subTitle: message,
      buttons: [{
        text: 'Ok',
        handler: () => {
          this.navCtrl.setRoot(HomePage);
        }
      }]
    });
    alert.present();
  }

}
