import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DisplayComponent} from './display.component';
import {CalculatorService} from '../service/calculator.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {ResponseOperation} from '../model/ResponseOperation';
import {of, throwError} from 'rxjs';
import {ButtonsService} from '../service/buttons.service';
import {HistoryService} from '../service/history.service';
import {HttpErrorResponse, HttpEventType} from '@angular/common/http';
import {DisplayService} from '../service/display.service';
import {DisplayClear, DisplayEvent, DisplaySubmit, DisplayValue} from '../model/DisplayEvent';

describe('DisplayComponent', () => {
  let component: DisplayComponent;
  let fixture: ComponentFixture<DisplayComponent>;
  const mockCalculatorService = jasmine.createSpyObj('CalculatorService', ['executeOperation']);
  const mockButtonsService = jasmine.createSpyObj('ButtonsService', ['enableButtons']);
  const mockHistoryService = jasmine.createSpyObj('HistoryService', ['refreshHistory']);
  const mockSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);
  let displayService: DisplayService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatSnackBarModule],
      declarations: [DisplayComponent],
      providers: [{
        provide: CalculatorService,
        useValue: mockCalculatorService
      },
        {
          provide: ButtonsService,
          useValue: mockButtonsService
        },
        {
          provide: HistoryService,
          useValue: mockHistoryService
        },
        {
          provide: MatSnackBar,
          useValue: mockSnackBar
        },
        DisplayService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    displayService = TestBed.inject(DisplayService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when display is empty', () => {
    expect(component.displayForm.valid).toBeFalsy();
  });

  it('display invalid when it has invalid operations', () => {
    const expression = component.displayForm.controls.expression;
    expect(expression.valid).toBeFalsy();

    expression.setValue('');
    expect(expression.hasError('required')).toBeTruthy();

    expression.setValue('a');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('7');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('*');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('**8');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('9*');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('6*/');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('%&%/&%');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('2+3+6');
    expect(expression.hasError('pattern')).toBeTruthy();

    expression.setValue('2*9*6*8*6');
    expect(expression.hasError('pattern')).toBeTruthy();

    expect(component.displayForm.valid).toBeFalsy();
  });

  it('form valid when display has the correct expression', () => {
    const expression = component.displayForm.controls.expression;

    expression.setValue('7+6');
    expect(expression.valid).toBeTruthy();

    expression.setValue('5-4');
    expect(expression.valid).toBeTruthy();

    expression.setValue('2*2');
    expect(expression.valid).toBeTruthy();

    expression.setValue('4/2');
    expect(expression.valid).toBeTruthy();

    expect(component.displayForm.valid).toBeTruthy();
  });

  it('submit a valid expression', () => {
    const expression = component.displayForm.controls.expression;
    expression.setValue('7+6');

    const expectValue: ResponseOperation = {
      result: 13
    };

    mockCalculatorService.executeOperation.and.returnValue(of(expectValue));

    component.onSubmit();

    expect(mockButtonsService.enableButtons).toHaveBeenCalled();
    expect(mockHistoryService.refreshHistory).toHaveBeenCalled();
    expect(mockCalculatorService.executeOperation).toHaveBeenCalled();
    expect(expression.value).toBe('13');
  });

  it('submit a invalid expression', () => {
    const expression = component.displayForm.controls.expression;
    expression.setValue('0/0');

    const expectValue: HttpErrorResponse = {
      error: 'Operation not valid',
      message: '',
      url: '',
      statusText: '',
      status: 400,
      headers: null,
      ok: false,
      type: HttpEventType.Response,
      name: 'HttpErrorResponse'
    };

    mockCalculatorService.executeOperation.and.returnValue(throwError(expectValue));

    component.onSubmit();

    expect(mockButtonsService.enableButtons).toHaveBeenCalled();
    expect(mockHistoryService.refreshHistory).toHaveBeenCalled();
    expect(mockCalculatorService.executeOperation).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalled();
    expect(expression.value).toBe('');
  });

  it('submit when form is invalid', () => {
    const expression = component.displayForm.controls.expression;
    expression.setValue('');

    component.onSubmit();

    expect(mockButtonsService.enableButtons).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalled();
    expect(expression.value).toBe('');
  });

  it('received a display clear events', () => {
    const expression = component.displayForm.controls.expression;
    expression.setValue('345');

    const event: DisplayEvent = new DisplayClear();
    displayService.displayEvent(event);

    expect(expression.value).toBe('');
  });

  it('received a display submit events', () => {
    spyOn(component, 'onSubmit');

    const event: DisplayEvent = new DisplaySubmit();
    displayService.displayEvent(event);

    expect(component.onSubmit).toHaveBeenCalled();
  });

  it('received a display value events', () => {
    const expression = component.displayForm.controls.expression;
    expression.setValue('');

    const event: DisplayEvent = new DisplayValue('3');
    displayService.displayEvent(event);

    expect(expression.value).toBe('3');
  });
});
