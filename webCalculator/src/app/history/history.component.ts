import {Component, OnInit} from '@angular/core';
import {History} from '../model/History';
import {faDivide, faEquals, faMinus, faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';
import {HistoryService} from '../service/history.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  histories: Array<History>;
  faPlus = faPlus;
  faMinus = faMinus;
  faTimes = faTimes;
  faDivide = faDivide;
  faEquals = faEquals;

  constructor(private historyService: HistoryService) {
  }

  ngOnInit(): void {
    this.historyService.historyObs.subscribe(() => {
      this.fetchHistories();
    });
    this.fetchHistories();
  }

  fetchHistories(): void {
    this.historyService.fetchHistory().subscribe((histories: Array<History>) => {
      this.histories = histories;
    });
  }

}
