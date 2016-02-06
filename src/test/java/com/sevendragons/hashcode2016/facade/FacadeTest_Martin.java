package com.sevendragons.hashcode2016.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class FacadeTest_Martin {
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

    Facade facade = new Facade();


    @Test
    public void test_fromGrid() {
        Scanner scanner = new Scanner("5 7\n" +
                "....#..\n" +
                "..###..\n" +
                "..#.#..\n" +
                "..###..\n" +
                "..#....\n");
        Facade.Grid grid = Facade.Grid.fromScanner(scanner);
        Assert.assertEquals(5, grid.cells.length);
        Assert.assertEquals("....#..", new String(grid.cells[0]));
        Assert.assertEquals("..###..", new String(grid.cells[3]));
    }

    @Test
    public void test_generateCommnand() {
        Scanner scanner = new Scanner("2 7\n" +
                "....#..\n" +
                "..#....\n");
        Facade.Grid grid = Facade.Grid.fromScanner(scanner);
        List<Facade.Command> commands = Facade.generateCommands(grid);
        List<Facade.Command> expected = Arrays.asList(new Facade.PaintSquare(0,4,0), new Facade.PaintSquare(1,2,0));
        Assert.assertEquals(expected, commands);
    }
}
