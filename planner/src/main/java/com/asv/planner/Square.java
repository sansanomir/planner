package com.asv.planner;

import sun.rmi.server.InactiveGroupException;

public class Square {
    private Integer id;
    private Integer xPos;
    private Integer yPos;

    private Integer xPrev;
    private Integer yPrev;

    private Square previous;

    private String type;

    private Integer gCost;
    private Integer hCost;

    public Square(int x,int y, int xPrev,int yPrev, String type){
        xPos = x;
        yPos = y;
        this.xPrev = xPrev;

        this.yPrev = yPrev;
        this.type = type;

        this.gCost = 0;
        this.hCost = 0;
    }

    public Square(int x,int y, String type){
        xPos = x;
        yPos = y;
        this.xPrev = -1;

        this.yPrev = -1;
        this.type = type;

        this.gCost = 0;
        this.hCost = 0;
    }

    public void setPrevious(Square p){
        this.previous = p;
    }

    public Integer getId(){
        return id;
    }

    public Integer getXPos(){
        return xPos;
    }

    public Integer getYPos(){
        return yPos;
    }

    public Integer getXPrev(){
        return xPrev;
    }

    public Integer getYPrev(){
        return yPrev;
    }

    public int getFCost(){
        return gCost + hCost;
    }

    public int getHCost(){
        return hCost;
    }

    public int getGCost(){
        return gCost;
    }

    public Square getPrevious(){
        return this.previous;
    }

    public String getType(){
        return type;
    }

    public void setXPos(Integer i){
        xPos = i;
    }

    public void setYPos(Integer i){
        yPos = i;
    }

    public void setType(String t){
        type = t;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }
    public void setGCost(Square previous){
        this.gCost = previous.getGCost()+1;
    }

    public void setGCost(int cost){
        this.gCost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Square other = (Square) obj;
        if (this.xPos != other.xPos) {
            return false;
        }
        if (this.yPos != other.yPos) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.xPos;
        hash = 17 * hash + this.yPos;
        return hash;
    }

}