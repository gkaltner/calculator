import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HistoryComponent} from './history.component';
import {HistoryService} from '../service/history.service';
import {of} from 'rxjs';
import {Action} from '../model/Action';
import {By} from '@angular/platform-browser';

describe('HistoryComponent', () => {
  let component: HistoryComponent;
  let fixture: ComponentFixture<HistoryComponent>;
  const mockHistoryService = jasmine.createSpyObj('HistoryService', ['fetchHistory', 'listenHistoryEvents']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HistoryComponent],
      providers: [{
        provide: HistoryService,
        useValue: mockHistoryService
      }]
    })
      .compileComponents();
  });

  beforeEach(() => {
    mockHistoryService.listenHistoryEvents.and.returnValue(of());
    mockHistoryService.fetchHistory.and.returnValue(of());
    fixture = TestBed.createComponent(HistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('refreshHistory received empty list', () => {
    mockHistoryService.fetchHistory.and.returnValue(of([]));
    component.fetchHistories();
    expect(mockHistoryService.fetchHistory).toHaveBeenCalled();
    expect(component.histories).toEqual([]);
  });

  it('refreshHistory received a list with histories elements', () => {
    const histories = [{
        id: 1,
        action: Action.ADD,
        operator1: 1,
        operator2: 2,
        result: 3,
        timeStamp: new Date()
      },
      {
        id: 2,
        action: Action.MULTIPLY,
        operator1: 2,
        operator2: 2,
        result: 4,
        timeStamp: new Date()
      }
    ];
    mockHistoryService.fetchHistory.and.returnValue(of(histories));
    component.fetchHistories();
    expect(mockHistoryService.fetchHistory).toHaveBeenCalled();
    expect(component.histories).toEqual(histories);
  });

  it('list of histories should show in the html', () => {
    component.histories = [{
      id: 1,
      action: Action.ADD,
      operator1: 1,
      operator2: 2,
      result: 3,
      timeStamp: new Date()
    },
      {
        id: 2,
        action: Action.MULTIPLY,
        operator1: 2,
        operator2: 2,
        result: 4,
        timeStamp: new Date()
      }
    ];
    fixture.detectChanges();

    const listElements = fixture.debugElement.queryAll(By.css('mat-list-item'));
    expect(listElements).toHaveSize(2);
  });
});
