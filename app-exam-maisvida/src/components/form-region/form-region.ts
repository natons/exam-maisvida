import { Component } from '@angular/core';
import { AlertController, NavController } from 'ionic-angular';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';
import { RestProvider } from '../../providers/rest/rest';
import { HomePage } from '../../pages/home/home';

/**
 * Generated class for the FormRegionComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'form-region',
  templateUrl: 'form-region.html'
})
export class FormRegionComponent {

  access_token;

  formGroup: FormGroup;
  state: AbstractControl;
  city: AbstractControl;
  newState: AbstractControl;

  valueCity;
  valueState;
  valueNewState = true;

  regions;

  formError = {
    state: {
      active: false,
      message: ""
    },
    city: {
      active: false,
      message: ""
    },
    newState: {
      active: false,
      message: ""
    }
  } 
    

  constructor(public formBuilder: FormBuilder, public restProvider: RestProvider, 
    public alertCtrl: AlertController, public navCtrl: NavController) {
    this.buildForm();
    this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
      console.log("token "+access_token);
      this.access_token = access_token;
      this.getRegions();
    }, err => {
    });
  }
  ionViewWillEnter(){
    this.getRegions();
  }

  private buildForm(){
    this.formGroup = this.formBuilder.group({
      state:['', Validators.required],
      city:['', Validators.required],
      newState:['', Validators.required],
    });

    this.state = this.formGroup.controls['state'];
    this.city = this.formGroup.controls['city'];
    this.newState = this.formGroup.controls['newState'];
  }

  private getRegions(){
    this.restProvider.getRegions(this.access_token).then((data) => {
      this.regions = data;
    }, err => {

    });
  }

  validateForm() {
    let { state, city, newState } = this.formGroup.controls;
 
    if (!this.formGroup.valid) {
      if (!state.valid) {
        this.formError.state.active = true;
        this.formError.state.message = "Ops! Estado inválido";
      } else {
        this.formError.state.active = false;
        this.formError.state.message = "";
      }

      if (!city.valid) {
        this.formError.city.active = true;
        this.formError.city.message = "Ops! Cidade inválida";
      } else {
        this.formError.city.active = false;
        this.formError.city.message = "";
      }

      if (!newState.valid) {
        this.formError.newState.active = true;
        this.formError.newState.message = "Ops! Novo Estado inválida";
      } else {
        this.formError.newState.active = false;
        this.formError.newState.message = "";
      }
    } else {
      this.save();
    }
  }

  getRegionId(){
    let id;
    this.regions.forEach(element => {
      if(element.state === this.valueState)
        id = element.id;
    });
    return id;
  }

  save(){
    let region = {
      id: this.valueNewState ? undefined : this.getRegionId(),
      state: this.valueState.toUpperCase(),
      cities: [
        {
          name: this.valueCity,
        }
      ],
    }
    console.log(region);
    this.restProvider.saveRegion(region, this.access_token).then((data) => {
      this.presentAlert("Operação Realizada com sucesso!");
    }, err => {
      console.log(err);
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
