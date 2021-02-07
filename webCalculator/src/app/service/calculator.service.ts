import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RequestOperation} from '../model/RequestOperation';
import {Observable} from 'rxjs';
import {ResponseOperation} from '../model/ResponseOperation';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CalculatorService {

  constructor(private http: HttpClient) {
  }

  executeOperation(request: RequestOperation): Observable<ResponseOperation> {
    return this.http.post<ResponseOperation>(environment.host + '/calculator', request);
  }

}
