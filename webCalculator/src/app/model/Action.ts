export enum Action {
  ADD = 'ADD',
  SUBTRACT = 'SUBTRACT',
  MULTIPLY = 'MULTIPLY',
  DIVIDE = 'DIVIDE'
}

// tslint:disable-next-line:no-namespace
export namespace Action {
  export function parse(action: string): Action {
    switch (action.toLowerCase()) {
      case '+':
        return Action.ADD;
      case '-':
        return Action.SUBTRACT;
      case '*':
        return Action.MULTIPLY;
      case '/':
        return Action.DIVIDE;
      default:
        throw new Error('action can\'t parse');
    }
  }

  export function toString(face: Action): string {
    switch (face) {
      case Action.ADD:
        return '+';
      case Action.SUBTRACT:
        return '-';
      case Action.MULTIPLY:
        return '*';
      case Action.DIVIDE:
        return '/';
    }
  }
}
