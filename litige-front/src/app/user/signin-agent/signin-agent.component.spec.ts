import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SigninAgentComponent } from './signin-agent.component';

describe('SigninAgentComponent', () => {
  let component: SigninAgentComponent;
  let fixture: ComponentFixture<SigninAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SigninAgentComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SigninAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
