package com.sevendragons.hashcode2016.facade;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FacadeTest_PJ {
    // Tips:
    //
    // Run a single test method in IntelliJ:
    //  => move the cursor inside the method body and press Control Shift F10
    //
    // Run all test methods of a class in IntelliJ:
    //  => move the cursor inside the class body but outside any method and press Control Shift F10
    //
    // Re-run the last test, whether one method or all methods:
    //  => press Shift F10
    //
    // Debug instead of run:
    //  => Control Shift F9 for first run
    //  => Shift F9 for re-run
    //
    // Run all test methods of a class with Maven:
    //  => mvn clean test -Dtest=FacadeTest
    //

   String exampleGrid1 = "5 7\n" +
            "....#..\n" +
            "..###..\n" +
            "..#.#..\n" +
            "..###..\n" +
            "..#....\n";

    @Test
    public void testGridParsing() {
        Facade_PJ facade = new Facade_PJ(new Scanner(exampleGrid1));
        assertArrayEquals(new int[]{0,0,1,1,1,0,0}, facade.getOriginalGrid()[1]);
    }

    @Test
    public void testSumMatrix() {
        Facade_PJ facade = new Facade_PJ(new Scanner(exampleGrid1));
        assertEquals(10, facade.getCornerSumGrid()[0][0]);
    }

//    @Test
//    public void testSolution() {
//        Facade_PJ facade = new Facade_PJ(new Scanner(exampleGrid1));
//        facade.resolve();
//        assertArrayEquals(facade.getOriginalGrid()[0], facade.getResultGrid()[0]);
//    }


}
