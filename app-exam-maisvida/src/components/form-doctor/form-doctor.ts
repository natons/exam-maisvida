import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { RestProvider, GET_SPECIALTIES, GET_REGIONS, SAVE_DOCTOR } from '../../providers/rest/rest';
import { HomePage } from '../../pages/home/home';

/**
 * Generated class for the FormDoctorComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'form-doctor',
  templateUrl: 'form-doctor.html'
})
export class FormDoctorComponent {

  valueState;
  valueActive = false;
  valueFirstName;
  valueLastName;
  valueEmail;
  valueStatus;
  valueSpecialty;
  valueCity;
  
  private access_token;
  specialties;
  cities;
  regions;
  error;
  formError = {
    firstName: {
      active: false,
      message: ""
    }, 
    lastName: {
      active: false,
      message: ""
    }, 
    state: {
      active: false,
      message: ""
    }, 
    city: {
      active: false,
      message: ""
    }, 
    specialty: {
      active: false,
      message: ""
    }, 
    email: {
      active: false,
      message: ""
    }, 
    status: {
      active: false,
      message: ""
    },
    active: {
      active: false,
      message: ""
    },
  }

  formGroup: FormGroup;
  firstName: AbstractControl;
  lastName: AbstractControl;
  state: AbstractControl;
  city: AbstractControl;
  specialty: AbstractControl;
  email: AbstractControl;
  status: AbstractControl;
  active: AbstractControl;

  constructor(public navCtrl: NavController, public formBuilder: FormBuilder, 
    public restProvider: RestProvider, public alertCtrl: AlertController) {
      this.buildForm();
      this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
        this.access_token = access_token;
        this.getSpecialties();
        this.getRegions();
      }, err => {
      });
  }

  private buildForm(){
    this.formGroup = this.formBuilder.group({
      firstName:['', Validators.required],
      lastName:['', Validators.required],
      state:['', Validators.required],
      city:['', Validators.required],
      specialty: ['', Validators.required],
      email:['', Validators.required],
      status:['', Validators.required],
      active:['', Validators.required],
    });

    this.firstName = this.formGroup.controls['firstname'];
    this.lastName = this.formGroup.controls['lastname'];
    this.state = this.formGroup.controls['state'];
    this.city = this.formGroup.controls['city'];
    this.specialty = this.formGroup.controls['specialty'];
    this.email = this.formGroup.controls['email'];
    this.status = this.formGroup.controls['status'];
    this.active = this.formGroup.controls['active'];
  }

  validateForm() {
    let { firstName, lastName, state, city, specialty, email, status, active } = this.formGroup.controls;
 
    if (!this.formGroup.valid) {
      if (!firstName.valid) {
        this.formError.firstName.active = true;
        this.formError.firstName.message = "Ops! Nome inválido";
      } else {
        this.formError.firstName.active = false;
        this.formError.firstName.message = "";
      }
 
      if (!lastName.valid) {
        this.formError.lastName.active = true;
        this.formError.lastName.message = "Ops! Nome inválido";
      } else {
        this.formError.lastName.active = false;
        this.formError.lastName.message = "";
      }

      if (!state.valid) {
        this.formError.state.active = true;
        this.formError.state.message = "Ops! Estado inválido";
      } else {
        this.formError.state.active = false;
        this.formError.state.message = "";
      }

      if (!status.valid) {
        this.formError.status.active = true;
        this.formError.status.message = "Ops! Status inválido";
      } else {
        this.formError.status.active = false;
        this.formError.status.message = "";
      }

      if (!city.valid) {
        this.formError.city.active = true;
        this.formError.city.message = "Ops! Cidade inválida";
      } else {
        this.formError.city.active = false;
        this.formError.city.message = "";
      }

      if (!active.valid) {
        this.formError.active.active = true;
        this.formError.active.message = "Ops! Ativo inválido";
      } else {
        this.formError.active.active = false;
        this.formError.active.message = "";
      }

      if (!email.valid) {
        this.formError.email.active = true;
        this.formError.email.message = "Ops! Email inválido";
      } else {
        this.formError.email.active = false;
        this.formError.email.message = "";
      }

      if (!specialty.valid) {
        this.formError.specialty.active = true;
        this.formError.specialty.message = "Ops! Especialidade inválido";
      } else {
        this.formError.specialty.active = false;
        this.formError.specialty.message = "";
      }
    } else {
      this.saveDoctor();
    }
  }

  getSpecialties(){
    this.restProvider.get(GET_SPECIALTIES, this.access_token)
        .then((data: any) => {
          this.specialties = data;
        }, err => {
          this.error = err;
        });
  }

  getRegions(){
    this.restProvider.get(GET_REGIONS, this.access_token)
        .then((data: any) => {
          this.regions = data;
        }, err => {
          this.error = err;
        });
  }

  getSpecialty(): any{
    let specialty;
    this.specialties.forEach(element => {
      if(element.name == this.valueSpecialty){
        specialty = element;
      }
    });
    return specialty;
  }

  getCity(): any{
    let city;
    this.getRegion().cities.forEach(element => {
      if(element.name === this.valueCity){
        city = element;
      }
    });
    return city;
  }

  getRegion(): any {
    let region;
    this.regions.forEach(element => {
      if(element.state === this.valueState){
        region = element;
      }
    });
    return region;
  }

  saveDoctor(): void {
    let doctor = {
      firstName: this.valueFirstName,
      lastName: this.valueLastName,
      email: this.valueEmail,
      active: this.valueActive,
      status: this.valueStatus,
      specialty : this.getSpecialty(),
      region: this.getRegion(),
      city: this.getCity()
    }
    this.restProvider.post(SAVE_DOCTOR, this.access_token, doctor).then((response) => {
      this.presentAlert("Operação Realizada com sucesso!");
    }, err=> {
      this.presentAlert("Ops! ocorreu um erro!");
    });
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
