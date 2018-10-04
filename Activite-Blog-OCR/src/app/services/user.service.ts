import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {User} from '../models/user.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError} from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: String = 'http://localhost:9001';
  users: User[] = [];

  userSubject = new Subject<User[]>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };


  constructor(private http: HttpClient) {
  }

  emitUsers() {
    this.userSubject.next(this.users);
  }

  getUsers() {
    // Ok reconstruction de la list Users
    this.users = [];
    this.http.get<any[]>(this.apiUrl + '/get-all').toPromise().then(
      data => {
        data.forEach(value => {
          let user = new User(value.email, value.pseudo);
          user.id = value.id;
          user.mdp = value.mdp;
          this.users.push(user)
        });
      }
    )
    //Pour debug
    console.log(this.users);
    this.emitUsers();
  }

  getSingleUser(id: number): User {
    let user: User = new User('', '');
    console.log(this.apiUrl + '/get-one/' + (id))
    this.http.get<User>(this.apiUrl + '/get-one/' + (id)).toPromise().then(
      data => {
        user.id = data.id;
        user.pseudo = data.pseudo;
        user.email = data.email;
        user.mdp = data.mdp;
      }
    )
    //Pour debug
    return user;
  }

  createNewUser(user: User) {
    user.id = 0;
    let objectObservable = this.http.post<User>(this.apiUrl + '/add-one', user, this.httpOptions).pipe();

    console.log(objectObservable);
    return objectObservable;

  }

}
