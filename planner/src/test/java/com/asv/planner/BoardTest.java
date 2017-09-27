package com.asv.planner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardTest {


    @Test
    public void setPreviousTest() {
        Square a = new Square(4,5,0,0,"-");
        Square b = new Square(4,4,0,0,"-");
        a.setPrevious(b);
        Assert.assertEquals(b,a.getPrevious());

    }



}