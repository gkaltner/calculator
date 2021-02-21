import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {DisplayEvent} from '../model/DisplayEvent';

@Injectable({
  providedIn: 'root'
})
export class DisplayService {

  private displaySource = new Subject<DisplayEvent>();
  private displayObs = this.displaySource.asObservable();

  constructor() {
  }

  public displayEvent(event: DisplayEvent): void {
    this.displaySource.next(event);
  }

  public listenEvents(): Observable<DisplayEvent> {
    return this.displayObs;
  }
}
