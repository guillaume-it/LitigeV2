import { Litige } from './../../models/litige';
import { LitigeServive } from './../../services/litige.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-litige',
  templateUrl: './litige.component.html',
  styleUrls: ['./litige.component.scss']
})
export class LitigeComponent implements OnInit {
  constructor(private litigeServive: LitigeServive) {}
  litige: Litige;
  ngOnInit() {
    this.litigeServive.getLitige().subscribe(litige => {
      this.litige = litige;
    });
  }
}
