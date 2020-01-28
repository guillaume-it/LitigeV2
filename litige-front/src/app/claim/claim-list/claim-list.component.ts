import { Component, OnInit, ViewChild } from '@angular/core';
import { Litige } from 'src/app/models';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { LitigeService } from 'src/app/services';

@Component({
  selector: 'app-claim-list',
  templateUrl: './claim-list.component.html',
  styleUrls: ['./claim-list.component.scss']
})
export class ClaimListComponent implements OnInit {
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
