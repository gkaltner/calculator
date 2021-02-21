import {Component, OnDestroy, OnInit} from '@angular/core';
import {DisplayService} from '../service/display.service';
import {Subscription} from 'rxjs';
import {Action} from '../model/Action';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ResponseOperation} from '../model/ResponseOperation';
import {CalculatorService} from '../service/calculator.service';
import {RequestOperation} from '../model/RequestOperation';
import {HistoryService} from '../service/history.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {DisplayClear, DisplayEvent, DisplaySubmit, DisplayValue} from '../model/DisplayEvent';
import {finalize} from 'rxjs/operators';
import {ButtonsService} from '../service/buttons.service';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.scss']
})
export class DisplayComponent implements OnInit, OnDestroy {
  readonly pattern: RegExp = /^(\d+)([+|\-*/])(\d+)$/;
  private displaySubs: Subscription = null;

  displayForm = new FormGroup({
    expression: new FormControl('', [Validators.required, Validators.pattern(this.pattern)])
  });

  action: Action;

  constructor(private displayService: DisplayService,
              private calculatorService: CalculatorService,
              private historyService: HistoryService,
              private snackBar: MatSnackBar,
              private buttonsService: ButtonsService) {
  }

  ngOnInit(): void {
    this.displaySubs = this.displayService.listenEvents().subscribe((event: DisplayEvent) => {
      if (event instanceof DisplayValue) {
        const value = this.displayForm.value.expression + event.value;
        this.displayForm.controls.expression.setValue(value);
      }

      if (event instanceof DisplayClear) {
        this.clear();
      }

      if (event instanceof DisplaySubmit) {
        this.onSubmit();
      }
    });
  }

  onSubmit(): void {
    if (this.displayForm.valid) {

      const values = this.displayForm.value.expression.match(this.pattern);
      console.log(values);
      const requestOperation: RequestOperation = {
        action: Action.parse(values[2]),
        operator1: values[1],
        operator2: values[3]
      };

      this.calculatorService.executeOperation(requestOperation)
        .pipe(finalize(() => {
          this.buttonsService.enableButtons(true);
          this.historyService.refreshHistory();
        }))
        .subscribe((response: ResponseOperation) => {
            this.displayForm.controls.expression.setValue(response.result.toString());
          }, (error) => {
            this.clear();
            this.showError(error.error);
          }
        );
    } else {
      this.showError();
      this.clear();
      this.buttonsService.enableButtons(true);
    }
  }


  ngOnDestroy(): void {
    this.displaySubs.unsubscribe();
  }

  private clear(): void {
    this.displayForm.controls.expression.setValue('');
  }

  private showError(message?: string): void {
    this.snackBar.open(message ? message : 'Operation not allowed', null, {
      duration: 2000,
    });
  }
}
