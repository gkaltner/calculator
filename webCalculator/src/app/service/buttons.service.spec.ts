import {TestBed} from '@angular/core/testing';

import {ButtonsService} from './buttons.service';

describe('ButtonsService', () => {
  let service: ButtonsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ButtonsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('send value event', () => {
    service.enableButtons(true);
    service.listenEnableButtons().subscribe(eventReceived => {
      expect(eventReceived).toBe(true);
    });
  });
});
