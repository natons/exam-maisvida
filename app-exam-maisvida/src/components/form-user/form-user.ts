import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { RestProvider } from '../../providers/rest/rest';
import { AlertController, NavController } from 'ionic-angular';
import { HomePage } from '../../pages/home/home';

/**
 * Generated class for the FormUserComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'form-user',
  templateUrl: 'form-user.html'
})
export class FormUserComponent {

  text: string;
  access_token;

  formGroup: FormGroup;
  username: AbstractControl;
  password: AbstractControl;
  name: AbstractControl;

  valueUsername;
  valuePassword;
  valueName;

  formError = {
    username: {
      active: false,
      message: ""
    },
    name: {
      active: false,
      message: ""
    },
    password: {
      active: false,
      message: ""
    }
  } 
    

  constructor(public formBuilder: FormBuilder, public restProvider: RestProvider, 
    public alertCtrl: AlertController, public navCtrl: NavController) {
    console.log('Hello FormUserComponent Component');
    this.text = 'Hello World';
    this.buildForm();
    this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
      console.log("token "+access_token);
      this.access_token = access_token;
    }, err => {
    });
  }

  private buildForm(){
    this.formGroup = this.formBuilder.group({
      username:['', Validators.required],
      name:['', Validators.required],
      password:['', Validators.required],
    });

    this.username = this.formGroup.controls['username'];
    this.name = this.formGroup.controls['name'];
    this.password = this.formGroup.controls['password'];
  }

  validateForm() {
    let { username, name, password } = this.formGroup.controls;
 
    if (!this.formGroup.valid) {
      if (!username.valid) {
        this.formError.username.active = true;
        this.formError.username.message = "Ops! Usuário inválido";
      } else {
        this.formError.username.active = false;
        this.formError.username.message = "";
      }

      if (!name.valid) {
        this.formError.name.active = true;
        this.formError.name.message = "Ops! Nome inválido";
      } else {
        this.formError.name.active = false;
        this.formError.name.message = "";
      }

      if (!password.valid) {
        this.formError.password.active = true;
        this.formError.password.message = "Ops! Senha inválida";
      } else {
        this.formError.password.active = false;
        this.formError.password.message = "";
      }
    } else {
      this.save();
    }
  }

  save(){
    let user = {
      name: this.valueName,
      password: this.valuePassword,
      login: this.valueUsername,
      roles : [{
        name: 'ROLE_USER',
      }, {
        name: 'ROLE_ADMIN',
      }
      ]
    }
    this.restProvider.saveUser(user, this.access_token).then((data) => {
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
