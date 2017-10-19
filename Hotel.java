
package hotel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ajay
 */
public class Hotel {

    public static void main(String[] args) {

        ArrayList<Integer> revenueList = new ArrayList<Integer>();
        ArrayList<Integer> costList = new ArrayList<Integer>();
        ArrayList<String> dependencyList = new ArrayList<String>();

        File file = new File("information.txt");

        try {
            Scanner fileReader = new Scanner(file);
            String costElements[] = fileReader.nextLine().split(" ");
            for (int i = 0; i < costElements.length; i++) {
                costList.add(Integer.parseInt(costElements[i]));
            }

            String revenueElements[] = fileReader.nextLine().split(" ");
            for (int i = 0; i < revenueElements.length; i++) {
                revenueList.add(Integer.parseInt(revenueElements[i]));
            }
            do {
                dependencyList.add(fileReader.nextLine());
            } while (fileReader.hasNext());
        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found");
        }
        int numberOfVertices = revenueList.size() + costList.size() + 2;

        int[][] graph = new int[numberOfVertices][numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                graph[i][j] = 0;
            }
        }

        for (int i = 0; i < revenueList.size(); i++) {
            graph[0][i + 1] = revenueList.get(i);
        }

        for (int i = 0; i < costList.size(); i++) {
            graph[i + revenueList.size() + 1][numberOfVertices - 1] = costList.get(i);
        }

        for (int i = 0; i < dependencyList.size(); i++) {
            String amenitiesList = dependencyList.get(i);
            String elements[] = amenitiesList.split(" ");
            for (int j = 0; j < elements.length; j++) {
                graph[i + 1][revenueList.size() + 1 + Integer.parseInt(elements[j])] = Integer.MAX_VALUE;
            }
        }
        /*for(int i=0;i<numberOfVertices;i++){
            for(int j=0;j<numberOfVertices;j++){
                if(i==0){
                    if(j!=0){
                        graph[i][j]=revenueList.get(j-1);
                    }
                    
                }
            }
        }*/
 /*for(int i=0;i<numberOfVertices;i++){
            for(int j=0;j<numberOfVertices;j++){
                System.out.print(graph[i][j]+" ");
            }
            System.out.println();
        }*/
 
        MinCut mincut = new MinCut(numberOfVertices);
        ArrayList<String> minCutList = mincut.minCut(graph, 0, numberOfVertices - 1);

        /* int[][] egraph = {{0,5,5,0,0,0},
                            {0,0,0,3,6,0},
                            {0,0,0,1,3,0},
                            {0,0,0,0,0,6},
                            {0,0,0,0,0,6},
                            {0,0,0,0,0,0}};
         
        MinCut mincut = new MinCut(6);
        ArrayList<String> minCutList = mincut.minCut(egraph, 0, 5);
         */
        /*for (int i = 0; i < minCutList.size(); i++) {
            System.out.println(minCutList.get(i));
        }*/

        /* ArrayList<Integer> optimalAmenities = new ArrayList<Integer>();
        String amenitiesString = "";
        for (int i = 0; i < minCutList.size(); i++) {
            String elements[] = minCutList.get(i).split("-");
            if (Integer.parseInt(elements[0]) >= revenueList.size() + 1) {
                //optimalAmenities.add(Integer.parseInt(elements[0])-(revenueList.size()+1));
                if (amenitiesString.length() == 0) {
                    amenitiesString = Integer.toString(Integer.parseInt(elements[0]) - (revenueList.size() + 1));
                } else {
                    amenitiesString = amenitiesString + " " + Integer.toString(Integer.parseInt(elements[0]) - (revenueList.size() + 1));
                }
                //amenitiesString = amenitiesString + (Integer.parseInt(elements[0])-(revenueList.size()+1));
            }
        }

        for(int i=0;i<optimalAmenities.size();i++){
            System.out.println(optimalAmenities.get(i));
        }
        System.out.println(amenitiesString);
         */
        ArrayList<Integer> investorList = new ArrayList<Integer>();

        boolean rightInvestorList[] = new boolean[revenueList.size()];
        for (int i = 0; i < rightInvestorList.length; i++) {
            rightInvestorList[i] = true;
        }
        for (int i = 0; i < minCutList.size(); i++) {
            String elements[] = minCutList.get(i).split("--");
            if (Integer.parseInt(elements[1]) <= revenueList.size()) {
                investorList.add(Integer.parseInt(elements[1]) - 1);
            }
        }

        for (int i = 0; i < investorList.size(); i++) {
            rightInvestorList[investorList.get(i)] = false;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("investors.txt"));
            for (int i = 0; i < rightInvestorList.length; i++) {
                if (rightInvestorList[i]) {
                    writer.write(i + "\n");
                }
            }
            writer.close();
        } catch (IOException ioe) {
            System.out.println("Error processing file");
        }

        System.out.println("Successful");

    }

}
