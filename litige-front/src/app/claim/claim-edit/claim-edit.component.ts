import { LoadingState } from './../../models/state';
import { Component, OnInit, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, UrlSegment } from '@angular/router';
import { LitigeService } from 'src/app/services';
import { Litige } from 'src/app/models/litige';
import { ScreenState, State } from 'src/app/models/state';
import { FileInformation } from 'src/app/models/file-information';

@Component({
  selector: 'app-claim-edit',
  templateUrl: './claim-edit.component.html',
  styleUrls: ['./claim-edit.component.scss']
})
export class ClaimEditComponent implements OnInit {
  screenState = ScreenState;
  state = new State();
  loading = false;
  claim: Litige;
  formGroup: FormGroup;
  filesStatus = new Map<string, FileInformation>();
  stateEvent = new EventEmitter<State>();

  constructor(private router: Router, private litigeService: LitigeService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const urlSegment = this.route.snapshot.url.find(
        (url: UrlSegment) =>
          url.path === ScreenState.DETAIL || url.path === ScreenState.CREATE || url.path === ScreenState.EDIT
      );
      this.manageState(urlSegment.path as ScreenState);

      if (params.get('id')) {
        this.litigeService.getLitige(params.get('id')).subscribe(claim => {
          this.claim = claim;
          this.initFromGroup(this.claim);
          this.claim.fileInformations.forEach(fileInformation => {
            fileInformation.loaded = true;
            this.filesStatus.set(fileInformation.name, fileInformation);
          });
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

  manageStateLoading(loadingState: LoadingState) {
    this.state.loadingState = loadingState;
    this.stateEvent.next(this.state);
  }
  manageState(screenState: ScreenState, loadingState?: LoadingState) {
    this.state.screenState = screenState;

    if (loadingState) {
      this.state.loadingState = loadingState;
    }
    this.stateEvent.next(this.state);
  }
  ngCancel() {
    this.initFromGroup(this.claim);
    this.manageState(ScreenState.DETAIL);
  }
  ngSubmit() {
    // TODO

    this.claim.objet = this.formGroup.get('object').value;
    this.claim.message = this.formGroup.get('message').value;
    this.loading = true;
    if (this.state.screenState === ScreenState.CREATE) {
      this.manageStateLoading(LoadingState.LOADING);
      this.litigeService.create(this.claim).subscribe(
        (res: Litige) => {
          this.loading = false;
          if (this.filesStatus && this.filesStatus.size > 0) {
            this.litigeService.upload(this.filesStatus, res.id);
          } else {
            this.router.navigate(['/', 'claim', 'list']);
          }
        },
        err => {
          this.loading = false;
        }
      );
    } else if (this.state.screenState === ScreenState.EDIT) {
      this.manageStateLoading(LoadingState.LOADING);
      this.litigeService.update(this.claim).subscribe(
        res => {
          this.manageState(ScreenState.DETAIL);
          this.loading = false;

          //
          // TODO notification
        },
        err => {
          this.loading = false;
        }
      );
    }
  }
  selectFiles(filesStatus: Map<string, FileInformation>) {
    this.filesStatus = filesStatus;
  }
  deleteFile(fileStatus: FileInformation) {
    if (fileStatus.loaded) {
      //this.
    }
  }
  editing() {
    this.state.screenState = this.screenState.EDIT;
    this.manageState(this.state.screenState);
  }

  downloadFile(fileInformation: FileInformation) {
    this.litigeService.download(fileInformation, this.claim.id);
  }
}
