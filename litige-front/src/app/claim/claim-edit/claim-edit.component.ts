import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, NavigationEnd, UrlSegment } from '@angular/router';
import { LitigeService } from 'src/app/services';
import { Litige } from 'src/app/models/litige';
import { ScreenState } from 'src/app/models/screen-state';

@Component({
  selector: 'app-claim-edit',
  templateUrl: './claim-edit.component.html',
  styleUrls: ['./claim-edit.component.scss']
})
export class ClaimEditComponent implements OnInit {
  screenState = ScreenState;
  state: ScreenState;
  loading = false;
  claim: Litige;
  formGroup: FormGroup;

  constructor(private router: Router, private litigeService: LitigeService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const urlSegment = this.route.snapshot.url.find(
        (url: UrlSegment) =>
          url.path === ScreenState.DETAIL || url.path === ScreenState.CREATE || url.path === ScreenState.EDIT
      );
      this.state = urlSegment.path as ScreenState;

      if (params.get('id')) {
        this.litigeService.getLitige(params.get('id')).subscribe(claim => {
          this.claim = claim;
          this.initFromGroup(this.claim);
        });
      } else {
        this.claim = new Litige();
        this.initFromGroup(this.claim);
      }
    });
  }
  initFromGroup(claim: Litige) {
    this.formGroup = new FormGroup({
      object: new FormControl(claim.objet, Validators.required),
      message: new FormControl(claim.message, Validators.required)
    });
  }
  ngCancel() {
    this.initFromGroup(this.claim);
    this.state = ScreenState.DETAIL;
  }
  ngSubmit() {
    this.claim.objet = this.formGroup.get('object').value;
    this.claim.message = this.formGroup.get('message').value;
    this.loading = true;
    if (this.state === ScreenState.CREATE) {
      this.litigeService.create(this.claim).subscribe(
        res => {
          this.loading = false;
          this.router.navigate(['/claim', 'list']);
        },
        err => {
          this.loading = false;
        }
      );
    } else if (this.state === ScreenState.EDIT) {
      this.litigeService.update(this.claim).subscribe(
        res => {
          this.state = ScreenState.DETAIL;
          this.loading = false;
          // TODO notification
        },
        err => {
          this.loading = false;
        }
      );
    }
  }
}
