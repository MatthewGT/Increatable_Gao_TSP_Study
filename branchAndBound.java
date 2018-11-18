import java.util.*;

public class branchAndBound {
    private int nums;
    private int[] finalPath;
    private boolean[] visited;
    private double finalCost;
    private city c;

    public branchAndBound(int N,city City){
        this.nums = N;
        this.finalPath = new int[N+1];
        this.visited = new boolean[N];
        this.finalCost = Double.MAX_VALUE;
        this.c = City;
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

    private void recursion(double currBond,double currCost,int level,int[] currpath){
        if(level == this.nums && this.c.getDistances()[currpath[level-1]][currpath[0]] != 0.0){
            double tempCurrCost = currCost + this.c.getDistances()[currpath[level-1]][currpath[0]];
            if(tempCurrCost < this.finalCost){
                convertToFianl(currpath);
                this.finalCost = tempCurrCost;
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
                    recursion(currBond,currCost,level+1,currpath);
                }
                currCost -= this.c.getDistances()[currpath[level-1]][i];
                currBond = temp;
                resetVisit();
                for(int j = 0;j < level;j++)
                    this.visited[currpath[j]] = true;
            }
        }
    }

    public void branchBound(){
        int[] currPath = new int[this.nums];
        double currBound = 0.0;
        for(int i = 0;i < this.nums;i++)
            currPath[i] = -1;
        resetVisit();
        for (int i = 0; i < this.nums; i++) {
            currBound += firstMin(i) + secondMin(i);
        }
        this.visited[0] = true;
        currPath[0] = 0;
        recursion(currBound,0.0,1,currPath);
    }

    public int[] getFinalPath(){
        return this.finalPath;
    }

    public double getFinalCost(){
        return this.finalCost;
    }
}
