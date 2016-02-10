package com.sevendragons.hashcode2016.facade;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by baraud on 09/02/2016.
 */
public class Facade_PJ {

    private Facade.Grid providedGrid;
    private int[][] originalGrid;
    private int[][] resultGrid;
    private int[][] horizontalGrid;
    private int[][] verticalSumGrid;
    private int[][] cornerSumGrid;
    private InstructionSet[][] instructionsRankGrid;
    private int lines;
    private int columns;
    private Logger logger = Logger.getLogger(Facade_PJ.class.getName());
    private int originalCellsCovered = 0;
    private int cellsCovered = 0;
    private int totalCellsToCover = 0;

    private Comparator<InstructionSet> numberInstructionsComparator = new Comparator<InstructionSet>() {
        @Override
        public int compare(InstructionSet instructionSet, InstructionSet t1) {
            return instructionSet.getNumberOfInstructions() - t1.getNumberOfInstructions();
        }
    };

    private Comparator<InstructionSet> coveredCellsComparator = new Comparator<InstructionSet>() {
        @Override
        public int compare(InstructionSet instructionSet, InstructionSet t1) {
            return instructionSet.getCells_covered() - t1.getCells_covered();
        }
    };

    public Facade_PJ (Facade.Grid grid){
        init(grid);
        resolve();
    }

    public void init(Facade.Grid grid){
        lines = grid.cells.length;
        columns = grid.cells.length;
        originalGrid = new int[lines][columns];
        horizontalGrid = new int[lines][columns];
        verticalSumGrid = new int[lines][columns];
        cornerSumGrid = new int[lines][columns];
        resultGrid = new int[lines][columns];
        instructionsRankGrid = new InstructionSet[lines][columns];
        computeSumMatrix();
    }

    public void resolve(){
        generateInstructionsRanking();
        Collection<InstructionSet> instructions = applyBestInstructions();
        instructions.addAll(clearCoveredCells());
        printInstructions(instructions);
    }

    private void computeSumMatrix(){
        logger.info("Computing Sum Matrices...");
        logger.fine(" --> Computing original matrix [ " +lines+" x "+columns+" ] ... ");
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < lines; j++){
                originalGrid[i][j] = providedGrid.cells[i][j];
            }
        }
        logger.fine(" --> Computing original matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.fine(" --> Computing horizontal sum matrix [ " +lines+" x "+columns+" ] ... ");
        for (int i = lines - 1; i >= 0; i--){
            for (int j = columns; j >= 0; j--){
                if (j == columns - 1){
                    horizontalGrid[i][j] = originalGrid[i][j];
                }
                else{
                    horizontalGrid[i][j] = originalGrid[i][j] + horizontalGrid[i][j+1];
                }
            }
        }
        logger.fine(" --> Computing horizontal sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.fine(" --> Computing vertical sum matrix [ " +lines+" x "+columns+" ] ... ");
        for (int j = columns; j >= 0; j--){
            for (int i = lines - 1; i >= 0; i--){
                if (i == lines - 1){
                    verticalSumGrid[i][j] = originalGrid[i][j];
                }
                else{
                    verticalSumGrid[i][j] = originalGrid[i][j] + verticalSumGrid[i+1][j];
                }
            }
        }
        logger.fine(" --> Computing vertical sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.fine(" --> Computing corner sum matrix [ " +lines+" x "+columns+" ] ... ");
        for (int i = lines - 1; i >= 0; i--){
            for (int j = columns; j >= 0; j--){
                if (i == lines - 1){
                    cornerSumGrid[i][j] = horizontalGrid[i][j];
                }
                else{
                    cornerSumGrid[i][j] = horizontalGrid[i][j] + cornerSumGrid[i+1][j];
                }
            }
        }
        logger.fine(" --> Computing corner sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.info("Computing Sum Matrices: Done!");
        totalCellsToCover = cornerSumGrid[0][0];
    }

    private void generateInstructionsRanking(){
        logger.info("Computing Instructions Ranking...");
        for (int i = lines - 1; i >= 0; i--){
            for (int j = columns; j >= 0; j--){
                logger.fine(" --> Computing ["+i+","+j+"]...");
                InstructionSet instructionSet = computeBestInstruction(i,j);
                instructionsRankGrid[i][j] = instructionSet;
                logger.fine(" --> [i,j] "+instructionSet.getType()+" size "+instructionSet.getSize());
            }
        }
    }

    private InstructionSet computeBestInstruction(int i, int j){
        SortedSet<InstructionSet> bestInstructions = new TreeSet<>(numberInstructionsComparator);
        InstructionSet squareInst = getBestSquareInstruction(i, j);
        InstructionSet horizontalLineInst = getBestHorizontalLineInstruction(i, j);
        InstructionSet verticalLineInst = getBestVerticalLineInstruction( i, j);
        InstructionSet nothingInst = getNothingInstruction(i, j);
        if (squareInst != null){
            bestInstructions.add(squareInst);
        }
        if (horizontalLineInst != null){
            bestInstructions.add(horizontalLineInst);
        }
        if (verticalLineInst != null){
            bestInstructions.add(verticalLineInst);
        }
        if (nothingInst != null){
            bestInstructions.add(nothingInst);
        }
        return bestInstructions.first();
    }

    private InstructionSet getBestSquareInstruction(int i, int j){
        SortedSet<InstructionSet> computedSquaresInstructions = new TreeSet<>(numberInstructionsComparator);
        for (int size = 1; size <= Math.min(columns - j, lines - i); size += 2){
            int numberOfEmptyCells = getEmptyCellsCount(i,j)
                    - getEmptyCellsCount(i+size, j)
                    - getEmptyCellsCount(i,j+size)
                    + getEmptyCellsCount(i+size, j+size);

            int numberOfCoveredCells = getCoveredCellsCount(i,j)
                    - getCoveredCellsCount(i+size, j)
                    - getCoveredCellsCount(i, j+size)
                    + getCoveredCellsCount(i+size, j+size);

            InstructionSet instructionsRight = getBestInstruction(i, j + size);
            InstructionSet instructionsBottom = getBestInstruction(i + size, j);
            InstructionSet instructionsUnion = getBestInstruction(i + size, j + size);

            int instructionsRank = 1;
            instructionsRank += numberOfEmptyCells;
            instructionsRank += instructionsRight != null ? instructionsRight.getNumberOfInstructions() : 0;
            instructionsRank += instructionsBottom != null ? instructionsBottom.getNumberOfInstructions() : 0;
            instructionsRank -= instructionsUnion != null ? instructionsUnion.getNumberOfInstructions() : 0;

            computedSquaresInstructions.add(new InstructionSet(InstructionSet.TYPE.SQUARE, size, i, j, numberOfCoveredCells, instructionsRank));
        }
        return computedSquaresInstructions.first();
    }

    private InstructionSet getBestHorizontalLineInstruction(int i, int j){
        SortedSet<InstructionSet> computedHorizontalLinesInstructions = new TreeSet<>(numberInstructionsComparator);
        for (int size = 1; size <= columns - j; size ++){
            int numberOfEmptyCells = getEmptyCellsCount(i,j)
                    - getEmptyCellsCount(i+1, j)
                    - getEmptyCellsCount(i,j+size)
                    + getEmptyCellsCount(i+1, j+size);

            int numberOfCoveredCells = getCoveredCellsCount(i,j)
                    - getCoveredCellsCount(i+1, j)
                    - getCoveredCellsCount(i, j+size)
                    + getCoveredCellsCount(i+1, j+size);

            InstructionSet instructionsRight = getBestInstruction(i, j + size);
            InstructionSet instructionsBottom = getBestInstruction(i + 1, j);
            InstructionSet instructionsUnion = getBestInstruction(i + 1, j + size);

            int instructionsRank = 1;
            instructionsRank += numberOfEmptyCells;
            instructionsRank += instructionsRight != null ? instructionsRight.getNumberOfInstructions() : 0;
            instructionsRank += instructionsBottom != null ? instructionsBottom.getNumberOfInstructions() : 0;
            instructionsRank -= instructionsUnion != null ? instructionsUnion.getNumberOfInstructions() : 0;

            computedHorizontalLinesInstructions.add(new InstructionSet(InstructionSet.TYPE.LINE_HORIZONTAL, size, i, j, numberOfCoveredCells, instructionsRank));
        }
        return computedHorizontalLinesInstructions.first();
    }

    private InstructionSet getBestVerticalLineInstruction(int i, int j){
        SortedSet<InstructionSet> computedVerticalLinesInstructions = new TreeSet<>(numberInstructionsComparator);
        for (int size = 1; size <= columns - j; size ++){
            int numberOfEmptyCells = getEmptyCellsCount(i,j)
                    - getEmptyCellsCount(i+size, j)
                    - getEmptyCellsCount(i,j+1)
                    + getEmptyCellsCount(i+size, j+1);

            int numberOfCoveredCells = getCoveredCellsCount(i,j)
                    - getCoveredCellsCount(i+size, j)
                    - getCoveredCellsCount(i, j+1)
                    + getCoveredCellsCount(i+size, j+1);

            InstructionSet instructionsRight = getBestInstruction(i, j + 1);
            InstructionSet instructionsBottom = getBestInstruction(i + size, j);
            InstructionSet instructionsUnion = getBestInstruction(i + size, j + 1);

            int instructionsRank = 1;
            instructionsRank += numberOfEmptyCells;
            instructionsRank += instructionsRight != null ? instructionsRight.getNumberOfInstructions() : 0;
            instructionsRank += instructionsBottom != null ? instructionsBottom.getNumberOfInstructions() : 0;
            instructionsRank -= instructionsUnion != null ? instructionsUnion.getNumberOfInstructions() : 0;

            computedVerticalLinesInstructions.add(new InstructionSet(InstructionSet.TYPE.LINE_VERTICAL, size, i, j, numberOfCoveredCells, instructionsRank));
        }
        return computedVerticalLinesInstructions.first();
    }

    private InstructionSet getNothingInstruction(int i, int j){
        if (originalGrid[i][j] == 0){

            int numberOfCoveredCells = getCoveredCellsCount(i,j)
                    - getCoveredCellsCount(i+1, j)
                    - getCoveredCellsCount(i, j+1)
                    + getCoveredCellsCount(i+1, j+1);

            InstructionSet instructionsRight = getBestInstruction(i, j + 1);
            InstructionSet instructionsBottom = getBestInstruction(i + 1, j);
            InstructionSet instructionsUnion = getBestInstruction(i + 1, j + 1);

            int instructionsRank = 0;
            instructionsRank += instructionsRight != null ? instructionsRight.getNumberOfInstructions() : 0;
            instructionsRank += instructionsBottom != null ? instructionsBottom.getNumberOfInstructions() : 0;
            instructionsRank -= instructionsUnion != null ? instructionsUnion.getNumberOfInstructions() : 0;

            return new InstructionSet(InstructionSet.TYPE.NOTHING, 1, i, j, numberOfCoveredCells, instructionsRank);
        }

        return null;
    }

    private Collection<InstructionSet> clearCoveredCells(){
        List<InstructionSet> clearInstructions = new ArrayList<>();
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                if (originalGrid[i][j] == 0 && resultGrid[i][j] == 1){
                    logger.finer(" --> Clearing ["+i+"]["+j+")");
                    InstructionSet clearInst = new InstructionSet(InstructionSet.TYPE.CLEAR, 1, i, j, 1, 1);
                    clearInstructions.add(clearInst);
                    applyInstruction(clearInst);
                }
            }
        }
        return clearInstructions;
    }

    private Collection<InstructionSet> applyBestInstructions(){
        logger.info("Applying Instructions...");
        SortedSet<InstructionSet> instructions = new TreeSet<>(coveredCellsComparator);
        instructions.addAll(applyBestInstruction(0,0));
        for (InstructionSet instruction : instructions) {
            logger.fine(" --> Applying " + instruction);
            applyInstruction(instruction);
            logger.fine(" --> Covering rate : " + 100 * cellsCovered / totalCellsToCover + "% " +
                    "( final covering: " + 100 * originalCellsCovered / totalCellsToCover + "% )");
        }
        logger.info("Applying Instructions: Done!");
        return instructions;
    }

    private void applyInstruction(InstructionSet instruction){
        if (!InstructionSet.TYPE.NOTHING.equals(instruction.getType())){
            for (int i = instruction.getyCoord(); i < instruction.getyCoord() + instruction.getHeigth(); i++){
                for (int j = instruction.getxCoord(); j < instruction.getxCoord() + instruction.getWidth(); i++){
                    if (resultGrid[i][j] == 0 && originalGrid[i][j] == 1){
                        originalCellsCovered ++;
                    }
                    resultGrid[i][j] = 1;
                    cellsCovered ++;
                }
            }
        }
    }

    private SortedSet<InstructionSet> applyBestInstruction(int i, int j){
        SortedSet<InstructionSet> toReturn =  new TreeSet<>(coveredCellsComparator);
        InstructionSet instruction = instructionsRankGrid[i][j];
        toReturn.add(instruction);
        if (j + instruction.getWidth() < columns){
            toReturn.addAll(applyBestInstruction(i, j + instruction.getWidth()));
        }
        if (i + instruction.getHeigth() < lines){
            toReturn.addAll(applyBestInstruction(i + instruction.getHeigth(), j));
        }
        return toReturn;
    }


    private InstructionSet getBestInstruction(int i, int j){
        if ( i >= lines || j >= columns ){
            return null;
        }
        return instructionsRankGrid[i][j];
    }

    private int getEmptyCellsCount(int i, int j){
        if (i >= lines || j >= lines) {
            return 0;
        }
        return ((lines  - i ) * (columns - j )) - cornerSumGrid[i][j];
    }

    private int getCoveredCellsCount(int i, int j){
        if (i >= lines || j >= lines) {
            return 0;
        }
        return cornerSumGrid[i][j];
    }

    public void printInstructions(Collection<InstructionSet> instructionSets){
        for (InstructionSet instruction : instructionSets){
            logger.info(instruction.toString());
        }
    }
}
