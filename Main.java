import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        city c = FileIO.readFile("Cincinnati.tsp");
//		for (int i = 0; i < c.getNum(); i++) {
//			System.out.print(c.getCoordinate().get(i).getNumber() + " ");
//			System.out.print(c.getCoordinate().get(i).getx() + " ");
//			System.out.println(c.getCoordinate().get(i).gety());
//		}

		if (c.getDistanceType().equals("EUC_2D")) {
			c.calDistance();
		} else {
			c.calGeoDistance();
		}

		double[][] distances = c.getDistances();
//		for (int i = 0; i < c.getNum(); i++) {
//			String temp = "";
//			for (int j = 0; j < c.getNum(); j++) {
//				temp = temp + " " + String.valueOf(c.getDistances()[i][j]);
//			}
//			System.out.println(temp);
//		}

		double[][] test= {{0.0, 10.0, 15.0, 20.0},
			{10.0, 0.0, 35.0, 25.0},
			{15.0, 35.0, 0.0, 30.0},
			{20.0, 25.0, 30.0, 0.0}};
		city tt = new city("test","test",4);
		tt.setDistances(test);

		branchAndBound bb = new branchAndBound(c.getNum(),c);
		bb.branchBound();
		System.out.println((int)bb.getFinalCost());
		int[] path = bb.getFinalPath();
		for(int i = c.getNum() - 1; i >= 0; i--){
			System.out.println(path[(i+1) % c.getNum()] + " " + path[i] + " " + Math.round(c.getDistances()[path[i]][path[i+1]]));
		}
    }
}