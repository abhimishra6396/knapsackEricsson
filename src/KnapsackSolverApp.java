import model.HillClimbing;

import java.io.IOException;

public class KnapsackSolverApp {
    public static void main(String[] args) throws IOException {
        HillClimbing hc = new HillClimbing(100);
        //hc.genKnapsackData();
        hc.getKnapsackData();
        hc.runKnapsack();
    }
}
