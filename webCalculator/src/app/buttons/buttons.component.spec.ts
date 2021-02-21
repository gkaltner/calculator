import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ButtonsComponent} from './buttons.component';
import {DisplayService} from '../service/display.service';
import {DisplayClear, DisplaySubmit, DisplayValue} from '../model/DisplayEvent';
import {By} from '@angular/platform-browser';

describe('ButtonsComponent', () => {
  let component: ButtonsComponent;
  let fixture: ComponentFixture<ButtonsComponent>;
  const mockDisplayService = jasmine.createSpyObj('DisplayService', ['displayEvent']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ButtonsComponent],
      providers: [{
        provide: DisplayService,
        useValue: mockDisplayService
      }]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('add button value', () => {
    component.addButtonValue('3');
    expect(mockDisplayService.displayEvent).toHaveBeenCalledWith(new DisplayValue('3'));
  });

  it('clear', () => {
    component.clear();
    expect(mockDisplayService.displayEvent).toHaveBeenCalledWith(new DisplayClear());
  });

  it('submit', () => {
    component.submit();
    expect(mockDisplayService.displayEvent).toHaveBeenCalledWith(new DisplaySubmit());
  });


  it('click any number buttons should be trigger add button value', () => {
    spyOn(component, 'addButtonValue');
    const numberButtons = fixture.debugElement.queryAll(By.css('[color="primary"]'));
    numberButtons.forEach(button => {
      button.nativeElement.click();
      expect(component.addButtonValue).toHaveBeenCalledWith(button.nativeElement.innerHTML);
    });
  });

  it('click any operation buttons should be trigger add button value', () => {
    spyOn(component, 'addButtonValue');
    const operationButtons = fixture.debugElement.queryAll(By.css('[color="accent"]'));
    operationButtons.forEach(button => {
      button.nativeElement.click();
      expect(component.addButtonValue).toHaveBeenCalled();
    });
  });

  it('click equals button should be trigger submit method', () => {
    spyOn(component, 'submit');
    const submitButton = fixture.debugElement.query(By.css('[color="warn"][type="submit"]'));
    submitButton.nativeElement.click();
    expect(component.submit).toHaveBeenCalled();
  });

  it('click clear button should be trigger clear method', () => {
    spyOn(component, 'clear');
    const submitButton = fixture.debugElement.query(By.css('[color="warn"][type="button"]'));
    submitButton.nativeElement.click();
    expect(component.clear).toHaveBeenCalled();
  });
});
