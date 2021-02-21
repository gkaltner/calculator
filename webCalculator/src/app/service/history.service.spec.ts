import {TestBed} from '@angular/core/testing';

import {HistoryService} from './history.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Action} from '../model/Action';

describe('HistoryService', () => {
  const HOST = 'http://localhost:8080';
  let service: HistoryService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(HistoryService);

    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('refresh history', () => {
    service.refreshHistory();
    service.listenHistoryEvents().subscribe(value => {
      expect(value).toBeUndefined();
    });
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('get empty list', () => {

    const expectValue = [];

    service.fetchHistory()
      .subscribe(value => {
        expect(value).toBe(expectValue);
      });

    const req = httpTestingController.expectOne(HOST + '/history');
    expect(req.request.method).toEqual('GET');
    req.flush(expectValue);
  });

  it('get list of history values', () => {

    const expectValue = [{
      id: 1,
      action: Action.ADD,
      operator1: 1,
      operator2: 2,
      result: 3,
      timeStamp: new Date()
    },
      {
        id: 2,
        action: Action.MULTIPLY,
        operator1: 2,
        operator2: 2,
        result: 4,
        timeStamp: new Date()
      }
    ];

    service.fetchHistory()
      .subscribe(value => {
        expect(value).toBe(expectValue);
      });

    const req = httpTestingController.expectOne(HOST + '/history');
    expect(req.request.method).toEqual('GET');
    req.flush(expectValue);
  });
});
