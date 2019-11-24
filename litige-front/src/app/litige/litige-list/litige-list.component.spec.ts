/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { LitigeListComponent } from './litige-list.component';

describe('LitigeListComponent', () => {
  let component: LitigeListComponent;
  let fixture: ComponentFixture<LitigeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LitigeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LitigeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
