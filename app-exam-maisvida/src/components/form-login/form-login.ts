import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { RestProvider, AUTHENTICATE_USER } from '../../providers/rest/rest';
import { TabsPage } from '../../pages/tabs/tabs';

/**
 * Generated class for the FormLoginComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'form-login',
  templateUrl: 'form-login.html'
})
export class FormLoginComponent {

  formGroup: FormGroup;
  username: AbstractControl;
  password: AbstractControl;
  formError = {
    username: {
      active: false,
      message: ""
    }, 
    password: {
      active: false,
      message: ""
    }, 
  }

  valueUsername;
  valuePassword;

  constructor(public navCtrl: NavController, public navParams: NavParams, 
    public formBuilder: FormBuilder, public restProvider: RestProvider,
    private alertCtrl: AlertController) {
    this.buildForm();
  }

  buildForm(){
    this.formGroup = this.formBuilder.group({
      username:['', Validators.required],
      password:['', Validators.required],
    });

    this.username = this.formGroup.controls['password'];
    this.password = this.formGroup.controls['username'];
  }

  validateForm() {
    let { username, password } = this.formGroup.controls;
 
    if (!this.formGroup.valid) {
      if (!username.valid) {
        this.formError.username.active = true;
        this.formError.username.message = "Ops! Usuário inválido";
      } else {
        this.formError.username.active = false;
        this.formError.username.message = "";
      }

      if (!password.valid) {
        this.formError.password.active = true;
        this.formError.password.message = "Ops! Senha inválida";
      } else {
        this.formError.password.active = false;
        this.formError.password.message = "";
      }

    } else {
      this.authenticate();
    }
  }

  authenticate(){
    let user = {
      login: this.valueUsername,
      password: this.valuePassword,
    }
    this.restProvider.postNoToken(AUTHENTICATE_USER, user).then((data: any) => {
      localStorage.setItem("user", JSON.stringify(data));
      this.navCtrl.setRoot(TabsPage);
    }, err => {
      this.presentConfirm();
    })
  }

  presentConfirm(): void {
    let alert = this.alertCtrl.create({
      title: 'Erro',
      message: 'Usuário ou senha incorretos!',
      buttons: ['OK']
    });
    alert.present();
  }

}
