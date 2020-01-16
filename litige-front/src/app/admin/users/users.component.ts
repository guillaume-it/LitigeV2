import { Component, OnInit, ViewChild } from '@angular/core';
import {
  MatDialog,
  MatPaginator,
  MatSnackBar,
  MatSort,
  MatTableDataSource
} from '@angular/material';
import { StoreServiceDataSource } from 'src/app/helpers/store-service-data-source';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { formatError } from 'src/app/services/store.service';
import { UserService, UserServiceFilter } from 'src/app/services/user.service';
import { ChangePasswordComponent } from 'src/app/shared/change-password/change-password.component';
import { UserDetailComponent } from '../user-detail/user-detail.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  displayedColumns = ['email', 'role', 'actions'];

  filter: UserServiceFilter;
  current: User;
  dataSource: MatTableDataSource<User>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private authService: AuthenticationService,
    private userService: UserService
  ) {}

  ngOnInit() {
    console.log(`users initialized ${this.authService.loggedUser.email}`);
    this.current = this.authService.loggedUser;
    this.filter = new UserServiceFilter();

    this.userService.all().subscribe(page => {
      this.dataSource = new MatTableDataSource<User>(page.content);
      this.dataSource.paginator = this.paginator;
    });
  }

  private createOrEdit(i: number = null) {
    const dialogRef = this.dialog.open(UserDetailComponent, {
      disableClose: true,
      autoFocus: true,
      minWidth: '300px',
      data: {
        index: i,
        user: i === null ? new User() : this.dataSource.data[i],
        dataSource: this.dataSource,
        currentUser: this.current
      }
    });
  }

  create() {
    this.createOrEdit();
  }

  edit(i: number) {
    this.createOrEdit(i);
  }

  delete(i: number) {
    console.log(`delete ${i}`);
    this.userService.delete(i).subscribe(
      res => {
        if (this.dataSource.data[i].id === this.current.id) {
          this.authService.logout(`Deleted current user: forced logout.`);
        } else {
          this.snackBar.open(`User deleted.`);
        }
      },
      err =>
        this.snackBar.open(`User deletion failed due to ${formatError(err)}.`)
    );
  }

  applyFilter() {
    //this.dataSource.applyFilter(this.filter);
  }

  resetFilter() {
    //  this.dataSource.applyFilter(null);
  }

  changePassword(i: number) {
    const dialogRef = this.dialog.open(ChangePasswordComponent, {
      disableClose: true,
      autoFocus: true,
      minWidth: '300px',
      data: {
        user: this.dataSource.data[i],
        adminMode: true
      }
    });
  }
}
