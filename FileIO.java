
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileIO {
	// Create a new graph object from a simple dimacs-like file
	public static city readFile(String inputFile) throws IOException {
		// Open the file and read the number of vertices/edges
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String firstLine = br.readLine();
        String name = firstLine.split(":")[1].trim();
        int size = 0;
        String distanceType = "";
        while (true) {
            String line = br.readLine();
            if (line.split(":")[0].equals("DIMENSION")) {
                size = Integer.parseInt(line.split(":")[1].trim());
            }
            if (line.split(":")[0].equals("EDGE_WEIGHT_TYPE")) {
                distanceType = line.split(":")[1].trim();
            }
            if (line.equals("NODE_COORD_SECTION")) {
                break;
            }
        }

		
		city c = new city(name, distanceType, size);
		while (true) {
			String line = br.readLine();
			if (line.equals("EOF")) {
				break;
			}
			int num = Integer.parseInt(line.split(" ")[0]);
			double x = Double.parseDouble(line.split(" ")[1]);
			double y = Double.parseDouble(line.split(" ")[2]);
			c.addCoordinate(num, x, y);	
		}

		br.close();
		return c;
	}

	public static void main(String[] args) throws IOException{
		city c = readFile("Cincinnati.tsp");
//		for (int i = 0; i < c.getNum(); i++) {
//			System.out.print(c.getCoordinate().get(i).getNumber() + " ");
//			System.out.print(c.getCoordinate().get(i).getx() + " ");
//			System.out.println(c.getCoordinate().get(i).gety());
//		}
		c.calDistance();
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
		System.out.println(bb.getFinalCost());
		int[] path = bb.getFinalPath();
		for(int i = 0;i < c.getNum();i++){
			System.out.println(path[i] + " " + c.getDistances()[path[i]][path[i+1]]);
		}

    }
}


