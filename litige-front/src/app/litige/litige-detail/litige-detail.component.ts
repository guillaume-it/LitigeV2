import { Component, OnInit } from '@angular/core';
import { Litige } from 'src/app/models/litige';
import { LitigeService } from 'src/app/services/litige.service';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-litige-detail',
  templateUrl: './litige-detail.component.html',
  styleUrls: ['./litige-detail.component.scss']
})
export class LitigeDetailComponent implements OnInit {
  constructor(private litigeServive: LitigeService, private route: ActivatedRoute) {}
  litige: Litige;
  claimForm: FormGroup;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.litigeServive.getLitige(this.route.snapshot.paramMap.get('id')).subscribe(litige => {
        this.litige = litige;
        this.claimForm = new FormGroup({
          object: new FormControl(litige.objet, Validators.required),
          message: new FormControl(litige.message, Validators.required)
        });
      });
    });
  }
}
