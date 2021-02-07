import {Component, OnDestroy, OnInit} from '@angular/core';
import {DisplayService} from '../service/display.service';
import {Subscription} from 'rxjs';
import {Action} from '../model/Action';
import {faDivide, faEquals, faMinus, faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.scss']
})
export class DisplayComponent implements OnInit, OnDestroy {
  faPlus = faPlus;
  faEquals = faEquals;
  faMinus = faMinus;
  faTimes = faTimes;
  faDivide = faDivide;

  private displaySubs: Subscription = null;
  currentValue: string;
  action: Action;

  constructor(private displayService: DisplayService) {
  }

  ngOnInit(): void {
    this.displaySubs = this.displayService.displayObs.subscribe(value => {
      this.action = Action[value];
      if (this.action) {

      } else {
        this.currentValue = value;
      }
    });
  }

  ngOnDestroy(): void {
    this.displaySubs.unsubscribe();
  }

}
