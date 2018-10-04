import { Injectable } from '@angular/core';
import * as firebase from 'firebase';
import {UserService} from '../services/user.service';
import {User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  // TODO liaison avec Spring Security et Service Auth from PostPlatform BAckend

  constructor(private serviceUser: UserService) { }

  private apiUrl: String = 'http://localhost:9001';


  createNewUser(user: User) {

    return new Promise(
      (resolve, reject) => {
        firebase.auth().createUserWithEmailAndPassword( user.email , user.mdp).then(
          () => {
            resolve();
          },
          (error) => {
            reject(error);
          }
        );
      }
    );
  }

  signInUser(email: string, password: string) {
    return new Promise(
      (resolve, reject) => {
        firebase.auth().signInWithEmailAndPassword(email, password).then(
          () => {
            resolve();
          },
          (error) => {
            reject(error);
          }
        );
      }
    );
  }

  signOutUser() {
    firebase.auth().signOut();
  }
}
