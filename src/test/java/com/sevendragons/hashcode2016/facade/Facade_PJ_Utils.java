package com.sevendragons.hashcode2016.facade;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by baraud on 10/02/2016.
 */
public class Facade_PJ_Utils {

    private static Logger logger = Logger.getLogger(Facade_PJ_Utils.class.getName());
    private static boolean LOG_COVERAGE_ONLY_CELL_TO_PAINT = true;

    private static enum SUM_TYPE {
        HORIZONTAL,
        VERTICAL
    }

    private static enum CELL_TYPE {
        EMPTY,
        FULL
    }


    private static Comparator<InstructionSet> numberInstructionsComparator = new Comparator<InstructionSet>() {
        @Override
        public int compare(InstructionSet instructionSet, InstructionSet t1) {
            return instructionSet.getNumberOfInstructions() - t1.getNumberOfInstructions();
        }
    };

    private static Comparator<InstructionSet> coveredCellsComparator = new Comparator<InstructionSet>() {
        @Override
        public int compare(InstructionSet instructionSet, InstructionSet t1) {
            return instructionSet.getCells_covered() - t1.getCells_covered();
        }
    };

    ///////////////////////////////
    //---- Matrix operations -----/
    ///////////////////////////////

    public static int[][] computeSumMatrixVertical(int[][] originalMatrix){
        return computeSumMatrixGeneric(originalMatrix, SUM_TYPE.VERTICAL);
    }

    public static int[][] computeSumMatrixHorizontal(int[][] originalMatrix) {
        return computeSumMatrixGeneric(originalMatrix, SUM_TYPE.HORIZONTAL);
    }

    public static int[][] computeSumCornerMatrixFromHorizontalSum(int[][] originalMatrix){
        return computeSumMatrixGeneric(originalMatrix, SUM_TYPE.VERTICAL);
    }

    public static int[][] computeSumCornerMatrixFromVerticalSum(int[][] originalMatrix){
        return computeSumMatrixGeneric(originalMatrix, SUM_TYPE.HORIZONTAL);
    }

    /**
     * Compute a prefix sum for each column (if vertical is true) or line (if vertical is false) of the matrix
     * It compute starting from the end of the array to the beginning of the array
     * @param originalGrid the matrix from where compute the prefix sum
     * @param sumType if SUM_TYPE.VERTICAL, compute a prefix sum for each column, if SUM_TYPE.HORIZONTAL for each line.
     * @return a sum matrix with prefix sum of each line or column of the input matrix
     */
    private static int[][] computeSumMatrixGeneric(int[][] originalGrid, SUM_TYPE sumType){
        int lines = originalGrid.length;
        int columns = originalGrid[0].length;
        int[][] resultGrid = new int[lines][columns];
        if (SUM_TYPE.VERTICAL.equals(sumType)){
            for (int j = columns - 1; j >= 0; j--){
                for (int i = lines - 1; i >= 0; i--){
                    if (i == lines - 1){
                        resultGrid[i][j] = originalGrid[i][j];
                    }
                    else{
                        resultGrid[i][j] = originalGrid[i][j] + resultGrid[i+1][j];
                    }
                }
            }
        }
        else{
            for (int i = lines - 1; i >= 0; i--) {
                for (int j = columns - 1; j >= 0; j--) {
                    if (j == columns - 1) {
                        resultGrid[i][j] = originalGrid[i][j];
                    } else {
                        resultGrid[i][j] = originalGrid[i][j] + resultGrid[i][j + 1];
                    }
                }
            }
        }
        return resultGrid;
    }

    /**
     * Get number of cells to paint (or to leave empty) in the defined area
     * @param i x coordinate of the left top corner of the area
     * @param j y coordinate of the left top corner of the area
     * @param width width of the area to check for empty/nom empty cells
     * @param height height of the area to check for empty/nom empty cells
     * @param cellType compute sum of cell to paint CELL_TYPE.FULL or to leave empty CELL_TYPE.EMPTY
     * @param cornerSumGrid A matrix with prefix Sum that contains the sum of cell to paint to the right bottom border
     * @return the sum of cells to paint (or to not paint)
     */
    private static int getCellsInArea(int i, int j, int width, int height, CELL_TYPE cellType, int[][] cornerSumGrid){
        int cellsInArea = getCellsCountToBottomRight(i, j, cellType, cornerSumGrid)
                - getCellsCountToBottomRight(i + height, j, cellType, cornerSumGrid)
                - getCellsCountToBottomRight(i, j + width,  cellType, cornerSumGrid)
                + getCellsCountToBottomRight(i + height, j + width, cellType, cornerSumGrid);
        return cellsInArea;
    }

    /**
     * Get number of cells to paint (or to leave empty) in the area define by the coordinate to the right bottom corner
     * of the grid
     * @param i x coordinate of the left top corner of the are
     * @param j y coordinate of the left top corner of the area
     * @param cellType compute sum of cell to paint CELL_TYPE.FULL or to leave empty CELL_TYPE.EMPTY
     * @param cornerSumGrid A matrix with prefix Sum that contains the sum of cell to paint to the right bottom border
     * @return the sum of cells to paint (or to not paint)
     */
    private static int getCellsCountToBottomRight(int i, int j, CELL_TYPE cellType, int[][] cornerSumGrid){
        int lines = cornerSumGrid.length;
        int columns = cornerSumGrid[0].length;
        if (i >= lines || j >= columns) {
            return 0;
        }
        if (CELL_TYPE.EMPTY.equals(cellType)){
            return ((lines  - i ) * (columns - j )) - cornerSumGrid[i][j];
        }
        else{
            return cornerSumGrid[i][j];
        }
    }

    ///////////////////////////////////
    //------- Parsing Input ----------/
    ///////////////////////////////////

    /**
     * Parse input and produce a matrix with
     * 0 for cells to leave empty and 1 for cells to paint
     * @param scanner Entry for input
     *                format is
     *                rows column
     *                matrix (. = empty, # = to paint)
     *                example:
     *                2 3
     *                .#.
     *                #.#
     * @return Matrix of cells to paint (1) or leave empty (0)
     */
    public static int[][] parseMatrix(Scanner scanner) {
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        scanner.nextLine();
        char[][] cells = new char[rows][columns];
        int[][] result = new int[rows][columns];
        int row = 0;
        while (scanner.hasNextLine()) {
            cells[row] = scanner.nextLine().toCharArray();
            for (int j=0; j< columns; j++){
                if ('#' == cells[row][j]){
                    result[row][j] = 1;
                }else{
                    result[row][j] = 0;
                }
            }
            row++;
        }
        return result;
    }

    //////////////////////////////////
    //------- Applying output -------/
    //////////////////////////////////

    /**
     * Apply the best scenario starting from left top corner of the Grid
     * @param bestInstructionsGrids the precomputed set of best instructions per coordinates
     * @param originalGrid the grid set as input that we want to reproduce
     * @param resultGrid the grid where we are gonna apply our instructions
     * @return the list of needed instruction to fill the rest of the matrix to the bottom right end
     */
    public static Collection<InstructionSet> applyBestInstructions(InstructionSet[][] bestInstructionsGrids,
                                                             int[][] originalGrid, int[][] resultGrid, int totalCellsToCover){
        logger.info("Applying Instructions...");
        SortedSet<InstructionSet> instructions = new TreeSet<>(coveredCellsComparator);
        instructions.addAll(applyBestInstruction(0,0, bestInstructionsGrids));
        int cellsCovered = 0;
        for (InstructionSet instruction : instructions) {
            logger.fine(" --> Applying " + instruction);
            cellsCovered += applyInstruction(instruction, originalGrid, resultGrid);
            logger.fine(" --> Covering rate : " + 100 * cellsCovered / totalCellsToCover + "%");
        }
        logger.info("Applying Instructions: Done!");
        return instructions;
    }

    /**
     * Apply the best scenario starting from coordinates provided
     * @param i x coordinates of first instructions
     * @param j y coordinates of first instructions
     * @param bestInstructionsGrids the precomputed set of best instructions per coordinates
     * @return the list of needed instruction to fill the rest of the matrix to the bottom right end
     */
    private static SortedSet<InstructionSet> applyBestInstruction(int i, int j, InstructionSet[][] bestInstructionsGrids){
        int lines = bestInstructionsGrids.length;
        int columns = bestInstructionsGrids[0].length;
        SortedSet<InstructionSet> toReturn =  new TreeSet<>(coveredCellsComparator);
        InstructionSet instruction = bestInstructionsGrids[i][j];
        toReturn.add(instruction);
        if (j + instruction.getWidth() < columns){
            toReturn.addAll(applyBestInstruction(i, j + instruction.getWidth(), bestInstructionsGrids));
        }
        if (i + instruction.getHeigth() < lines){
            toReturn.addAll(applyBestInstruction(i + instruction.getHeigth(), j, bestInstructionsGrids));
        }
        return toReturn;
    }

    /**
     * Apply instruction to the result Grid
     * @param instruction
     * @param originalGrid the final Grid we want to look like
     * @param resultGrid the Grid where to apply instructions
     * @return the number of cells that the instructions has affected
     */
    public static int applyInstruction(InstructionSet instruction, int[][] originalGrid, int[][] resultGrid){
        int cellCovered = 0;
        if (!InstructionSet.TYPE.NOTHING.equals(instruction.getType())){
            for (int i = instruction.getLine(); i < instruction.getLine() + instruction.getHeigth(); i++){
                for (int j = instruction.getColumn(); j < instruction.getColumn() + instruction.getWidth(); j++){
                    if (resultGrid[i][j] == 0 && originalGrid[i][j] == 1){
                        if (LOG_COVERAGE_ONLY_CELL_TO_PAINT){
                            cellCovered ++;
                        }
                    }
                    resultGrid[i][j] = 1;
                    if (!LOG_COVERAGE_ONLY_CELL_TO_PAINT){
                        cellCovered ++;
                    }
                }
            }
        }
        return cellCovered;
    }

    /**
     * Clear the cells that have been paint and should been clean
     * @param originalGrid
     * @param resultGrid
     * @return the list of Clear instructions to get the same grid as the original
     */
    public static Collection<InstructionSet> clearCoveredCells(int[][] originalGrid, int[][] resultGrid){
        int lines = originalGrid.length;
        int columns = originalGrid[0].length;
        List<InstructionSet> clearInstructions = new ArrayList<>();
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                if (originalGrid[i][j] == 0 && resultGrid[i][j] == 1){
                    logger.finer(" --> Clearing ["+i+"]["+j+")");
                    InstructionSet clearInst = new InstructionSet(InstructionSet.TYPE.CLEAR, 1, i, j, 1, 1);
                    clearInstructions.add(clearInst);
                    applyInstruction(clearInst, originalGrid, resultGrid);
                }
            }
        }
        return clearInstructions;
    }

    //TODO PJ
    public static void printInstructions(Collection<InstructionSet> instructionSets){
        for (InstructionSet instruction : instructionSets){
            logger.info(instruction.toString());
        }
    }


    ///////////////////////////////////
    //-------- Compute Solution ------/
    ///////////////////////////////////

    /**
     * Generate the best Instructions to do for each coordinates to fill the matrix to the bottom right.
     * @param cornerSumGrid the prefix sum matrix of all cells to paint to the bottom right of the map
     * @return a optimized instruction for each coordinates
     */
    public static InstructionSet[][] generateInstructionsRanking(int[][] cornerSumGrid){
        int lines = cornerSumGrid.length;
        int columns = cornerSumGrid[0].length;
        InstructionSet[][] toReturn = new InstructionSet[lines][columns];
        logger.info("Computing Instructions Ranking...");
        for (int i = lines - 1; i >= 0; i--){
            for (int j = columns - 1; j >= 0; j--){
                logger.fine(" --> Computing ["+i+","+j+"]...");
                InstructionSet instructionSet = chooseBestInstruction( i, j, cornerSumGrid, toReturn);
                toReturn[i][j] = instructionSet;
                logger.fine(" --> [i,j] "+instructionSet.getType()+" size "+instructionSet.getSize());
            }
        }
        return toReturn;
    }

    /**
     * Compute the best size and type  of instruction to fill the matrix for coordinate to the bottom right
     * of the matrix with a minimum of instruction
     * @param i x coordinate of top left area to fill with instructions
     * @param j y coordinate of top left area to fill with instructions
     * @param cornerSumGrid prefix sum matrix with number of cells to paint to the bottom right of the matrix
     * @param bestRankedInstructionGrid already computed best instructions for each coordinates inside the area
     * @return the instruction  with the best size and type to minimize number of instructions
     */
    private static InstructionSet chooseBestInstruction(int i, int j, int[][] cornerSumGrid,
                                                 InstructionSet[][] bestRankedInstructionGrid){
        SortedSet<InstructionSet> bestInstructions = new TreeSet<>(numberInstructionsComparator);
        for (InstructionSet.TYPE type : InstructionSet.TYPE.values()){
            if (!InstructionSet.TYPE.CLEAR.equals(type)){
                InstructionSet inst = computeBestInstruction(i, j, type, cornerSumGrid, bestRankedInstructionGrid);
                if (inst != null){
                    bestInstructions.add(inst);
                }
            }
        }
        return bestInstructions.first();
    }


    /**
     * Compute the best size for given type of instruction to fill the matrix for coordinate to the bottom right
     * of the matrix with a minimum of instruction
     * @param i x coordinate of top left area to fill with instructions
     * @param j y coordinate of top left area to fill with instructions
     * @param type type of instruction we want to compute on this coordinate
     * @param cornerSumGrid prefix sum matrix with number of cells to paint to the bottom right of the matrix
     * @param bestRankedInstructionGrid already computed best instructions for each coordinates inside the area
     * @return the instruction type with the best size to minimize number of instructions
     */
    public static InstructionSet computeBestInstruction(int i, int j, InstructionSet.TYPE type, int[][] cornerSumGrid,
                                                  InstructionSet[][] bestRankedInstructionGrid){
        SortedSet<InstructionSet> computedInstructions = new TreeSet<>(numberInstructionsComparator);
        int lines = cornerSumGrid.length;
        int columns = cornerSumGrid[0].length;
        int incrementation = InstructionSet.TYPE.SQUARE.equals(type) ? 2 : 1;
        int maxSize = 0;
        switch (type){
            case SQUARE:
                maxSize = Math.min(columns - j, lines - i);
                break;
            case NOTHING:
                maxSize = 1;
                break;
            case LINE_HORIZONTAL:
                maxSize =  columns - j;
                break;
            case LINE_VERTICAL:
                maxSize = lines - i;
                break;
            case CLEAR:
                maxSize = 1;
                break;
        }
        for (int size = 1; size <= maxSize; size += incrementation){
            int width = getInstructionWidth(type, size);
            int height = getInstructionHeight(type, size);
            int numberOfEmptyCells = getCellsInArea(i, j, width, height, CELL_TYPE.EMPTY, cornerSumGrid);
            int numberOfCoveredCells = getCellsInArea(i, j, width, height, CELL_TYPE.FULL, cornerSumGrid);
            InstructionSet instructionsRight = getBestInstruction(i, j + width, bestRankedInstructionGrid);
            InstructionSet instructionsBottom = getBestInstruction(i + height, j, bestRankedInstructionGrid);
            //InstructionSet instructionsUnion = getBestInstruction(i + width, j + height, bestRankedInstructionGrid);
            int instructionsRank = 0;
            if (!InstructionSet.TYPE.NOTHING.equals(type)){
                instructionsRank += 1;
                instructionsRank += numberOfEmptyCells;
            }
            instructionsRank += instructionsRight != null ? instructionsRight.getNumberOfInstructions() : 0;
            instructionsRank += instructionsBottom != null ? instructionsBottom.getNumberOfInstructions() : 0;
            //instructionsRank -= instructionsUnion != null ? instructionsUnion.getNumberOfInstructions() : 0;

            if (InstructionSet.TYPE.NOTHING.equals(type)){
                if (numberOfCoveredCells == 0 ){
                    computedInstructions.add(new InstructionSet(type, size, i, j, numberOfCoveredCells, instructionsRank));
                }
            }
            else{
                computedInstructions.add(new InstructionSet(type, size, i, j, numberOfCoveredCells, instructionsRank));
            }
        }
        if (computedInstructions.isEmpty()){
            return null;
        }
        return computedInstructions.first();
    }

    private static int getInstructionWidth(InstructionSet.TYPE type, int size){
        if (InstructionSet.TYPE.SQUARE.equals(type) || InstructionSet.TYPE.LINE_HORIZONTAL.equals(type)){
            return size;
        }
        return 1;
    }

    private static int getInstructionHeight(InstructionSet.TYPE type, int size){
        if (InstructionSet.TYPE.SQUARE.equals(type) || InstructionSet.TYPE.LINE_VERTICAL.equals(type)){
            return  size;
        }
        return 1;
    }

    private static InstructionSet getBestInstruction(int i, int j, InstructionSet[][] instructionsRankGrid){
        int lines = instructionsRankGrid.length;
        int columns = instructionsRankGrid[0].length;
        if ( i >= lines || j >= columns ){
            return null;
        }
        return instructionsRankGrid[i][j];
    }

}
