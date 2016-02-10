package com.sevendragons.hashcode2016.facade;

/**
 * Created by baraud on 09/02/2016.
 */
public class InstructionSet  {

    public static enum TYPE {
        SQUARE,
        LINE_HORIZONTAL,
        LINE_VERTICAL,
        CLEAR,
        NOTHING
    }

    private TYPE type;
    private int size;
    private int xCoord;
    private int yCoord;
    private int cells_covered;
    private int numberOfInstructions;

    public InstructionSet(TYPE type, int size, int xCoord, int yCoord, int cells_covered, int numberOfInstructions) {
        this.type = type;
        this.size = size;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cells_covered = cells_covered;
        this.numberOfInstructions = numberOfInstructions;
    }

    public TYPE getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getCells_covered() {
        return cells_covered;
    }

    public int getNumberOfInstructions() {
        return numberOfInstructions;
    }

    public int getWidth(){
        if (TYPE.SQUARE.equals(this.type) || TYPE.LINE_HORIZONTAL.equals(this.type)){
            return size;
        }
        return 1;
    }

    public int getHeigth(){
        if (TYPE.SQUARE.equals(this.type) || TYPE.LINE_VERTICAL.equals(this.type)){
            return  size;
        }
        return 1;
    }
}
