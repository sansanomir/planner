package com.asv.planner;

public class Patient {
    private int initX;
    private int initY;

    private int posX;
    private int posY;

    private int time;

    Patient(int x, int y, int time){
        this.initX = x;
        this.initY = y;
        this.posX = x;
        this.posY = y;
        this.time = time;
    }

    int getInitX(){
        return initX;
    }

    int getInitY(){
        return initY;
    }
    int getPosX(){
        return posX;
    }

    int getPosY(){
        return posY;
    }

    int getTime(){return time;}

    void setPosX(int x){
        posX = x;
    }

    void setPosY(int y){
        posY = y;
    }
}
