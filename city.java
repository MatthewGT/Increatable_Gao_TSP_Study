import java.util.*;

public class city {
    private String name;
    private String distanceType;
    private int locationNum;
    private List<location> coordinates;
    private double[][] distances;

    public city (String name, String distanceType, int locationNum){
        this.name = name;
        this.distanceType = distanceType;
        this.locationNum = locationNum;
        this.coordinates = new ArrayList<>();
        this.distances = new double[this.locationNum][this.locationNum];
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setType(String type){
        this.distanceType = type;
    }

    public String getType(){
        return this.distanceType;
    }

    public void setNum(int num){
        this.locationNum = num;
    }

    public int getNum(){
        return this.locationNum;
    }

    public void addCoordinate(int number, double x,double y){
        location temp = new location(number,x,y);
        this.coordinates.add(temp);
    }

    public List<location> getCoordinate(){
        return this.coordinates;
    }

    /// calculate the distance between any two locations
    public void calDistance(){
        for(int i = 0;i < this.locationNum;i++){
            for(int j = i+1;j < this.locationNum;j++){
                double x1 = this.coordinates.get(i).getx();
                double y1 = this.coordinates.get(i).gety();
                double x2 = this.coordinates.get(j).getx();
                double y2 = this.coordinates.get(j).gety();
                double dis =  Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
                this.distances[i][j] = dis;
                this.distances[j][i] = dis;
            }
        }
    }

    
    public void setDistances(double[][] test){
        this.distances = test;
    }
    public double[][] getDistances(){
        return this.distances;
    }
}
