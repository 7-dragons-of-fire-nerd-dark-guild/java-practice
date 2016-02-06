package com.sevendragons.hashcode2016.facade;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FacadeTest_Pierre {
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

    @Test
    public void test_something_gives_something() {
        // just to have some static imports ready to use
        // delete when you have actual meaningful tests
        assertEquals(Arrays.asList(1, 2), new ArrayList<>(Arrays.asList(1, 2)));
        assertNotEquals(Collections.emptyList(), Collections.emptySet());
    }

    @Test
    public void testCopy() throws Exception {
        Facade.Grid grid;
        char[][] expected = {{1, 2}, {2, 1}};
        grid = new Facade.Grid(expected);
        Facade.Grid copy = grid.copy();
        assertArrayEquals(expected,copy.getCells());

    }


    @Test
    public void testCopyNotSquare() throws Exception {
        Facade.Grid grid;
        char[][] expected = {{1, 2}, {2, 1}, {2, 1}};
        grid = new Facade.Grid(expected);
        Facade.Grid copy = grid.copy();
        assertArrayEquals(expected,copy.getCells());

    }

    @Test
    public void testToString_line() throws Exception {
        Facade.PaintLine line = new Facade.PaintLine(0,0,1,1);
        assertEquals("PAINT_LINE 0 0 1 1",line.toString());
    }

    @Test
    public void testApply_line() throws Exception {
        Facade.PaintLine line = new Facade.PaintLine(0,0,1,0);
        char[][] grid = {{Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        char[][] expected = {{Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        Facade.Grid grid1 = new Facade.Grid(grid);
        line.apply(grid1);
        assertArrayEquals(expected,grid1.getCells());

    }
    @Test
    public void testApply_line_all() throws Exception {
        Facade.PaintLine line = new Facade.PaintLine(0,0,2,0);
        char[][] grid = {{Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        char[][] expected = {{Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_DONE, Facade.MARKER_EMPTY}};
        Facade.Grid grid1 = new Facade.Grid(grid);
        line.apply(grid1);
        assertArrayEquals(expected,grid1.getCells());

    }
    @Test
    public void testToString_erase() throws Exception {
        Facade.EraseCell eraseCell = new Facade.EraseCell(0,0);
        assertEquals("ERASE_CELL 0 0",eraseCell.toString());
    }

    @Test
    public void testApply_erase() throws Exception {
        Facade.EraseCell eraseCell = new Facade.EraseCell(0,0);
        char[][] grid = {{Facade.MARKER_EMPTY, Facade.MARKER_DONE}, {Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        char[][] expected = {{Facade.MARKER_EMPTY, Facade.MARKER_DONE}, {Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        Facade.Grid grid1 = new Facade.Grid(grid);
        eraseCell.apply(grid1);
        assertArrayEquals(expected,grid1.getCells());

    }
    @Test
    public void testToString_square() throws Exception {
        Facade.PaintSquare paintSquare = new Facade.PaintSquare(0,0,1);
        assertEquals("PAINT_SQUARE 0 0 1",paintSquare.toString());
    }

    @Test
    public void testApply_sqare() throws Exception {
        Facade.PaintSquare paintSquare = new Facade.PaintSquare(0,0,1);
        char[][] grid = {{Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        char[][] expected = {{Facade.MARKER_DONE, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}};
        Facade.Grid grid1 = new Facade.Grid(grid);
        paintSquare.apply(grid1);
        assertArrayEquals(expected,grid1.getCells());

    }
    @Test
    public void testApply_sqare_allgrid() throws Exception {
        Facade.PaintSquare paintSquare = new Facade.PaintSquare(0,0,3);
        char[][] grid = {{Facade.MARKER_EMPTY, Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY, Facade.MARKER_EMPTY}, {Facade.MARKER_EMPTY, Facade.MARKER_EMPTY , Facade.MARKER_EMPTY}};
        char[][] expected = {{Facade.MARKER_DONE, Facade.MARKER_DONE, Facade.MARKER_DONE}, {Facade.MARKER_DONE, Facade.MARKER_DONE, Facade.MARKER_DONE}, {Facade.MARKER_DONE, Facade.MARKER_DONE , Facade.MARKER_DONE}};
        Facade.Grid grid1 = new Facade.Grid(grid);
        paintSquare.apply(grid1);
        assertArrayEquals(expected,grid1.getCells());

    }
}
