import java.util.*;


public class SimulatedAnnealing{

    public static AnnealResult simulate(double[][] graph, double temperature, double coolRate, int seed){
        List<Integer> path = generateRandomPath(graph, 1);
        List<Long> traceList = new ArrayList<>();
        int num = graph.length;
        int num_iterate = 0;
        Random rand = new Random(seed);

        double distance = getDistance(graph, path);
        System.out.println("The initial total distance of path is: " + distance);

        // long terminTime = System.currentTimeMillis() + 60000;
        while( temperature > 1){
            num_iterate ++;

            int first_index = rand.nextInt(num);
            int second_index = rand.nextInt(num);
            if (first_index == second_index) {
				second_index = (second_index + 1) % num;
			}
            // while(first_index == second_index){
            //     second_index = rand.nextInt(num);
            // }

            //swap two nodes
            int temp = path.get(first_index);
            path.set(first_index, path.get(second_index));
            path.set(second_index, temp);
            
            double newdistance = getDistance(graph, path);
            System.out.println("The new total distance of path is: " + newdistance);

            temperature *= 1.0-coolRate;
            // if(temperature < 0.001){
            //     temperature = 10000;
            // }
            if(acceptProb(distance, newdistance, temperature) < rand.nextDouble()){
                temp = path.get(first_index);
                path.set(first_index, path.get(second_index));
                path.set(second_index, temp);
                continue;
            }

            traceList.add((long)newdistance);
            distance = newdistance;

        }

        path.add(path.get(0));
        System.out.println("The total number of iterations is: " + num_iterate);
        System.out.println("The final distance is: " + distance);

        AnnealResult result = new AnnealResult(path, traceList);
        return result;
    }




    public static List<Integer> generateRandomPath(double[][] graph, long seed){
        int len = graph.length;
        List<Integer> randomPath = new ArrayList<>();
        for(int i=0; i<len; i++){
            randomPath.add(i);
        }

        Random rand = new Random(seed);
        Collections.shuffle(randomPath, rand);
        return randomPath;
    }

    public static double acceptProb(double distance, double newdistance, double temperature){
    
        if (newdistance < distance) {
            return 1.0;
        }
        return Math.exp((distance - newdistance) / temperature);
    }


    public static double getDistance(double[][] graph, List<Integer> path){
        double distance = 0;
        for(int i=0; i<path.size()-1; i++){
            distance += graph[path.get(i)][path.get(i+1)];
        }
        distance += graph[path.get(path.size()-1)][path.get(0)];
        return distance;
    }
}