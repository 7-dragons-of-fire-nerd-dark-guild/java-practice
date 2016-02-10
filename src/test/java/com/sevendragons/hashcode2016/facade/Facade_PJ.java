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

    public Facade_PJ (Scanner scanner){
        init(scanner);
        resolve();
    }

    public void init(Scanner scanner){
        logger.fine(" --> Computing original matrix [ " +lines+" x "+columns+" ] ... ");
        originalGrid = Facade_PJ_Utils.parseMatrix(scanner);
        logger.fine(" --> Computing original matrix [ " +lines+" x "+columns+" ]: Done!");
        lines = originalGrid.length;
        columns = originalGrid.length;
        originalGrid = new int[lines][columns];
        horizontalGrid = new int[lines][columns];
        verticalSumGrid = new int[lines][columns];
        cornerSumGrid = new int[lines][columns];
        resultGrid = new int[lines][columns];
        instructionsRankGrid = new InstructionSet[lines][columns];
        computeSumMatrix();
    }

    public void resolve(){
        InstructionSet[][] bestInstGrid = Facade_PJ_Utils.generateInstructionsRanking(cornerSumGrid);
        Collection<InstructionSet> instructions = Facade_PJ_Utils.applyBestInstructions(bestInstGrid,
                originalGrid,
                resultGrid,cornerSumGrid[0][0]);
        instructions.addAll(Facade_PJ_Utils.clearCoveredCells(originalGrid, resultGrid));
        Facade_PJ_Utils.printInstructions(instructions);
    }

    private void computeSumMatrix(){
        logger.info("Computing Sum Matrices...");
        logger.fine(" --> Computing horizontal sum matrix [ " +lines+" x "+columns+" ] ... ");
        horizontalGrid = Facade_PJ_Utils.computeSumMatrixHorizontal(originalGrid);
        logger.fine(" --> Computing horizontal sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.fine(" --> Computing vertical sum matrix [ " +lines+" x "+columns+" ] ... ");
        verticalSumGrid = Facade_PJ_Utils.computeSumMatrixVertical(originalGrid);
        logger.fine(" --> Computing vertical sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.fine(" --> Computing corner sum matrix [ " +lines+" x "+columns+" ] ... ");
        cornerSumGrid = Facade_PJ_Utils.computeSumCornerMatrixFromHorizontalSum(horizontalGrid);
        logger.fine(" --> Computing corner sum matrix [ " +lines+" x "+columns+" ]: Done!");
        logger.info("Computing Sum Matrices: Done!");
        totalCellsToCover = cornerSumGrid[0][0];
    }
}
