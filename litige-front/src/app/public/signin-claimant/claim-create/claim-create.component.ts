import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-claim-create',
  templateUrl: './claim-create.component.html',
  styleUrls: ['./claim-create.component.scss']
})
export class ClaimCreateComponent implements OnInit {
  @Input() formGroup: FormGroup;
  private formGroupName = 'claimCreateFormGroup';

  constructor() {}

  ngOnInit() {
    this.formGroup.addControl(
      this.formGroupName,
      new FormGroup({
        object: new FormControl('', Validators.required),
        message: new FormControl('')
      })
    );
  }

  get claimCreateFormGroup(): FormGroup {
    return this.formGroup.get(this.formGroupName) as FormGroup;
  }
}
