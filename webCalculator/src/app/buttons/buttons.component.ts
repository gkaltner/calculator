import {Component, OnDestroy, OnInit} from '@angular/core';
import {faDivide, faEquals, faMinus, faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';
import {DisplayService} from '../service/display.service';
import {DisplayClear, DisplaySubmit, DisplayValue} from '../model/DisplayEvent';
import {Subscription} from 'rxjs';
import {ButtonsService} from '../service/buttons.service';

@Component({
  selector: 'app-buttons',
  templateUrl: './buttons.component.html',
  styleUrls: ['./buttons.component.scss']
})
export class ButtonsComponent implements OnInit, OnDestroy {
  private buttonsSubs: Subscription = null;
  faPlus = faPlus;
  faEquals = faEquals;
  faMinus = faMinus;
  faTimes = faTimes;
  faDivide = faDivide;
  disabled = false;

  constructor(private displayService: DisplayService,
              private buttonsService: ButtonsService) {
  }

  ngOnInit(): void {
    this.buttonsSubs = this.buttonsService.listenEnableButtons().subscribe(enable => {
      this.disabled = !enable;
    });
  }

  addButtonValue(value: string): void {
    this.displayService.displayEvent(new DisplayValue(value));
  }

  clear(): void {
    this.displayService.displayEvent(new DisplayClear());
  }

  submit(): void {
    this.disabled = true;
    this.displayService.displayEvent(new DisplaySubmit());
  }

  ngOnDestroy(): void {
    this.buttonsSubs.unsubscribe();
  }
}
