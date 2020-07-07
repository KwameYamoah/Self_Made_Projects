package sample;

import java.util.ArrayList;

public class VectorGroup {
    private ArrayList<Vector> vectors;
    public VectorGroup(ArrayList<Vector> vectors){
        if(vectors != null){
            this.vectors = vectors;
        }
        else{
            vectors = new ArrayList<>();
        }
    }

    public void addVector(Vector vector){
        if(vector != null){
            vectors.add(vector);

        }else{
            System.out.println("Vector is not adding, ");
        }
    }

    public ArrayList<Vector> getVectors() {
        return vectors;
    }
}
