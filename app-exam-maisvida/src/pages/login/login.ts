import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { TabsPage } from '../tabs/tabs';
import { RestProvider } from '../../providers/rest/rest';

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

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
    this.restProvider.authenticateUser(user).then((data: any) => {
      sessionStorage.setItem("user", data);
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
