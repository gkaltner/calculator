import {TestBed} from '@angular/core/testing';

import {CalculatorService} from './calculator.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {RequestOperation} from '../model/RequestOperation';
import {Action} from '../model/Action';
import {ResponseOperation} from '../model/ResponseOperation';
import {HttpErrorResponse} from '@angular/common/http';

describe('CalculatorService', () => {
  const HOST = 'http://localhost:8080';
  let service: CalculatorService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CalculatorService);

    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('post a new valid operation', () => {
    const requestOperation: RequestOperation = {
      action: Action.ADD,
      operator1: 1,
      operator2: 2
    };

    const expectValue: ResponseOperation = {
      result: 3
    };

    service.executeOperation(requestOperation)
      .subscribe(value => {
        expect(value).toBe(expectValue);
      });

    const req = httpTestingController.expectOne(HOST + '/calculator');
    expect(req.request.method).toEqual('POST');
    req.flush(expectValue);
  });

  it('post a new invalid operation', () => {
    const requestOperation: RequestOperation = {
      action: Action.DIVIDE,
      operator1: 0,
      operator2: 0
    };

    const expectValue: ResponseOperation = {
      result: NaN
    };

    service.executeOperation(requestOperation)
      .subscribe(value => {
      }, (error: HttpErrorResponse) => {
        expect(error.error).toBe(expectValue);
        expect(error.status).toBe(400);
        expect(error.statusText).toBe('Operation not valid');
      });

    const req = httpTestingController.expectOne(HOST + '/calculator');
    expect(req.request.method).toEqual('POST');
    req.flush(expectValue, {status: 400, statusText: 'Operation not valid'});
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
