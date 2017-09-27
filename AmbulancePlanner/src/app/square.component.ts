import { Component} from '@angular/core';

@Component({
  selector: 'solution',
  template: '{{t}}'
})

export class Square{  
  public type: String;
  x: number;
  y: number;

  constructor(t: String,x: number, y: number){
    this.type=t;
    this.x=x;
    this.y=y;
  }
}