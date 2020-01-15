import { LitigeServive } from './litige-service';
import { TestBed } from '@angular/core/testing';

describe('LitigeServive', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LitigeServive = TestBed.get(LitigeServive);
    expect(service).toBeTruthy();
  });
});
