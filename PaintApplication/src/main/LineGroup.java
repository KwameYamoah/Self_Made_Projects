package main;

import java.util.ArrayList;

public class LineGroup {
    private ArrayList<StraightLine> straightLines;
    public LineGroup(ArrayList<StraightLine> straightLines){
        if(straightLines != null){
            this.straightLines = straightLines;
        }
        else{
            straightLines = new ArrayList<>();
        }
    }

    public void addVector(StraightLine straightLine){
        if(straightLine != null){
            straightLines.add(straightLine);

        }else{
            System.out.println("Vector is not adding, ");
        }
    }

    public ArrayList<StraightLine> getStraightLines() {
        return straightLines;
    }
}
