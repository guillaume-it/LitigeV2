import { Component, OnInit, ViewChild } from '@angular/core';
import { LitigeService } from 'src/app/services/litige.service';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { Litige } from 'src/app/models';

@Component({
  selector: 'app-litige-list',
  templateUrl: './litige-list.component.html',
  styleUrls: ['./litige-list.component.scss']
})
export class LitigeListComponent implements OnInit {
  displayedColumns: string[];

  current: Litige;
  dataSource: MatTableDataSource<Litige>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  constructor(private litigeServive: LitigeService) {}

  ngOnInit() {
    this.displayedColumns = ['id', 'creation', 'objet', 'localite', 'requerant'];
    this.litigeServive.loadPages().subscribe(page => {
      this.dataSource = new MatTableDataSource<Litige>(page.content);
      this.dataSource.paginator = this.paginator;
    });
  }

  create() {}
}
