import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LitigeService, UserService } from 'src/app/services';
import { Litige } from 'src/app/models/litige';

@Component({
  selector: 'app-claim-create',
  templateUrl: './claim-create.component.html',
  styleUrls: ['./claim-create.component.scss']
})
export class ClaimCreateComponent implements OnInit {
  formGroup: FormGroup;

  constructor(private router: Router, private litigeService: LitigeService, private userService: UserService) {}

  ngOnInit() {
    this.formGroup = new FormGroup({
      object: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required)
    });
  }

  ngSubmit() {
    const litige = new Litige();
    litige.objet = this.formGroup.get('object').value;
    litige.message = this.formGroup.get('message').value;
    this.litigeService.create(litige).subscribe(
      res => {
        console.log(`after signin ${res}`);
      },
      err => {}
    );
  }
}
