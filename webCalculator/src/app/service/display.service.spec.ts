import {TestBed} from '@angular/core/testing';

import {DisplayService} from './display.service';
import {DisplayClear, DisplaySubmit, DisplayValue} from '../model/DisplayEvent';

describe('DisplayService', () => {
  let service: DisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('send submit event', () => {
    const event = new DisplaySubmit();
    service.displayEvent(event);
    service.listenEvents().subscribe(eventReceived => {
      expect(eventReceived).toBe(event);
    });
  });

  it('send clear event', () => {
    const event = new DisplayClear();
    service.displayEvent(event);
    service.listenEvents().subscribe(eventReceived => {
      expect(eventReceived).toBe(event);
    });
  });

  it('send value event', () => {
    const event = new DisplayValue('3');
    service.displayEvent(event);
    service.listenEvents().subscribe(eventReceived => {
      expect(eventReceived).toBe(event);
    });
  });
});
