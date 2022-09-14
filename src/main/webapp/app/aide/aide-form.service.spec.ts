import { TestBed } from '@angular/core/testing';

import { AideFormService } from './aide-form.service';

describe('AideFormService', () => {
  let service: AideFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AideFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
