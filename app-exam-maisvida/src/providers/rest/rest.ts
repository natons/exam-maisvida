import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StringBuilder } from 'typescript-string-operations';


export const GET_DOCTORS: string = "/doctor/get/all?access_token=";
export const GET_SPECIALTIES: string = "/specialty/get/all?access_token=";
export const GET_REGIONS: string = "/region/get/all?access_token=";
export const GET_ROLES: string = "/role/get/all?access_token=";
export const SAVE_DOCTOR: string = "/doctor/save?access_token=";
export const SAVE_USER: string = "/user/save?access_token=";
export const SAVE_REGION: string = "/region/save?access_token=";
export const SAVE_SPECIALTY: string = "/specialty/save?access_token=";
export const DELETE_DOCTOR: string = "/doctor/delete?access_token=";
export const AUTHENTICATE_USER: string = "/login/authenticate";


@Injectable()
export class RestProvider {

  private apiUrl = 'http://localhost:8080';
  private oauth2Url = '/oauth/token';
  private credentials = {
    client_id: "clientapp",
    client_secret: "123456"
  }

  constructor(public http: HttpClient) {
  }

  private isValid(...params: string[]): boolean{
    for(let param of params){
      if(param === undefined || param.trim().length === 0)
        return false;
    }

    return true;
  }

  public authenticate(username:string, password:string, grantType:string, scope:string) {
    return new Promise((resolve, reject) => {
      if(!this.isValid(username, password, grantType, scope))
        reject("invalida datas");

      let user = this.getParametersToToken(password, username, grantType, scope);
      this.getToken(user).then((data: any) => {
        resolve(data.access_token);
      }, err => {
        reject(err);
      })
    });
  }

  private getParametersToToken(password, username, grantType, scope) {
    let stringBuilder = new StringBuilder();
    stringBuilder.Append("password="+password);
    stringBuilder.Append("&username="+username);
    stringBuilder.Append("&grant_type="+grantType);
    stringBuilder.Append("&scope=read%20write");
    return stringBuilder.ToString();
  }

  private getToken(user) {
    return new Promise((resolve, reject) => {
      let headers = new HttpHeaders({
        "Content-Type": "application/x-www-form-urlencoded; charset=utf-8",
        "Authorization": "Basic " + btoa(this.credentials.client_id + ':' + this.credentials.client_secret),
      });

      this.http.post(`${this.apiUrl}${this.oauth2Url}`, user, {headers: headers})
        .subscribe((data: any) => {
          resolve(data);
        }, err => {
          reject(err.error);
        });
    });
  }

  get(path, access_token) {
    return new Promise((resolve, reject) => {

      this.http.get(this.apiUrl + path + access_token)
        .subscribe(data => {
          resolve(data);
        }, err => {
          reject(err);
        });
    });
  }

  post(path, access_token, data){
    return new Promise((resolve, reject) => {
      
      this.http.post(`${this.apiUrl}${path}${access_token}`, data)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  postNoToken(path, data){
    return new Promise((resolve, reject) => {
      if(!this.isValid(path))
        return reject("invalid datas!");
      
      this.http.post(`${this.apiUrl}${path}`, data)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }

  delete(path, access_token, id){
    return new Promise((resolve, reject) => {
      if(!this.isValid(path, access_token))
        return reject("invalid datas!");
      
      this.http.delete(`${this.apiUrl}${path}${access_token}&id=${id}`)
        .subscribe(data => {
            resolve(data);
        }, err => {
            reject(err);
        });
    });
  }
}
