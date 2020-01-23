import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-identification',
  templateUrl: './identification.component.html',
  styleUrls: ['./identification.component.scss']
})
export class IdentificationComponent implements OnInit {
  @Input() formGroup: FormGroup;
  private formGroupName = 'identificationFormGroup';

  constructor() {}

  ngOnInit() {
    this.formGroup.addControl(
      this.formGroupName,
      new FormGroup({
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        phone: new FormControl('', Validators.required),
        email: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
      })
    );
  }

  get identificationFormGroup(): FormGroup {
    return this.formGroup.get(this.formGroupName) as FormGroup;
  }
}
