package com.asv.planner;

public class Notification {
    private int turn;
    private int xPos;
    private int yPos;

    public  Notification(int turn,int x,int y){
        this.turn = turn;
        this.xPos = x;
        this.yPos = y;
    }

    public int getTurn() {
        return turn;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
