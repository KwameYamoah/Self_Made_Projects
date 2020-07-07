package main;

import java.util.ArrayList;

public class VectorGroup {
    private ArrayList<Line> lines;
    public VectorGroup(ArrayList<Line> lines){
        if(lines != null){
            this.lines = lines;
        }
        else{
            lines = new ArrayList<>();
        }
    }

    public void addVector(Line line){
        if(line != null){
            lines.add(line);

        }else{
            System.out.println("Vector is not adding, ");
        }
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}
