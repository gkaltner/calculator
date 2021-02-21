import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ButtonsService {

  private buttonsSource = new Subject<boolean>();
  private buttonsObs = this.buttonsSource.asObservable();

  constructor() {
  }

  public enableButtons(enable: boolean): void {
    this.buttonsSource.next(enable);
  }

  public listenEnableButtons(): Observable<boolean> {
    return this.buttonsObs;
  }
}
