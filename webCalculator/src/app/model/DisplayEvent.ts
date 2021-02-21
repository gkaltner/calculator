export abstract class DisplayEvent {
}

export class DisplayValue extends DisplayEvent {
  private readonly $value: string;

  constructor(value: string) {
    super();
    this.$value = value;
  }

  public get value(): string {
    return this.$value;
  }
}

export class DisplayClear extends DisplayEvent {
}

export class DisplaySubmit extends DisplayEvent {
}
