import {Action} from './Action';

export interface History {
  id: number;
  action: Action;
  operator1: number;
  operator2: number;
  result: number;
  timeStamp: Date;
}
