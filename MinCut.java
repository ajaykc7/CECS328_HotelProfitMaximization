
package hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Ajay
 */
public class MinCut {

    boolean visited[];
    int numberOfVertices;
    public MinCut(int numberOfVertices) {
        this.numberOfVertices=numberOfVertices;
        visited = new boolean[this.numberOfVertices];
    }

    public ArrayList<String> minCut(int graph[][], int source, int sink) {
        
        ArrayList<String> minCutList = new ArrayList<String>();
        
        int residualGraph[][] = new int[numberOfVertices][numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        Map<Integer, Integer> parent = new HashMap<Integer, Integer>();

        while (bfs(residualGraph, source, sink, parent)) {

            int flow = Integer.MAX_VALUE;
            int v = sink;

            while (v != source) {
                int u = parent.get(v);
                if (flow > residualGraph[u][v]) {
                    flow = residualGraph[u][v];
                }
                v = u;
            }
           
            v = sink;
            while(v !=source){
                int u = parent.get(v);
                residualGraph[u][v] = residualGraph[u][v]-flow;
                residualGraph[v][u] = residualGraph[v][u]+flow;
                v=u;
            }
        }
        
        depthForSearch(residualGraph, source, visited);
        
        for(int i=0;i<numberOfVertices;i++){
            for(int j=0;j<numberOfVertices;j++){
                if((visited[i]) && (!visited[j])&& (graph[i][j]>0)){
                    minCutList.add(i+"--"+j);
                    //System.out.println(i+"--"+j);
                }
            }
        }
        
        return minCutList;
    }

    private boolean bfs(int[][] residualGraph, int source, int sink, Map<Integer, Integer> parent) {

        Set<Integer> visited = new HashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();

        queue.add(source);
        visited.add(source);

        boolean pathPresent = false;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < numberOfVertices; v++) {
                if (!visited.contains(v) && residualGraph[u][v] > 0) {
                    parent.put(v, u);
                    visited.add(v);
                    queue.add(v);
                    if (v == sink) {
                        pathPresent = true;
                        break;
                    }
                }
            }
        }

        return pathPresent;
    }
    
    private void depthForSearch(int[][] residualGraph, int source, boolean[] visited){
        visited[source]=true;
        for(int i=0;i<numberOfVertices;i++){
            if((residualGraph[source][i]>0)&&(!visited[i])){
                depthForSearch(residualGraph,i,visited);
            }
        }
    }

}
