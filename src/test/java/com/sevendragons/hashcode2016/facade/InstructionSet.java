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
    private int line;
    private int column;
    private int cells_covered;
    private int numberOfInstructions;

    public InstructionSet(TYPE type, int size, int line, int column, int cells_covered, int numberOfInstructions) {
        this.type = type;
        this.size = size;
        this.line = line;
        this.column = column;
        this.cells_covered = cells_covered;
        this.numberOfInstructions = numberOfInstructions;
    }

    public TYPE getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
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

    @Override
    public String toString() {
        switch (type){
            case SQUARE:
                return "PAINT_SQUARE "+(line + (size - 1) / 2 )+" "+(column+ (size - 1) / 2 )+" "+ (size - 1) / 2;
            case LINE_HORIZONTAL:
                return "PAINT_LINE "+ line+" "+column+" "+ line+" "+ (column + size - 1);
            case LINE_VERTICAL:
                return "PAINT_LINE "+line+" "+ column +" "+ (line + size - 1) +" "+column;
            case NOTHING:
                return "NOTHING";
            case CLEAR:
                return "ERASE_CELL "+line+" "+column;
        }
        return "";
    }
}
