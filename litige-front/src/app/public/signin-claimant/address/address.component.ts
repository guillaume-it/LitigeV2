import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent implements OnInit {
  @Input() formGroup: FormGroup;

  private formGroupName = 'addressreateFormGroup';

  constructor() {}

  ngOnInit() {
    this.formGroup.addControl(
      this.formGroupName,
      new FormGroup({
        detail: new FormControl('', Validators.required),
        postOfficeBox: new FormControl(''),
        town: new FormControl(''),
        country: new FormControl('')
      })
    );
  }

  get addressFormGroup(): FormGroup {
    return this.formGroup.get(this.formGroupName) as FormGroup;
  }
}
