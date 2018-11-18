public class location {
    private int number;
    private double x;
    private double y;

    public location(int x, double y, double z){
        this.number = x;
        this.x = y;
        this.y = z;
    }

    public void setNumber(int a){
        this.number = a;
    }

    public int  getNumber(){
        return this.number;
    }

    public void setx(double x){
        this.x = x;
    }

    public double getx(){
        return this.x;
    }

    public void sety(double y){
        this.y = y;
    }

    public double gety(){
        return this.y;
    }
}
