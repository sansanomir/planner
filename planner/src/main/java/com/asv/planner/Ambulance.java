package com.asv.planner;

import java.util.List;

public class Ambulance {
    private int id;
    private int initX;
    private int initY;

    private int currentX;
    private int currentY;

    private int patientX;
    private int patientY;

    private int patientInitX;
    private int patientInitY;

    private int destinyX;
    private int destinyY;

    private int hospitalX;
    private int hospitalY;
    private boolean busy;

    private List<Square> path;

    public Ambulance(int id, int initX, int initY){
        this.id = id;
        this.initX = initX;
        this.initY = initY;
        this.currentX = initX;
        this.currentY = initY;
        this.patientX = -1;
        this.patientY = -1;

        this.patientInitX = -1;
        this.patientInitY = -1;

        this.destinyX = -1;
        this.destinyY = -1;

        this.hospitalX = -1;
        this.hospitalY = -1;
        this.busy = false;
        this.path = null;
    }

    public Ambulance(Ambulance amb){
        this.id = amb.id;
        this.initX = amb.initX;
        this.initY = amb.initY;
        this.currentX = amb.initX;
        this.currentY = amb.initY;
        this.patientX = amb.patientX;
        this.patientY = amb.patientY;

        this.patientInitX = amb.patientInitX;
        this.patientInitY = amb.patientInitY;

        this.destinyX = amb.destinyX;
        this.destinyY = amb.destinyY;

        this.hospitalX = amb.hospitalX;
        this.hospitalY = amb.hospitalY;
        this.busy = amb.busy;
        this.path = amb.path;
    }
    public List<Square> getPath(){
        return this.path;
    }
    public void setPath(List<Square> s){
        this.path = s;
    }


    public void removeFirstPath(){
        this.path.remove(0);
    }

    public int getId(){
        return id;
    }
    public int getInitX(){
        return initX;
    }
    public int getInitY(){
        return initY;
    }

    public int getCurrentX(){
        return currentX;
    }

    public int getCurrentY(){
        return currentY;
    }
    public int getPatientX(){
        return patientX;
    }

    public int getPatientY(){
        return patientY;
    }

    public int getPatientInitY(){
        return patientInitY;
    }

    public int getPatientInitX(){
        return patientInitX;
    }

    public int getHospitalY(){
        return hospitalY;
    }

    public int getHospitalX(){
        return hospitalX;
    }

    public int getDestinyX(){
        return destinyX;
    }

    public int getDestinyY(){
        return destinyY;
    }
    public boolean isBusy(){
        return busy;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
    public void setPatientX(int x){
        this.patientX = x;
    }

    public void setHospitalX(int x){
        this.hospitalX = x;
    }

    public void setHospitalY(int y){
        this.hospitalY = y;
    }

    public void setPatientY(int y){
        this.patientY = y;
    }
    public void setPatientInitY(int y){
        this.patientInitY = y;
    }

    public void setPatientInitX(int x){
        this.patientInitX = x;
    }

    public void setDestinyX(int x){
        this.destinyX = x;
    }

    public void setDestinyY(int y){
        this.destinyY = y;
    }
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void moveRigh(){
        this.currentY ++;
    }

    public void moveLeft(){
        this.currentY --;
    }

    public void moveDown(){
        this.currentX ++;
    }

    public void moveUp(){
        this.currentX --;
    }
}
