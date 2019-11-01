import { TestBed } from '@angular/core/testing';

import { LitigeService } from './litige.service';

describe('LitigeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LitigeService = TestBed.get(LitigeService);
    expect(service).toBeTruthy();
  });
});
