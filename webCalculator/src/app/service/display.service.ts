import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DisplayService {

  private displaySource = new Subject<string>();
  public displayObs = this.displaySource.asObservable();

  constructor() {
  }

  public displayValue(num: string): void {
    this.displaySource.next(num);
  }
}
