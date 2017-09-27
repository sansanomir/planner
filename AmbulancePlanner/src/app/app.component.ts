import { Component } from '@angular/core';
import { Http, Response} from '@angular/http';
import { Observable} from 'rxjs/Rx';
import { Injectable } from '@angular/core';
import { Solution } from './solution.component';
import { Square } from './square.component';
import {SimpleTimer} from 'ng2-simple-timer';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

@Injectable()
export class AppComponent {
  title = 'Ambulance Planner';
  board: Object;
  boardSquare:Square[] =[];
  notifications: Object;
  jsonMaps: Object;
  solutions : Solution[] =[];
  patients: Solution[]=[];
  row: Square[];
  new: Object;
  time: Number;

  constructor(private http: Http,private st: SimpleTimer) {
    this.time = 0;
      console.log('constructor');
      this.http.get("http://localhost:9000/map").subscribe(
        data => {this.jsonMaps = data.json();
          //console.log(Object.keys(this.jsonMaps).length);
          for(let i=0; i<Object.keys(this.jsonMaps).length;i++){
            //console.log(this.jsonMaps[i].turn);
            if(this.jsonMaps[i].turn!="-1"){
              //console.log(this.jsonMaps[i].turn);
              this.solutions.push(new Solution(this.jsonMaps[i].turn, this.jsonMaps[i].a, this.jsonMaps[i].x,this.jsonMaps[i].y));
            }
            else{
              //console.log('patientaasdfqsdfas');
              this.patients.push(new Solution(this.jsonMaps[i].t, this.jsonMaps[i].a, this.jsonMaps[i].x,this.jsonMaps[i].y));         
            }
          }
          this.board = JSON.parse("[[\"1\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"4\"],"
          + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
          + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
          + "[\"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\"],"
          + "[\"null\", \"B\", \"H\", \"2\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
          + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
          + "[\"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"3\", \"null\", \"null\"],"
          + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
          + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"H\", \"null\"],"
          + "[\"5\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"6\", \"null\", \"null\"]]");
         this.notifications = JSON.parse("[[\"1\", \"4\", \"4\"], [\"5\", \"1\", \"7\"], [\"20\", \"4\", \"9\"]]");

         console.log('Creating timer');
         this.st.newTimer('1sec',1);
         this.st.subscribe('1sec',() => this.tiemer());

        });
  }

  private tiemer():void{

    console.log('timer: '+this.time);
    let ambulances = this.getSolutionsByTime(this.time);
    let patients = this.getPatientsByTime(this.time);
    for(let i=0;i<Object.keys(this.board).length;i++){
      for(let j=0;j< Object.keys(this.board[i]).length;j++){
        let ambulance = this.getAmbulanceByPosition(ambulances,i,j);
        let patient = this.getPatientByPosition(patients,i,j);
        
        if(ambulance!=null && patient !=null){
          console.log('ambulanceP atient');
          this.boardSquare.push(new Square('p'+this.board[i][j],i,j));
        }else if(ambulance!=null){
          console.log('ambulance');
          this.boardSquare.push(new Square('a',i,j));
        }
        else if(patient!=null){
          console.log('patient');
          this.boardSquare.push(new Square("P",i,j));
        }
        else{
          //console.log('bucle');
          if(this.board[i][j] == "null"){
            this.boardSquare.push(new Square("_",i,j));
          }
          else if(this.board[i][j] == "B"){
            this.boardSquare.push(new Square("B",i,j));
          }
          else if(this.board[i][j] == "H"){
            this.boardSquare.push(new Square("H",i,j));
          }
          else{
            this.boardSquare.push(new Square('ab'+this.board[i][j],i,j));
          }
        }   
      }
    }

    this.new =[];
    for(let i=0;i<10;i++){
      this.new[i] = this.allSquaresRow(i);
    }
    this.boardSquare = [];
    this.time = +this.time + 1;
    if(this.time== 50){
      this.st.delTimer('1sec');
    }
  }

  private allSquaresRow(z: number): Square[]{
    let sq: Square[]=[];
    for(let i=0;i<this.boardSquare.length;i++){
      if(this.boardSquare[i].x == z){
        sq.push(this.boardSquare[i]);
      }
    }
    return sq;
  }

  private getAmbulanceByPosition(amb: Solution[],x: Number,y: Number): Solution{
    if(amb == undefined){
      return null;
    }
    
    for(let i=0;i<amb.length;i++){
      if(amb[i].x == x && amb[i].y == y){
        return amb[i];
      }
    }
    return null;
  }

  private getPatientByPosition(pat: Solution[],x: Number,y: Number): Solution{
    //console.log('getPatientByPosition');
    
    if(pat == undefined){
      return null;
    }
    //console.log('2getPatientByPosition2');
    for(let i=0;i<pat.length;i++){
      if(pat[i].x == x && pat[i].y == y){
        return pat[i];
      }
    }
    return null;
  }

  private getSolutionsByTime(time: Number): Solution[]{
    //console.log('solutions');
    let sol: Solution[]= Array();
    for(let i=0;i<this.solutions.length;i++){
      if(this.solutions[i].t == time){
        sol.push(this.solutions[i])
      }
    }
    return sol;
  }

  private getPatientsByTime(time: Number): Solution[]{
    let pat: Solution[]= Array();
    for(let i=0;i<this.patients.length;i++){
     
      if(this.patients[i].t == time){
        pat.push(this.patients[i])
      }
    }
    return pat;
  }
}
