import java.util.*;
import java.lang.Math;

public class FarthestInsert{
    public static int FarthestNode(double[] tempRow, boolean[] onPath) {

        int maxIndex = 0;
        double max = Double.NEGATIVE_INFINITY;

        for (int i=0; i < tempRow.length; i++)
        {
            if (tempRow[i]>max && onPath[i] == false) {
                max = tempRow[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int SelectNode(List<Integer> path, double[][] matrix) {
        int dim = matrix.length;
        boolean[] onPath = new boolean[dim];
        double max = Double.NEGATIVE_INFINITY;
        int to=0, from=0;

        //initialize all nodes to be not on path
        for (int i=0; i<dim; i++) {
            onPath[i] = false;
        }

        //set node on path to be true
        for (int i=0; i<path.size(); i++) {
            onPath[path.get(i)] = true;
        }

        for (int i=0; i<path.size(); i++) {
            double[] tempRow = matrix[path.get(i)];
            int tempMax = FarthestNode(tempRow, onPath);
            if (tempRow[tempMax] > max) {
                max = tempRow[tempMax];
                to = tempMax;
                from = path.get(i);
            }
        }
        return to;
    }

    public static int InsertNode(List<Integer> path, double[][] matrix, int k) {

        double min = Double.POSITIVE_INFINITY;
        double temp = Double.POSITIVE_INFINITY;
        int from, to;
        int insert = path.get(0);
        int dim = path.size()-1;
        for (int i=0; i<dim; i++) {
            from = path.get(i);
            to = path.get(i+1);
            // double p = (matrix[from][k]+matrix[k][to]+matrix[from][to])/2;
            // double dist = Math.pow(p*(p-matrix[from][k])*(p-matrix[k][to])*(p-matrix[from][to]),0.5)*2/(matrix[from][to]);
            double dist = matrix[from][k]+matrix[k][to] - matrix[from][to];
            if (dist < min) {
                min = dist;
                insert = i+1;
            }
        }
        return insert;
    }


    public static void FarthestInsertion(city City){

        double[][] distances = City.getDistances();
        List<Integer> path = new ArrayList<>();

        // long startTime = System.nanoTime();
        path.add(0);
        double[] row = distances[0];

        //Find the farthest node to node 0
        int node = 0;
        double max = Double.NEGATIVE_INFINITY;
        for(int i=1; i<row.length; i++){
            if(row[i] > max){
                node = i;
                max = row[i];
            }
        }
        path.add(node);
        path.add(0);

        int choice, insert_position;
        while(path.size() < distances.length+1){
            choice = SelectNode(path, distances);
            insert_position = InsertNode(path, distances, choice);
            path.add(insert_position, choice);
        }

        // long endTime = System.nanoTime();

        //Get the farthest distance
        double distance = 0.0;
        for(int i=0; i<path.size()-1; i++){
            distance += distances[path.get(i)][path.get(i+1)];
        }

        System.out.println(distance);

        //Get the route of path
        for(int i=0; i<path.size()-1; i++){
            System.out.print(path.get(i) + ", ");
        } 
        System.out.println(path.get(0));
    }
}