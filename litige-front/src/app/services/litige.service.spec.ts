import { LitigeService } from './litige.service';
import { TestBed } from '@angular/core/testing';

describe('LitigeServive', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LitigeService = TestBed.get(LitigeService);
    expect(service).toBeTruthy();
  });
});
