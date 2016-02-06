package com.sevendragons.hashcode2016.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FacadeTest {
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

    Facade.Grid exampleGrid1 = Facade.Grid.fromString("5 7\n" +
            "....#..\n" +
            "..###..\n" +
            "..#.#..\n" +
            "..###..\n" +
            "..#....\n");

    @Test
    public void test_fromGrid() {
        Facade.Grid grid = exampleGrid1;
        assertEquals(5, grid.cells.length);
        assertEquals("....#..", new String(grid.cells[0]));
        assertEquals("..###..", new String(grid.cells[3]));
    }

    @Test
    public void test_generateCommnand() {
        Scanner scanner = new Scanner("2 7\n" +
                "....#..\n" +
                "..#....\n");
        Facade.Grid grid = Facade.Grid.fromScanner(scanner);
        List<Facade.Command> commands = Facade.generateCommands(grid);
        List<Facade.Command> expected = Arrays.asList(new Facade.PaintSquare(0,4,0), new Facade.PaintSquare(1,2,0));
        assertEquals(expected, commands);
    }

    @Test
    public void test_countCellsToPaint() {
        Facade.Grid grid = exampleGrid1;
        assertEquals(10, grid.countCellsToPaint(0,0,5,7));
    }

    @Test
    public void test_findBestBrush_on_empty_cell_is_0() {
        Facade.Grid grid = Facade.Grid.fromString("2 3\n" +
                        "...\n" +
                        "...\n" );
        assertEquals(0, grid.findBestBrush(0, 0));
    }

    @Test
    public void test_findBestBrush_on_solitary_cell_is_0() {
        Facade.Grid grid = Facade.Grid.fromString("2 3\n" +
                        "#..\n" +
                        "...\n" );
        assertEquals(0, grid.findBestBrush(0, 0));
    }

    @Test
    public void test_findBestBrush_on_complete_3block_is_1() {
        Facade.Grid grid = Facade.Grid.fromString("3 3\n" +
                "###\n" +
                "###\n" +
                "###\n");
        assertEquals(1, grid.findBestBrush(0, 0));
    }

    @Test
    public void test_findBestBrush_on_3block_with_hole_is_1() {
        Facade.Grid grid = Facade.Grid.fromString("3 3\n" +
                "###\n" +
                "#.#\n" +
                "###\n");
        assertEquals(1, grid.findBestBrush(0, 0));
    }

    @Test
    public void test_findBestBrush_on_complete_2block_is_0() {
        Facade.Grid grid = Facade.Grid.fromString("3 3\n" +
                "##.\n" +
                "##.\n" +
                "...\n");
        assertEquals(0, grid.findBestBrush(0, 0));
    }
}
