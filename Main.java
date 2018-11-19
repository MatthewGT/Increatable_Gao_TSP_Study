import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{

		if (args.length != 4) {
			System.out.println("Wrong input parameter number!");
			return;
		}

		String filename = args[0];
		String alg = args[1];
		int cut_off = Integer.parseInt(args[2]);
		int seed = Integer.parseInt(args[3]);
		String outfile;
		String outfile2;
		if (alg.equals("BnB") || alg.equals("Approx")) {
			outfile = filename.split("\\.")[0] + "_" + alg + "_" + Integer.toString(cut_off) + ".trace";
		} else {
			outfile = filename.split("\\.")[0] + "_" + alg + "_" + Integer.toString(cut_off) + "_" + Integer.toString(seed) + ".trace";
		}

		if (alg.equals("BnB") || alg.equals("Approx")) {
			outfile2 = filename.split("\\.")[0] + "_" + alg + "_" + Integer.toString(cut_off) + ".sol";
		} else {
			outfile2 = filename.split("\\.")[0] + "_" + alg + "_" + Integer.toString(cut_off) + "_" + Integer.toString(seed) + ".sol";
		}
		

        city c = FileIO.readFile(filename);
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

		if (alg.equals("BnB")) {
			branchAndBound bb = new branchAndBound(c.getNum(),c);
			List<List<Double>> output = bb.branchBound();
			System.out.println((int)bb.getFinalCost());
			int[] path = bb.getFinalPath();
			for(int i = c.getNum() - 1; i >= 0; i--){
				System.out.println(path[(i+1) % c.getNum()] + " " + path[i] + " " + Math.round(c.getDistances()[path[i]][path[i+1]]));
			}
			PrintWriter output2 = new PrintWriter(outfile2, "UTF-8");
			output2.println((int)bb.getFinalCost());
			output2.printf("%d,", path[0]);
			for(int i = c.getNum() - 1; i >= 0; i--) {
				if (i == 0) {
					output2.printf("%d", path[i]);
				} else {
					output2.printf("%d,", path[i]);
				}
			}
			output2.close();
			FileIO.writeData(output, outfile);
		} else if (alg.equals("Approx")) {

		} else if (alg.equals("LS1")) {

		} else if (alg.equals("LS2")) {

		} else {
			System.out.println("No such algorithm!");
			return;
		}
    }
}