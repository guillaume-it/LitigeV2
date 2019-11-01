import { Component, OnInit , Inject} from '@angular/core';
import {Router} from "@angular/router";
import {Users} from "../model/users.model";
import {UserService} from "../service/user.service";
import { LitigeService } from '../service/litige.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  users: any;

  constructor(private router: Router, private userService: UserService, private litigeService: LitigeService) { }

  ngOnInit() {
    if(!window.sessionStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.userService.getUsers()
      .subscribe( data => {
        console.log(data)
          this.users = data;
      });

    this.litigeService.getLitigeById(1).subscribe(data => {
      console.log('Litige: ',data);
    });
  }

  deleteUser(user: Users): void {
    this.userService.deleteUser(user.id)
      .subscribe( data => {
        debugger
        this.users = this.users.filter(u => u !== user);
      })
  };

  editUser(user: Users): void {
    window.sessionStorage.removeItem("editUserId");
    window.sessionStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['edit-user']);
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };
}
