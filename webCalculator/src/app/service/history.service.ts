import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {History} from '../model/History';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private historySource = new Subject<string>();
  private historyObs = this.historySource.asObservable();

  constructor(private http: HttpClient) {
  }

  fetchHistory(): Observable<Array<History>> {
    return this.http.get<Array<History>>(environment.host + '/history');
  }

  public refreshHistory(): void {
    this.historySource.next();
  }

  public listenHistoryEvents(): Observable<string> {
    return this.historyObs;
  }
}
