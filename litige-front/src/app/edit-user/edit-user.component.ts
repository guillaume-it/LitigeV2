import { Component, OnInit , Inject} from '@angular/core';
import {Router} from "@angular/router";
import {Users} from "../model/users.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: Users;
  editForm: FormGroup;
  constructor(private formBuilder: FormBuilder,private router: Router, private userService: UserService) { }

  ngOnInit() {
    let userId = window.sessionStorage.getItem("editUserId");
    if(!userId) {
      alert("Invalid action.")
      this.router.navigate(['list-user']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: [''],
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      age: ['', Validators.required],
      salary: ['', Validators.required]
    });
    this.userService.getUserById(+userId)
      .subscribe( data => {
        this.editForm.setValue(data);
      });
  }

  onSubmit() {
    this.userService.updateUser(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
            alert('User updated successfully.');
            this.router.navigate(['list-user']);
        },
        error => {
          alert(error);
        });
  }

}
