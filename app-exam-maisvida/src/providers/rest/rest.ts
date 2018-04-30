import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StringBuilder } from 'typescript-string-operations';


/*
  Generated class for the RestProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class RestProvider {

  private apiUrl = 'http://localhost:8080';
  private oauth2Url = '/oauth/token';
  private credentials = {
    client_id: "clientapp",
    client_secret: "123456"
  }

  constructor(public http: HttpClient) {
    console.log('Initializing.. RestServiceProvider Provider');
  }

  public authenticate(username:string, password:string, grantType:string, scope:string) {
    return new Promise((resolve, reject) => {
      if(!this.isValid(username, password, grantType, scope))
        reject();

      this.buildOAuth2Url(password, username, grantType, scope);
      this.getToken().then((data: any) => {
        resolve(data.access_token);
      }, err => {
        reject(err);
      })
    });
  }

  private isValid(...params: string[]): boolean{
    for(let param of params){
      if(param == undefined || param.trim().length == 0)
        return false;
    }

    return true;
  }

  private buildOAuth2Url(password, username, grantType, scope) {
    let stringBuilder = new StringBuilder();
    //stringBuilder.Append(this.oauth2Url+"?");
    stringBuilder.Append("password="+password);
    stringBuilder.Append("&username="+username);
    stringBuilder.Append("&grant_type="+grantType);
    stringBuilder.Append("&scope=read%20write");
    this.oauth2Url = stringBuilder.ToString();
  }

  private getToken() {
    return new Promise((resolve, reject) => {
      let headers = new HttpHeaders({
        "Content-Type": "application/x-www-form-urlencoded; charset=utf-8",
        "Authorization": "Basic " + btoa(this.credentials.client_id + ':' + this.credentials.client_secret),
      });

      this.http.post(`${this.apiUrl}/oauth/token`, this.oauth2Url, {headers: headers})
        .subscribe((data: any) => {
          resolve(data);
        }, err => {
          reject(err.error);
        });
    });
  }

  getDoctors(access_token) {
    return new Promise((resolve, reject) => {
      if (access_token == undefined)
        return reject("invalid token!");

      this.http.get(this.apiUrl + '/doctor/get/all' + '?access_token=' + access_token)
        .subscribe(data => {
          resolve(data);
        }, err => {
          console.log(err);
          reject(err);
        });
    });
  }

  getSpecialties(access_token) {
    return new Promise((resolve, reject) => {
      if (access_token == undefined)
        return reject("invalid token!");

      this.http.get(this.apiUrl + '/specialty/get/all' + '?access_token=' + access_token)
        .subscribe(data => {
          resolve(data);
        }, err => {
          console.log(err);
          reject(err);
        });
    });
  }

  getRegions(access_token) {
    return new Promise((resolve, reject) => {
      if (access_token == undefined)
        return reject("invalid token!");

      this.http.get(this.apiUrl + '/region/get/all' + '?access_token=' + access_token)
        .subscribe(data => {
          resolve(data);
        }, err => {
          console.log(err);
          reject(err);
        });
    });
  }

  deleteDoctor(id, access_token){
    return new Promise((resolve, reject) => {
      if(!this.isValid(id, access_token))
        return reject("invalid datas!");
      
      this.http.delete(`${this.apiUrl}/doctor/delete?id=${id}&access_token=${access_token}`)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  saveDoctor(doctor, access_token){
    return new Promise((resolve, reject) => {
      if(!this.isValid(access_token))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}/doctor/save?access_token=${access_token}`, doctor)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  saveUser(user, access_token){
    return new Promise((resolve, reject) => {
      if(!this.isValid(access_token))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}/user/save?access_token=${access_token}`, user)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  saveRegion(region, access_token){
    return new Promise((resolve, reject) => {
      if(!this.isValid(access_token))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}/region/save?access_token=${access_token}`, region)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  saveSpecialty(region, access_token){
    return new Promise((resolve, reject) => {
      if(!this.isValid(access_token))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}/specialty/save?access_token=${access_token}`, region)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  authenticateUser(user) {
    return new Promise((resolve, reject) => {
      if(!this.isValid(user.login, user.password))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}/login/authenticate`, user)
        .subscribe((data: any) => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

}
