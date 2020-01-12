import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { SigninClaimantComponent } from './signin-claimant.component';

describe('SignInClaimantComponent', () => {
  let component: SigninClaimantComponent;
  let fixture: ComponentFixture<SigninClaimantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SigninClaimantComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SigninClaimantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
