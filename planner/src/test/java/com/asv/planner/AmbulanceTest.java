package com.asv.planner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmbulanceTest{


    @Test
    public void moveRightTest() {
        Ambulance amb=new Ambulance(2,5,6);
        amb.moveRigh();
        Assert.assertEquals(7,amb.getCurrentY());
    }

    @Test
    public void moveLeftTest() {
        Ambulance amb=new Ambulance(2,5,6);
        amb.moveRigh();
        Assert.assertEquals(5,amb.getCurrentY());
    }

    @Test
    public void moveUpTest() {
        Ambulance amb=new Ambulance(2,5,6);
        amb.moveRigh();
        Assert.assertEquals(4,amb.getCurrentX());
    }

    public void moveDownTest() {
        Ambulance amb=new Ambulance(2,5,6);
        amb.moveDown();
        Assert.assertEquals(6,amb.getCurrentX());
    }

    @Test
    public void setPathTest() {
        Ambulance amb=new Ambulance(2,5,6);
        ArrayList<Square> a = new ArrayList<Square>();
        a.add(new Square(6,6,5,6,"-"));
        a.add(new Square(2,2,1,2,"-"));
        amb.setPath(a);
        Assert.assertEquals(a,amb.getPath());
    }

}