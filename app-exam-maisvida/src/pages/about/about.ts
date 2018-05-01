import { Component } from '@angular/core';

@Component({
  selector: 'page-about',
  templateUrl: 'about.html'
})
export class AboutPage {

  isUser = true;
  isAdmin = true;

  insert = "doctor";

  constructor() {
  }
  
  ionViewWillEnter(){
    this.isAdmin = this.getRole("ROLE_ADMIN");
    this.isUser = this.getRole("ROLE_USER"); 
    this.insert = this.isAdmin && !this.isUser ? "user" : "doctor";
  }

  getRole(roleName): boolean{
    let user = JSON.parse(localStorage.getItem('user'));
    let role = false;
    user.roles.forEach(element => {
      if(element.name === roleName)
        role = true
    });
    return role;
  }
}
