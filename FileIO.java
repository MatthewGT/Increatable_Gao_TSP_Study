import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
			if (line.trim().equals("EOF")) {
				break;
			}
			int num = Integer.parseInt(line.trim().split(" ")[0]);
			double x = Double.parseDouble(line.trim().split(" ")[1]);
			double y = Double.parseDouble(line.trim().split(" ")[2]);
			c.addCoordinate(num, x, y);
		}

		br.close();
		return c;
	}

	// public static void writeData(List<List<Double>> outputdata, String outputFile) throws IOException {
	// 	PrintWriter output = new PrintWriter(outputFile, "UTF-8");
	// 	for (int i = 0; i < outputdata.size(); i++) {
	// 		output.println(outputdata.get(i).get(0) + "," + Math.round(outputdata.get(i).get(1)));
	// 	}
	// 	output.close();
	// }
}


