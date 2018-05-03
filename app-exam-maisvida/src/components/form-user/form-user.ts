import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { RestProvider, SAVE_USER, GET_ROLES } from '../../providers/rest/rest';
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

  private access_token;

  formGroup: FormGroup;
  username: AbstractControl;
  password: AbstractControl;
  name: AbstractControl;
  isUser: AbstractControl;
  isAdmin: AbstractControl;

  valueUsername;
  valuePassword;
  valueName;
  valueIsUser = true;
  valueIsAdmin = false;

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
    },
    roles: {
      active: false,
      message: ""
    }
  }
  
  roles;
    

  constructor(public formBuilder: FormBuilder, public restProvider: RestProvider, 
    public alertCtrl: AlertController, public navCtrl: NavController) {
    this.buildForm();
    this.restProvider.authenticate("roy", "spring", "password", "write").then(access_token => {
      this.access_token = access_token;
      this.getRoles();
    }, err => {
    });
  }

  private buildForm(){
    this.formGroup = this.formBuilder.group({
      username:['', Validators.required],
      name:['', Validators.required],
      password:['', Validators.required],
      isAdmin:['', Validators.required],
      isUser:['', Validators.required]
    });

    this.username = this.formGroup.controls['username'];
    this.name = this.formGroup.controls['name'];
    this.password = this.formGroup.controls['password'];
    this.isAdmin = this.formGroup.controls['isAdmin'];
    this.isUser = this.formGroup.controls['isUser'];
  }

  validateForm() {
    let { username, name, password } = this.formGroup.controls;

    if (!this.valueIsAdmin && !this.valueIsUser) {
      this.formError.roles.active = true;
      this.formError.roles.message = "Ops! Uma permissão precisa ser concedida!";
      this.formGroup.controls['isUser'].setErrors({'incorrect': true});
    } else {
      this.formError.roles.active = false;
      this.formError.roles.message = "";
      this.formGroup.controls['isUser'].setErrors(null);
    }
 
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

  getRoles(){
    this.restProvider.get(GET_ROLES, this.access_token).then((data) =>{
      console.log(data);
      this.roles = data;
    }, err => {

    });
  }

  getRole(roleName){
    let role;
    this.roles.forEach(element => {
      if(element.name === roleName)
        role = element;
    });
    return role;
  }

  save(){
    let roles = [];
    if(this.valueIsAdmin){ roles.push(this.getRole("ROLE_ADMIN")) }
    if(this.valueIsUser){ roles.push(this.getRole("ROLE_USER")) }
    let user = {
      name: this.valueName,
      password: this.valuePassword,
      login: this.valueUsername,
      roles : roles
    }
    this.restProvider.post(SAVE_USER, this.access_token, user).then((data) => {
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
