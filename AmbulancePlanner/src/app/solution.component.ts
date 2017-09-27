import { Component} from '@angular/core';

@Component({
  selector: 'solution',
  template: '{{t}}'
})

export class Solution{  
  t: number;
  a: number;
  x: number;
  y: number;

  constructor(t: number,a: number,x: number, y: number){
    this.t=t;
    this.a=a;
    this.x=x;
    this.y=y;
  }
}