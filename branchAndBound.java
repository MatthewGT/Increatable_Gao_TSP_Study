import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.*;

public class branchAndBound{
    private int nums;
    private int[] finalPath;
    private boolean[] visited;
    private double finalCost;
    private List<List<Double>> output;
    private city c;

    public branchAndBound(int N,city City){
        this.nums = N;
        this.finalPath = new int[N+1];
        this.visited = new boolean[N];
        this.finalCost = Double.MAX_VALUE;
        this.c = City;
        this.output = new ArrayList<>();
    }

    private void convertToFianl(int[] path){
        for (int i = 0; i < this.nums; i++) {
            this.finalPath[i] = path[i];
        }
        this.finalPath[this.nums] = path[0];
    }

    ////find the minimum edge cost
    public double firstMin(int i){
        double min = Double.MAX_VALUE;
        for(int j = 0;j < this.nums;j++){
            if(this.c.getDistances()[i][j] < min && j !=i){
                min = this.c.getDistances()[i][j];
            }
        }
        return min;
    }

    ///find the second minimum edge cost
    public double secondMin(int i){
        double firstmin = Double.MAX_VALUE;
        double secondmin = Double.MAX_VALUE;
        for(int j = 0;j < this.nums;j++){
            if(i == j)
                continue;
            if(this.c.getDistances()[i][j] <= firstmin ){
                secondmin = firstmin;
                firstmin = this.c.getDistances()[i][j];
            }
            else if(this.c.getDistances()[i][j] <= secondmin && this.c.getDistances()[i][j] != firstmin){
                secondmin = this.c.getDistances()[i][j];
            }
        }
        return secondmin;
    }

    private void resetVisit(){
        Arrays.fill(this.visited,false);
    }

    private void recursion(double currBond,double currCost,int level,int[] currpath,long start, PrintWriter output1, PrintWriter output2, String outfile2, int cut_off) throws IOException{
        if(level == this.nums && this.c.getDistances()[currpath[level-1]][currpath[0]] != 0.0){
            double tempCurrCost = currCost + this.c.getDistances()[currpath[level-1]][currpath[0]];
            if(tempCurrCost < this.finalCost){
                convertToFianl(currpath);
                this.finalCost = tempCurrCost;
                if ((double)(System.currentTimeMillis()- start) / 1000 > cut_off) {
                    output1.close();
                    System.exit(0);
                }
                output1.println((double)(System.currentTimeMillis()- start) / 1000 + "," +  (int)this.finalCost);
                output2 = new PrintWriter(outfile2);
                int[] path = this.finalPath;
                output2.println((int)this.finalCost);
                for (int i = path.length - 1; i >= 0; i--) {
                    if (i == 0) {
                        output2.printf("%d", path[i]);
                    } else {
                        output2.printf("%d,", path[i]);
                    }
                }
                output2.close();
                
                // List<Double> temp = new ArrayList<>();
                // temp.add((double) (System.currentTimeMillis()- start));
                // temp.add(this.finalCost);
                // System.out.println("-----better choice occurs------");
                // System.out.println("current run time: " + (System.currentTimeMillis()- start));
                // System.out.println((System.currentTimeMillis()- start) + "," + (int)this.finalCost);
//                for(int i = 0;i < this.nums-1;i++){
//                    System.out.println(currpath[i] + " " + currpath[i+1] + " " + this.c.getDistances()[currpath[i]][currpath[i+1]]);
//                }
                // output.add(temp);
            }
            return;
        }
        for (int i = 0; i < this.nums; i++) {
            if(this.c.getDistances()[currpath[level-1]][i] != 0.0 && this.visited[i] == false){
                double temp = currBond;
                currCost += this.c.getDistances()[currpath[level-1]][i];
                if(level == 1){
                    currBond = (firstMin(currpath[level-1]) + firstMin(i))/2;
                }
                else{
                    currBond = (secondMin(currpath[level-1]) + firstMin(i))/2;
                }
                if(currBond + currCost < this.finalCost){
                    currpath[level] = i;
                    this.visited[i] = true;
                    recursion(currBond,currCost,level+1,currpath,start,output1, output2, outfile2, cut_off);
                }
                currCost -= this.c.getDistances()[currpath[level-1]][i];
                currBond = temp;
                resetVisit();
                for(int j = 0;j < level;j++)
                    this.visited[currpath[j]] = true;
            }
        }
    }

    public void branchBound(String outfile1, String outfile2, int cut_off) throws IOException{
        long start = System.currentTimeMillis();
        PrintWriter output1 = new PrintWriter(outfile1, "UTF-8");
        PrintWriter output2 = new PrintWriter(outfile2, "UTF-8");
        int[] currPath = new int[this.nums];
        double currBound = 0.0;
        for(int i = 0;i < this.nums;i++)
            currPath[i] = -1;
        resetVisit();
        for (int i = 0; i < this.nums; i++) {
            currBound += firstMin(i) + secondMin(i);
        }
        currBound = currBound/2;
        this.visited[0] = true;
        currPath[0] = 0;
        recursion(currBound,0.0,1,currPath,start, output1, output2, outfile2, cut_off);
        output1.close();
        output2.close();
    }

    public int[] getFinalPath(){
        return this.finalPath;
    }

    public double getFinalCost(){
        return this.finalCost;
    }
}
