import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-claim-create',
  templateUrl: './claim-create.component.html',
  styleUrls: ['./claim-create.component.css']
})
export class ClaimCreateComponent implements OnInit {

  claimForm: FormGroup;
 
  constructor(private router: Router, private litigeService: LitigeService) {
   }

  ngOnInit() {
    this.claimForm = new FormGroup({
      object: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required)
    })
  });
  }

  signin() {
    this.litigeService
      .create(
        this.claimForm.get('object').value,
        this.claimForm.get('message').value,user.id
      )
      .subscribe(
        res => {
          console.log(`after signin ${res}`);           
        },
        err => {        }
      );
  }
}
