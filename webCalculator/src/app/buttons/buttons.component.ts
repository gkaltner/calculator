import {Component, OnInit} from '@angular/core';
import {faDivide, faEquals, faMinus, faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';
import {DisplayService} from '../service/display.service';
import {Action} from '../model/Action';
import {CalculatorService} from '../service/calculator.service';
import {RequestOperation} from '../model/RequestOperation';
import {ResponseOperation} from '../model/ResponseOperation';
import {HistoryService} from '../service/history.service';

@Component({
  selector: 'app-buttons',
  templateUrl: './buttons.component.html',
  styleUrls: ['./buttons.component.scss']
})
export class ButtonsComponent implements OnInit {
  faPlus = faPlus;
  faEquals = faEquals;
  faMinus = faMinus;
  faTimes = faTimes;
  faDivide = faDivide;

  currentValue = '';
  operator1: number = null;
  operator2: number = null;
  action: Action = null;
  disabled = false;

  constructor(private displayService: DisplayService,
              private calculatorService: CalculatorService,
              private historyService: HistoryService) {
  }

  ngOnInit(): void {
  }

  addButtonValue(value: string): void {
    const action = Action[value];
    if (!action) {
      this.currentValue += value;
      this.displayService.displayValue(this.currentValue);
    } else {
      if (this.operator1 === null && this.currentValue.length > 0) {
        this.operator1 = parseInt(this.currentValue, 10);
        this.action = action;
        this.displayService.displayValue(value);
        this.currentValue = '';
      }
    }
  }

  clear(): void {
    this.currentValue = '';
    this.operator1 = null;
    this.operator2 = null;
    this.action = null;
    this.disabled = false;
    this.displayService.displayValue(this.currentValue);
  }

  submit(): void {
    if (this.operator1 != null && this.action != null && this.currentValue.length > 0) {
      this.operator2 = parseInt(this.currentValue, 10);
      const requestOperation: RequestOperation = {
        action: this.action,
        operator1: this.operator1,
        operator2: this.operator2
      };
      this.disabled = true;
      this.calculatorService.executeOperation(requestOperation).subscribe((response: ResponseOperation) => {
        this.clear();
        this.displayService.displayValue(response.result.toString());
        this.historyService.refreshHistory();
      });
    }
  }
}
