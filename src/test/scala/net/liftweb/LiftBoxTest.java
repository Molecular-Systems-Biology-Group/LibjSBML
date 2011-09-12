/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package net.liftweb;

import com.hp.hpl.jena.graph.query.regexptrees.Nothing;
import net.liftweb.common.*;
import net.liftweb.common.Box;
import net.liftweb.common.Full;
import org.apache.log4j.*;
import org.slf4j.Logger;
import scala.Int;
import org.junit.*;
import java.io.Console;
import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class LiftBoxTest {

    public LiftBoxTest(){
      System.out.println(this.getClass() + " constructor is running");
    }

    @Before
    public void setUp() {
      System.out.println(this.getClass() + ".setUp is running");
    }

    @After
    public void tearDown() {
      System.out.println(this.getClass() + ".tearDown is running");
    }

     @Test
    public void openFullBox(){
         Box<Integer> fullBox = new Full<Integer>(new Integer(1));
        //Check if Box is Full
        assertTrue( ! fullBox.isEmpty() );
        //Check content
        assertTrue(fullBox.openTheBox() == 1);
     }
     @Test
     public void checkEmptyBox(){
         BoxJBridge b = new BoxJBridge();
        //Check if Box is empty
         Box emptyBox = b.empty();
         assertTrue(emptyBox.isEmpty());
    }
}