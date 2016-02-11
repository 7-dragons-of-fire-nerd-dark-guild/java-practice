package com.sevendragons.hashcode2016.facade;

import org.junit.Test;

import java.util.*;

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

    @Test
    public void testInstructionsApplications(){
        int[][] grid = new  int[5][3];
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        InstructionSet instruction1 = new InstructionSet(InstructionSet.TYPE.LINE_VERTICAL, 5, 0, 1, 5, 1);
        InstructionSet instruction2 = new InstructionSet(InstructionSet.TYPE.SQUARE, 3, 2, 0, 9, 1);
        Facade_PJ_Utils.applyInstruction(instruction1, originalGrid, grid);
        Facade_PJ_Utils.applyInstruction(instruction2, originalGrid, grid);
        assertArrayEquals(originalGrid[0],grid[0]);
        assertArrayEquals(originalGrid[1],grid[1]);
        assertArrayEquals(originalGrid[2],grid[2]);
    }

    @Test
    public void testBestSquareSize(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] bestRankingInstructions = new InstructionSet[5][3];
        bestRankingInstructions[4][2] = Facade_PJ_Utils.computeBestInstruction(4,2, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        assertEquals(1, bestRankingInstructions[4][2].getSize());
        bestRankingInstructions[4][1] = Facade_PJ_Utils.computeBestInstruction(4,1, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[4][0] = Facade_PJ_Utils.computeBestInstruction(4,0, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[3][2] = Facade_PJ_Utils.computeBestInstruction(3,2, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[3][1] = Facade_PJ_Utils.computeBestInstruction(3,1, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[3][0] = Facade_PJ_Utils.computeBestInstruction(3,0, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[2][2] = Facade_PJ_Utils.computeBestInstruction(2,2, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[2][1] = Facade_PJ_Utils.computeBestInstruction(2,1, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        bestRankingInstructions[2][0] = Facade_PJ_Utils.computeBestInstruction(2,0, InstructionSet.TYPE.SQUARE, sumGrid, bestRankingInstructions);
        assertEquals(3, bestRankingInstructions[2][0].getSize());
    }

    @Test
    public void testInstructionsRankingMatrix_4_2(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.SQUARE, instructions[2][0].getType());
        assertEquals(1, instructions[4][2].getSize());
        assertEquals(1, instructions[4][2].getCells_covered());
        assertEquals(1, instructions[4][2].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_4_1(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_HORIZONTAL, instructions[4][1].getType());
        assertEquals(2, instructions[4][1].getSize());
        assertEquals(2, instructions[4][1].getCells_covered());
        assertEquals(1, instructions[4][1].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_4_0(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_HORIZONTAL, instructions[4][0].getType());
        assertEquals(3, instructions[4][0].getSize());
        assertEquals(3, instructions[4][0].getCells_covered());
        assertEquals(1, instructions[4][0].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_3_2(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[3][2].getType());
        assertEquals(2, instructions[3][2].getSize());
        assertEquals(2, instructions[3][2].getCells_covered());
        assertEquals(1, instructions[3][2].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_3_1(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_HORIZONTAL, instructions[3][1].getType());
        assertEquals(2, instructions[3][1].getSize());
        assertEquals(2, instructions[3][1].getCells_covered());
        assertEquals(2, instructions[3][1].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_3_0(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_HORIZONTAL, instructions[3][0].getType());
        assertEquals(3, instructions[3][0].getSize());
        assertEquals(3, instructions[3][0].getCells_covered());
        assertEquals(2, instructions[3][0].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_2_2(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[2][2].getType());
        assertEquals(3, instructions[2][2].getSize());
        assertEquals(3, instructions[2][2].getCells_covered());
        assertEquals(1, instructions[2][2].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_2_1(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[2][1].getType());
        assertEquals(3, instructions[2][1].getSize());
        assertEquals(3, instructions[2][1].getCells_covered());
        assertEquals(2, instructions[2][1].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_2_0(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.SQUARE, instructions[2][0].getType());
        assertEquals(3, instructions[2][0].getSize());
        assertEquals(9, instructions[2][0].getCells_covered());
        assertEquals(1, instructions[2][0].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_1_2(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.NOTHING, instructions[1][2].getType());
        assertEquals(1, instructions[1][2].getSize());
        assertEquals(0, instructions[1][2].getCells_covered());
        assertEquals(1, instructions[1][2].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_1_1(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[1][1].getType());
        assertEquals(4, instructions[1][1].getSize());
        assertEquals(4, instructions[1][1].getCells_covered());
        assertEquals(2, instructions[1][1].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_1_0(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.NOTHING, instructions[1][0].getType());
        assertEquals(1, instructions[1][0].getSize());
        assertEquals(0, instructions[1][0].getCells_covered());
        assertEquals(3, instructions[1][0].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_0_2(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.NOTHING, instructions[0][2].getType());
        assertEquals(1, instructions[0][2].getSize());
        assertEquals(0, instructions[0][2].getCells_covered());
        assertEquals(1, instructions[0][2].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_0_1(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[0][1].getType());
        assertEquals(5, instructions[0][1].getSize());
        assertEquals(5, instructions[0][1].getCells_covered());
        assertEquals(2, instructions[0][1].getNumberOfInstructions());
    }

    @Test
    public void testInstructionsRankingMatrix_0_0(){
        int[][] originalGrid = {
                {0,1,0},
                {0,1,0},
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
        assertEquals(InstructionSet.TYPE.LINE_VERTICAL, instructions[0][0].getType());
        assertEquals(5, instructions[0][0].getSize());
        assertEquals(3, instructions[0][0].getCells_covered());
        assertEquals(5, instructions[0][0].getNumberOfInstructions());
    }

//    @Test
//    public void testApplyBestInstructions(){
//        int[][] originalGrid = {
//                {0,1,0},
//                {0,1,0},
//                {1,1,1},
//                {1,1,1},
//                {1,1,1}
//        };
//        int[][] resultGrid = new int[5][3];
//        int[][] sumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromVerticalSum(Facade_PJ_Utils.computeSumMatrixVertical(originalGrid));
//        InstructionSet[][] instructions = Facade_PJ_Utils.generateInstructionsRanking(sumGrid);
//        Collection<InstructionSet> instructionSets = Facade_PJ_Utils.applyBestInstructions(instructions, originalGrid, resultGrid, sumGrid[0][0]);
//    }



//    @Test
//    public void testSolution() {
//        Facade_PJ facade = new Facade_PJ(new Scanner(exampleGrid1));
//        facade.resolve();
//        assertArrayEquals(facade.getOriginalGrid()[0], facade.getResultGrid()[0]);
//    }


}
