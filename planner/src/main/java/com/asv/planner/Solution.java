package com.asv.planner;

public class Solution {
    private int turn;
    private int ambulance;
    private int xPos;
    private int yPos;

    public Solution(int turn,int ambulance,int xPos, int yPos){
        this.turn = turn;
        this.ambulance = ambulance;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public int getTurn(){
        return turn;
    }

    public int getAmbulance(){
        return ambulance;
    }

    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
}
