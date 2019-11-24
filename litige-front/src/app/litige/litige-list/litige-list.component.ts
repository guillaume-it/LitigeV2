import { Page } from '../../models/page';
import { Component, OnInit } from '@angular/core';
import { LitigeServive } from 'src/app/services/litige.service';
import { Litige } from 'src/app/models/litige';

@Component({
  selector: 'app-litige-list',
  templateUrl: './litige-list.component.html',
  styleUrls: ['./litige-list.component.scss']
})
export class LitigeListComponent implements OnInit {
  litiges: Page<Litige>;

  constructor(private litigeServive: LitigeServive) {}

  ngOnInit() {
    this.litigeServive.loadPages().subscribe(litige => {
      this.litiges = litige;
    });
  }
}
