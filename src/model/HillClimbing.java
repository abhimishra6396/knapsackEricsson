package model;
import java.util.*;
import java.io.*;

public class HillClimbing {
    int capacity;
    FileWriter write_val;
    FileWriter write_weight;
    ArrayList<Integer> val_array;
    ArrayList<Integer> weight_array;
    String currentsolution;
    String nextsolution;
    Random rg;
    int num_items;

    public void setNum_items(int num_items) {
        this.num_items = num_items;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public HillClimbing(int capacity) throws IOException {
        this.setCapacity(capacity);
        this.setNum_items(200);
        currentsolution="";
        for(int i=0;i<num_items;i++){
            currentsolution+="0";
        }
        rg = new Random();
    }

    public void genKnapsackData() throws IOException {
        write_val = new FileWriter("valfile.txt");
        write_weight = new FileWriter("weightfile.txt");
        for(int i=0;i<num_items;i++)
        {
            write_val.write(String.valueOf(rg.nextInt(20)+1)+"\n");
            write_val.flush();
            write_weight.write(String.valueOf(rg.nextInt(50)+1)+"\n");
            write_weight.flush();
        }
        write_val.close();
        write_weight.close();
    }

    public void getKnapsackData() throws IOException {
        val_array = new ArrayList<Integer>();
        weight_array = new ArrayList<Integer>();
        BufferedReader readval = new BufferedReader(new FileReader("valfile.txt"));
        BufferedReader readweight = new BufferedReader(new FileReader("weightfile.txt"));
        for(int i=0;i<num_items;i++){
            val_array.add(Integer.valueOf(readval.readLine()));
            weight_array.add(Integer.valueOf(readweight.readLine()));
        }
    }

    public int getSolutionValue(String currentsolution){
        int cumulative_val=0;
        int cumulative_weight=0;
        for(int i=0;i<num_items;i++) {
            cumulative_val += val_array.get(i) * Character.getNumericValue(currentsolution.charAt(i));
            cumulative_weight += weight_array.get(i) * Character.getNumericValue(currentsolution.charAt(i));
        }
        return cumulative_weight > capacity ? -1 : cumulative_val;
    }

    public int updatedVal(String currentsolution){
        int min_weight_violation=100;
        int max_value_gain=0;
        String next_solution=currentsolution;

        for (int i=0;i<num_items;i++){
            String newsol = currentsolution;
            if ('0'==currentsolution.charAt(i)){
                char[] char_array = newsol.toCharArray();
                char_array[i] = '1';
                newsol = String.valueOf(char_array);

                if (weight_array.get(i)<min_weight_violation){
                    min_weight_violation = weight_array.get(i);
                    next_solution=newsol;
                    max_value_gain = val_array.get(i);
                }
                else if (weight_array.get(i) == min_weight_violation){
                    next_solution = max_value_gain < val_array.get(i) ? newsol : next_solution;
                    max_value_gain = max_value_gain < val_array.get(i) ? val_array.get(i) : max_value_gain;
                }
            }
        }
        int next_val = getSolutionValue(next_solution);
        nextsolution = next_solution;
        return next_val;
    }

    public void printSolutions(){
        System.out.println("----------------------------------------- Results --------------------------------------------");
        System.out.println("Optimal Solution is: " + currentsolution);
        System.out.println("Optimal Value is: " + getSolutionValue(currentsolution));
        int cumulative_weight=0;
        for(int i=0;i<num_items;i++) {
            cumulative_weight += weight_array.get(i) *Character.getNumericValue(currentsolution.charAt(i));
        }
        System.out.println("Weight of Optimal Solution is: " + cumulative_weight);
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    public void runKnapsack(){
        int currentindex;
        while (true){
            currentindex = rg.nextInt(num_items);
            if (weight_array.get(currentindex)<capacity)
                break;
        }
        char[] char_array = currentsolution.toCharArray();
        char_array[currentindex] = '1';
        currentsolution = String.valueOf(char_array);
        while(true){
            if (updatedVal(currentsolution)<=getSolutionValue(currentsolution))
                break;
            currentsolution = nextsolution;
        }
        printSolutions();
    }
}
