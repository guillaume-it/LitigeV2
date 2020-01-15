import { Component, OnInit } from '@angular/core';
import { Litige } from 'src/app/models/litige';
import { LitigeServive } from 'src/app/services/litige-service';

@Component({
  selector: 'app-litige-detail',
  templateUrl: './litige-detail.component.html',
  styleUrls: ['./litige-detail.component.scss']
})
export class LitigeDetailComponent implements OnInit {
  constructor(private litigeServive: LitigeServive) {}
  litige: Litige;
  ngOnInit() {
    this.litigeServive.getLitige().subscribe(litige => {
      this.litige = litige;
    });
  }
}
