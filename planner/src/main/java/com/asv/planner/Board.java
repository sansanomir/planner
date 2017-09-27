package com.asv.planner;
import org.json.JSONException;
import org.json.JSONArray;

import java.util.*;

public class Board {
    private int dimX;
    private int dimY;
    private Square board[][];
    private ArrayList<Notification> notifications;
    private ArrayList<Ambulance> ambulances;
    private ArrayList<Street> streets;
    private ArrayList<Hospital> hospitals;
    private ArrayList<Solution> solutions;
    private ArrayList<Patient> patients;

    private ArrayList<Square> openList;
    private ArrayList<Square> closedList;

    private boolean done;

    Board(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.board = new Square[dimX][dimY];
        this.ambulances = new ArrayList<Ambulance>();
        this.streets = new ArrayList<Street>();
        this.hospitals = new ArrayList<Hospital>();
        this.notifications = new ArrayList<Notification>();
        this.patients = new ArrayList<Patient>();
        this.solutions = new ArrayList<Solution>();

        this.openList = new ArrayList<Square>();
        this.closedList = new ArrayList<Square>();
        this.done = false;

        String boardS = "[[\"1\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"4\"],"
                + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
                + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
                + "[\"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\"],"
                + "[\"null\", \"B\", \"H\", \"2\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
                + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
                + "[\"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"3\", \"null\", \"null\"],"
                + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\"],"
                + "[\"null\", \"B\", \"B\", \"null\", \"B\", \"B\", \"null\", \"B\", \"H\", \"null\"],"
                + "[\"5\", \"null\", \"null\", \"null\", \"null\", \"null\", \"null\", \"6\", \"null\", \"null\"]]";
        String notificationsJ = "[[\"1\", \"4\", \"4\"], [\"5\", \"1\", \"7\"], [\"20\", \"4\", \"9\"]]";
        try {
            JSONArray obj = new JSONArray(boardS);
            //System.out.println(obj);
            for (int i = 0; i < obj.length(); i++) {
                //System.out.println(obj.get(i));
                JSONArray fila = (JSONArray) obj.get(i);
                for (int j = 0; j < fila.length(); j++) {
                    //System.out.println(fila.get(j));
                    if (fila.get(j).equals("null")) {
                        board[i][j] = new Square(i, j, "_");
                        streets.add(new Street(i, j));
                    } else if (fila.get(j).equals("B")) {
                        board[i][j] = new Square(i, j, "B");
                    } else if (fila.get(j).equals("H")) {
                        board[i][j] = new Square(i, j, "H");
                        this.hospitals.add(new Hospital(i, j));
                    } else {
                        //System.out.println(fila.get(j));
                        this.board[i][j] = new Square(i, j, fila.get(j).toString());
                        this.streets.add(new Street(i, j));
                        this.ambulances.add(new Ambulance(Integer.parseInt(fila.get(j).toString()), i, j));

                    }
                }
            }
            this.shortAmbulances();
            JSONArray not = new JSONArray(notificationsJ);
            for (int i = 0; i < not.length(); i++) {
                JSONArray noti = (JSONArray) not.get(i);
                this.notifications.add(new Notification(noti.getInt(0), noti.getInt(1), noti.getInt(2)));
            }
        } catch (JSONException e) {
            System.out.println("Exception JSON: " + e.toString());
        }
    }
    public ArrayList<Solution> getSolutions(){
        return this.solutions;
    }

    public ArrayList<Patient> getPatients(){
        return this.patients;
    }
    public void showAmbulances() {
        for (int i = 0; i < this.ambulances.size(); i++) {
            System.out.print("[" + this.ambulances.get(i).getId() + ", x: " + this.ambulances.get(i).getCurrentX()
                    + ", y: " + this.ambulances.get(i).getCurrentY() + "]");
        }
        System.out.println();
    }

    public void showPatients() {
        for (int i = 0; i < this.patients.size(); i++) {
            System.out.print("[x: " + this.patients.get(i).getPosX()
                    + ", y: " + this.patients.get(i).getPosY() +
                    "t: "+this.patients.get(i).getTime()+"]");
        }
        System.out.println();
    }

    public void showHospitals() {
        for (int i = 0; i < this.hospitals.size(); i++) {
            System.out.print("[x: " + this.hospitals.get(i).getX()
                    + ", y: " + this.hospitals.get(i).getY() + "]");
        }
        System.out.println();
    }

    private void shortAmbulances() {
        ArrayList<Ambulance> n = new ArrayList<Ambulance>();
        int index = 1;

        do {
            for (int i = 0; i < this.ambulances.size(); i++) {
                if (this.ambulances.get(i).getId() == index) {
                    n.add(this.ambulances.get(i));
                    index++;
                }
            }
        } while (n.size() < this.ambulances.size());
        this.ambulances = n;
    }

    public String toString() {
        String boardS = "";
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                //System.out.println(board[i][j].getType());
                boardS += board[i][j].getType() + " ";
            }
            boardS += "\n";
        }
        boardS += "\n\n";
        for (int i = 0; i < this.notifications.size(); i++) {
            boardS += "[" + notifications.get(i).getTurn() + "," + notifications.get(i).getxPos() + "," + notifications.get(i).getyPos() + "]";

        }
        return boardS;
    }

    public int distanceBetweenDots(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return 0;
        } else if (x1 == x2) {
            return Math.abs(y1 - y2);
        } else if (y1 == y2) {
            return Math.abs(x1 - x2);
        } else {
            return Math.abs(y1 - y2) + Math.abs(x1 - x2);
        }
    }

    public int nearestAmbulance(int x, int y) {
        int values[] = new int[this.ambulances.size()];
        int minor = 100;
        for (int i = 0; i < this.ambulances.size(); i++)
            values[i] = distanceBetweenDots(x, y, this.ambulances.get(i).getCurrentX(), ambulances.get(i).getCurrentY());

        for (int i = 0; i < values.length; i++)
            if (minor > values[i])
                minor = values[i];

        for (int i = 0; i < this.ambulances.size(); i++)
            if (minor == distanceBetweenDots(x, y, this.ambulances.get(i).getCurrentX(), ambulances.get(i).getCurrentY())
                    && !this.ambulances.get(i).isBusy())
                return i + 1;

        return -1;
    }

    public ArrayList distanceAmbulances(int x, int y) {
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for (int i = 0; i < this.ambulances.size(); i++) {
            distances.add(distanceBetweenDots(x, y, this.ambulances.get(i).getCurrentX(), ambulances.get(i).getCurrentY()));
        }
        return distances;
    }

    public boolean isInHospital(int x, int y) {
        for (int i = 0; i < this.hospitals.size(); i++) {
            if (this.hospitals.get(i).getX() == x && this.hospitals.get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }

    public int nearestHospital(int x, int y) {
        int values[] = new int[this.hospitals.size()];
        int minor = 100;
        int hospital = -1;
        for (int i = 0; i < this.hospitals.size(); i++) {
            values[i] = distanceBetweenDots(x, y, this.hospitals.get(i).getX(), hospitals.get(i).getY());
        }
        for (int i = 0; i < values.length; i++) {
            //System.out.println("Minor "+values[i]);
            if (minor > values[i]) {
                minor = values[i];
            }
        }
        for (int i = 0; i < this.hospitals.size(); i++) {
            if (minor == distanceBetweenDots(x, y, this.hospitals.get(i).getX(), hospitals.get(i).getY())) {
                //System.out.println("Ambulance "+i);
                return i + 1;
            }
        }
        return hospital;
    }

    public int idPatient(int x, int y) {
        for (int i = 0; i < this.patients.size(); i++) {
            if (this.patients.get(i).getPosX() == x
                    && this.patients.get(i).getPosY() == y) {
                return i;
            }
        }
        return -1;
    }

    private boolean outOfBoard(int x,int y){
        if(x<0 || x>(dimX)-1)
            return true;
        if(y<0 || y>(dimY)-1)
            return true;
        return false;
    }

    private boolean isBuilding(int x,int y){
        return this.board[x][y].getType().equals("B");
    }

    public int optimistic(Square sqInit, Square squareEnd){
        int xI = sqInit.getXPos();
        int yI = sqInit.getYPos();

        int xP = squareEnd.getXPos();
        int yP = squareEnd.getYPos();

        if(xI == xP && yI == yP){
            return 0;
        }
        else if(xI == xP){
            if(yI-yP<0){
                return 1 + this.optimistic(new Square(xI,yI+1,xI,yI,"opt"),squareEnd);
            }
            else{
                return 1 + this.optimistic(new Square(xI,yI-1,xI,yI,"opt"),squareEnd);
            }
        }

        else if(yI == yP){
            if(xI-xP<0){
                return 1 + this.optimistic(new Square(xI+1,yI,xI,yI,"opt"),squareEnd);
            }
            else{
                return 1 + this.optimistic(new Square(xI-1,yI,xI,yI,"opt"),squareEnd);
            }
        }
        else{
            return Math.abs(xI+xP) + Math.abs(yI+yP);
        }
    }

    private ArrayList<Square> expand(Square sq,int xDest, int yDest){
        ArrayList<Square> squares = new ArrayList<Square>();
        int x = sq.getXPos();
        int y = sq.getYPos();
        int gCost = sq.getGCost();
        boolean destiny = this.oneMovement(sq.getXPos(),sq.getYPos(),xDest,yDest);
        if(!destiny) {
            if (!this.outOfBoard(x, y + 1) && !isBuilding(x, y + 1)) {
                Square sqN = new Square(x, y + 1, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x, y - 1) && !isBuilding(x, y - 1)) {
                Square sqN = new Square(x, y - 1, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x - 1, y) && !isBuilding(x - 1, y)) {
                Square sqN = new Square(x - 1, y, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x + 1, y) && !isBuilding(x + 1, y)) {
                Square sqN = new Square(x + 1, y, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
        }
        else{
            if (!this.outOfBoard(x, y + 1)) {
                Square sqN = new Square(x, y + 1, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x, y - 1)) {
                Square sqN = new Square(x, y - 1, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x - 1, y)) {
                Square sqN = new Square(x - 1, y, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
            if (!this.outOfBoard(x + 1, y)) {
                Square sqN = new Square(x + 1, y, x, y, "square");
                sqN.setGCost(gCost + 1);
                squares.add(sqN);
            }
        }
        return squares;
    }

    private Square lowestFCostOpen(){
        Square cheapest = this.openList.get(0);
        for(int i=0;i<this.openList.size();i++){
            if(this.openList.get(i).getFCost() < cheapest.getFCost()){
                cheapest = openList.get(i);
            }
        }
        return cheapest;
    }

    public int getOptimisticCost(int xInit,int yInit, int xLast, int yLast){
        if(xInit == xLast){
            return Math.abs(yInit - yLast);
        }
        else if(yInit == yLast){
            return Math.abs(xInit - xLast);
        }
        else{
            return Math.abs(yInit - yLast) + Math.abs(xInit - xLast);
        }
    }
    public void game(){
        for(int t=1;t<100;t++) {
            if(this.notifications.size() > 0){
                Notification actualNotification = this.notifications.get(0);
                if (actualNotification.getTurn() == t) {
                    System.out.println("xeuuu");
                    this.patients.add(new Patient(this.notifications.get(0).getxPos(), this.notifications.get(0).getyPos(),t));
                    int nearestAmbulance = this.nearestAmbulance(actualNotification.getxPos(), actualNotification.getyPos());
                    nearestAmbulance--;
                    System.out.println("Nearest Ambulance: "+nearestAmbulance+this.ambulances.get(nearestAmbulance).getCurrentX()
                            +this.ambulances.get(nearestAmbulance).getCurrentY());
                    if(nearestAmbulance != -2){
                    /*System.out.println("Nearest ambulance: " + this.ambulances.get(nearestAmbulance).getId() + " " +
                            +this.ambulances.get(nearestAmbulance).getInitX() + " "
                            + this.ambulances.get(nearestAmbulance).getInitY());*/
                        this.ambulances.get(nearestAmbulance).setPatientX(actualNotification.getxPos());
                        this.ambulances.get(nearestAmbulance).setPatientY(actualNotification.getyPos());
                        this.ambulances.get(nearestAmbulance).setBusy(true);

                        System.out.println("Notification: "+this.notifications.get(0).getxPos()+this.notifications.get(0).getyPos());
                        List<Square> path = this.squarePath(this.ambulances.get(nearestAmbulance).getCurrentX()
                                ,this.ambulances.get(nearestAmbulance).getCurrentY()
                                , actualNotification.getxPos()
                                , actualNotification.getyPos());
                        this.ambulances.get(nearestAmbulance).setPath(path);
                        System.out.println("epawperap`");
                        for(int i=0;i<path.size();i++){
                            System.out.print("p: "+path.get(i).getXPos()+path.get(i).getYPos());
                        }
                        System.out.println();
                        this.notifications.remove(0);

                    }
                }
            }
            for (int i = 0; i < this.ambulances.size(); i++) {
                System.out.println();
                int currentAX = this.ambulances.get(i).getCurrentX();
                int currentAY = this.ambulances.get(i).getCurrentY();
                int initAX = this.ambulances.get(i).getInitX();
                int initAY = this.ambulances.get(i).getInitY();
                int patientX = this.ambulances.get(i).getPatientX();
                int patientY = this.ambulances.get(i).getPatientY();

                //Ambulancia tiene que recoger a un paciente
                if (patientX != -1) {
                    System.out.println("Voyyyyy"+this.ambulances.get(i).getId());

                    if(this.ambulances.get(i).getPath().size() == 0){

                        System.out.println("Camino ambulancia(recojo paciente): "+currentAX+currentAY);
                        System.out.println("Camino ambulancia(paciente): "+patientX+patientY);
                        this.patients.remove(this.patients.size()-1);

                        this.ambulances.get(i).setPatientX(-1);
                        this.ambulances.get(i).setPatientY(-1);

                        //para llevarlo al hospital
                        this.ambulances.get(i).setHospitalX(-2);
                        this.ambulances.get(i).setPath(null);
                        int nearestHospital = this.nearestHospital(currentAX,currentAY);
                        List<Square> nextPath = this.squarePath(currentAX,currentAY,
                                this.hospitals.get(nearestHospital-1).getX(),
                                this.hospitals.get(nearestHospital-1).getY());
                        for(int j=0;j<nextPath.size();j++){
                            System.out.print("p: "+nextPath.get(j).getXPos()+nextPath.get(j).getYPos());
                        }
                        System.out.println();
                        this.ambulances.get(i).setPath(nextPath);
                        this.solutions.add(new Solution(t,ambulances.get(i).getId(),currentAX,currentAY));
                    }
                    else{
                        int xR = this.ambulances.get(i).getPath().get(0).getXPos();
                        int yR = this.ambulances.get(i).getPath().get(0).getYPos();
                        this.solutions.add(new Solution(t
                                ,this.ambulances.get(i).getId()
                                ,this.ambulances.get(i).getCurrentX()
                                ,this.ambulances.get(i).getCurrentY()));

                        this.ambulances.get(i).setCurrentX(xR);
                        this.ambulances.get(i).setCurrentY(yR);

                        //paciente esperando
                        this.patients.add(new Patient(this.ambulances.get(i).getPatientX(), this.ambulances.get(i).getPatientY(),t));
                        //mover ambulancia a siguiente ppoción

                        System.out.println("xY yR:"+xR+yR);
                        System.out.println("xY yRAMB:"+this.ambulances.get(i).getCurrentX()+this.ambulances.get(i).getCurrentY());

                        System.out.println("Camino ambulancia: "+this.ambulances.get(i).getPath().get(0).getXPos()+","+
                                this.ambulances.get(i).getPath().get(0).getYPos());
                        this.ambulances.get(i).getPath().remove(0);


                    }
                }
                //llevar al paciente al hospital más cercano
                else if (this.ambulances.get(i).getHospitalX() == -2 || this.ambulances.get(i).getHospitalX() > -1) {
                    System.out.println("Llevamos");
                    if(this.ambulances.get(i).getPath().size() == 0){
                        System.out.println("Llegado al hospital");
                        this.ambulances.get(i).setDestinyX(initAX);
                        this.ambulances.get(i).setDestinyY(initAY);
                        this.ambulances.get(i).setPath(null);
                        List<Square> path = this.squarePath(currentAX,currentAY,initAX,initAY);
                        this.ambulances.get(i).setPath(path);
                        this.ambulances.get(i).setBusy(false);
                        //this.solutions.add(new Solution(t,ambulances.get(i).getId(),currentAX,currentAY));
                        this.ambulances.get(i).setHospitalX(-1);
                        this.ambulances.get(i).setHospitalY(-1);
                        //ambulancia volverá a su posición return;
                    }
                    else{
                        int xR = this.ambulances.get(i).getPath().get(0).getXPos();
                        int yR = this.ambulances.get(i).getPath().get(0).getYPos();
                        this.ambulances.get(i).setCurrentX(xR);
                        this.ambulances.get(i).setCurrentY(yR);

                        this.solutions.add(new Solution(t,ambulances.get(i).getId()
                                ,this.ambulances.get(i).getCurrentX()
                                ,this.ambulances.get(i).getCurrentY()));

                        System.out.println("xY yR(llevr):"+xR+yR);
                        System.out.println("xY yRAMB(llevr):"+this.ambulances.get(i).getCurrentX()+this.ambulances.get(i).getCurrentY());

                        System.out.println("Camino ambulanciaHospi: "+this.ambulances.get(i).getPath().get(0).getXPos()+","+
                                this.ambulances.get(i).getPath().get(0).getYPos());
                        this.ambulances.get(i).getPath().remove(0);
                    }
                }
                //Ambulancia tiene que volver a su posición inicial ya que está vacía
                else if (this.ambulances.get(i).getDestinyX() != -1) {
                    System.out.println("Volvemos al inicio");
                    if(this.ambulances.get(i).getPath().size() == 0) {
                        //ha vuelto
                        this.ambulances.get(i).setDestinyX(-1);
                        this.ambulances.get(i).setDestinyY(-1);
                        //por si acaso
                        this.ambulances.get(i).setPatientX(-1);
                        this.ambulances.get(i).setPatientX(-1);


                    }
                    else{
                        int xR = this.ambulances.get(i).getPath().get(0).getXPos();
                        int yR = this.ambulances.get(i).getPath().get(0).getYPos();
                        this.ambulances.get(i).setCurrentX(xR);
                        this.ambulances.get(i).setCurrentY(yR);

                        this.solutions.add(new Solution(t,ambulances.get(i).getId()
                                ,this.ambulances.get(i).getCurrentX()
                                ,this.ambulances.get(i).getCurrentY()));

                        System.out.println("xY yR(retorno):"+xR+yR);
                        System.out.println("xY yRAMB(retorno):"+this.ambulances.get(i).getCurrentX()+this.ambulances.get(i).getCurrentY());

                        System.out.println("Camino ambulanciaRetoro: "+this.ambulances.get(i).getPath().get(0).getXPos()+","+
                                this.ambulances.get(i).getPath().get(0).getYPos());
                        this.ambulances.get(i).getPath().remove(0);
                    }
                }
            }
            //System.out.println("Ambulances:");
            //this.showAmbulances();
            //System.out.println("Hospitals:");
            //this.showHospitals();
            System.out.println("Patients:");
            this.showPatients();
            System.out.print("Notifications: " + "\n" + "[");
            for (int i = 0; i < this.notifications.size(); i++) {
                System.out.print("[" +
                        + (this.notifications.get(i).getTurn()) + ","
                        + this.notifications.get(i).getxPos() + ","
                        + this.notifications.get(i).getyPos()
                        + "]");
            }
            System.out.println("]");
            System.out.print("Solution: " + "\n" + "[");
            for (int i = 0; i < this.solutions.size(); i++) {
                System.out.print("[" + this.solutions.get(i).getTurn() + ","
                        + (this.solutions.get(i).getAmbulance()) + ","
                        + this.solutions.get(i).getXPos() + ","
                        + this.solutions.get(i).getYPos()
                        + "]");
            }
            System.out.println("]");
            System.out.println();
            System.out.println();
        }
    }

    private boolean oneMovement(int oldX, int oldY, int newX, int newY){
        return ((oldX == newX && Math.abs(oldY-newY) == 1) || (oldY == newY && Math.abs(oldX-newX) == 1));
    }

    public List<Square> squarePath(int oldX, int oldY, int newX, int newY){
        this.openList = new ArrayList<Square>();
        this.closedList = new ArrayList<Square>();
        openList.add(this.board[oldX][oldY]);
        done = false;
        Square current;
        //System.out.println("coordenadas"+oldX+oldY+newX+newY);
        while(!this.openList.isEmpty()){
            current = this.lowestFCostOpen();
            this.closedList.add(current);
            this.openList.remove(current);

            //System.out.println("current"+current.getXPos()+current.getYPos());
            //goal
            if(current.getXPos() == newX && current.getYPos() == newY){
                return this.calcPath(board[oldX][oldY],current);
            }
            List<Square> adjacentSquares=this.expand(current,newX,newY);

            for(int i=0;i<adjacentSquares.size();i++){
                Square currentSquare = adjacentSquares.get(i);
                if (!openList.contains(currentSquare)) { // node is not in openList
                    currentSquare.setPrevious(current); // set current node as previous for this node
                    currentSquare.sethCost(this.getOptimisticCost(currentSquare.getXPos(),currentSquare.getYPos(),newX,newY)); // set h costs of this node (estimated costs to goal)
                    currentSquare.setGCost(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentSquare); // add node to openList
                } else { // node is in openList
                    int costGold=100000000;
                    Square old;
                    for(int j=0; j<this.openList.size();j++){
                        if(this.openList.get(j).getXPos()  == current.getXPos()
                                && this.openList.get(j).getYPos() == current.getYPos()){
                            costGold = this.openList.get(j).getGCost();
                        }
                    }
                    if (currentSquare.getGCost() < costGold) { // costs from current node are cheaper than previous costs
                        currentSquare.setPrevious(current); // set current node as previous for this node
                        currentSquare.setGCost(current); // set g costs of this node (costs from start to this node)
                    }
                }
            }
        }
        return null;
    }

    private List<Square> calcPath(Square start, Square goal) {
        // TODO if invalid nodes are given (eg cannot find from
        // goal to start, this method will result in an infinite loop!)
        LinkedList<Square> path = new LinkedList<Square>();
        Square curr = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(curr);
            curr = (Square) curr.getPrevious();
            if (curr.equals(start)) {
                done = true;
            }
        }
        return path;
    }

    public void aTMore(){
        int currentX=-1;
        int currentY=-1;
        int currentTime=-1;
        ArrayList<Patient> a= new ArrayList<Patient>();
        for(int i=0;i<this.patients.size();i++){
            int x = this.patients.get(i).getPosX();
            int y = this.patients.get(i).getPosY();
            int t = this.patients.get(i).getTime();
            a.add(new Patient(x,y,t));
            if(x!= currentX && y!=currentY && currentX!=-1){
                a.add(new Patient(currentX,currentY,currentTime+1));
            }
            currentX = x;
            currentY = y;
            currentTime = t;
        }
        a.add(new Patient(currentX,currentY,currentTime+1));
        this.patients = a;
        return;
    }
}
