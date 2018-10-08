
// ************************************************************ // 

    /* MSTvsSPT.java
   CSC 226 - Summer 2017
   Assignment 3 - Minimum Weight Spanning Tree versus Shortest Path Spanning Tree Template
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
    java MSTvsSPT
    
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
    java MSTvsSPT file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
    <adjacency matrix row 1>
    ...
    <adjacency matrix row n>
    
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
    
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   R. Little - 06/23/2017
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MSTvsSPT class


public class MSTvsSPT_1{
    private static int distTo[]; 
    private static Edge edgeTo[]; 
    IndexMinPQ<Integer> pq ;
    static IndexMinPQ<Edge> pq1 ; 
    private static int MAX = (int)Double.POSITIVE_INFINITY;
    /* MSTvsSPT(G)
        Given an adjacency matrix for graph G, determine if the minimum spanning
        tree of G equals the single-source shortest path tree from vertex 0.
        
        If G[i][j] == 0, there is no edge between vertex i and vertex j
        If G[i][j] > 0, there is an edge between vertices i and j, and the
        value of G[i][j] gives the weight of the edge.
        No entries of G will be negative and all will be distinct.
    */



        //---------------------------------//

        // public int MST_Path(){}

        // public int SPT_PATH(){

        // }
public void  paths(int path[],int n){
            for(int i =0; i<= n ; i++){
                if(path[i] != -1 )
                    System.out.println("");

            }

        }

   private void relax(Edge e){

    int v = e.either(); 
    int w = e.other(v); 

    if (distTo[w] > distTo[v]+ e.weight() ){ //check for shortest edge out 
        distTo[w] = distTo[v] + e.weight(); 
        edgeTo[w] = e;
    }

    if(pq.contains(w))
        pq.decreaseKey(w , distTo[w]); 
    else
        pq.insert(w, distTo[w]); 
   }

public static int minDistance(int distTo[] , int path[]){
    
    int min = MAX;
    int minIx = -1 ; 

    for(int v =0 ; v<= distTo.length ; v++ ){
        if(path[v] == -1  && distTo[v]<= min){
            min = distTo[v] ; 
            minIx = v ; 
        }
    }
    return minIx; 

}

    static boolean MSTvsSPT(int[][] G){
    
        int n = G.length;
        int prev[] = new int[n];  
        
        int path0[]= new int[n]; 
        
        distTo= new int[n]; 
        edgeTo = new Edge[n]; 
        int temp [] = new int[n]; 
        
        Edge edgeBag[] = new Edge[n];

        int edgers = 0 ; 
    
        IndexMinPQ pq ; 

        for (int v = 0 ; v<n ; v++){
            distTo[v] = MAX; 
            //add all nodes to unvisited PQ 
            path0[v] = -1 ;; 
        } 
        distTo[0] = 0 ;

        for(int i =0 ; i<= n ; i++ ){
            //find min neigbor 
            int u = minDistance(distTo, path0); 
            //mark as visited
            path0[u] = 1 ; 

            for(int v = 0 ; v < n ; v++){
                //update the distTo only if not in path, edge exist from u-v , total weight of apth frmo source to v is smaller thru u than current value of distT0[]

                if(path0[v]>0 && G[u][v] != 0 && distTo[u] != MAX && (distTo[u]+G[u][v]) < distTo[v] ){
                    distTo[v] = distTo[u]  + G[u][v]; 
                }

            }
        }
    
        boolean mstEqSpt = false;
        /* ... Your code here ... */

        return mstEqSpt;
        
    }
        
    /* main()
       Contains code to test the MSTvsSPT function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args){
        Scanner s;
        if (args.length > 0){
            try{
                s = new Scanner(new File(args[0]));
            } catch(java.io.FileNotFoundException e){
                System.out.printf("Unable to open %s\n",args[0]);
                return;
            }
            System.out.printf("Reading input values from %s.\n",args[0]);
        }else{
            s = new Scanner(System.in);
            System.out.printf("Reading input values from stdin.\n");
        }
        
        int graphNum = 0;
        double totalTimeSeconds = 0;
        
        //Read graphs until EOF is encountered (or an error occurs)
        while(true){
            graphNum++;
            if(graphNum != 1 && !s.hasNextInt())
                break;
            System.out.printf("Reading graph %d\n",graphNum);
            int n = s.nextInt();
            int[][] G = new int[n][n];
            int valuesRead = 0;
            for (int i = 0; i < n && s.hasNextInt(); i++){
                for (int j = 0; j < n && s.hasNextInt(); j++){
                    G[i][j] = s.nextInt();
                    valuesRead++;
                }
            }
            if (valuesRead < n*n){
                System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
                break;
            }
            long startTime = System.currentTimeMillis();
        
            boolean msteqspt = MSTvsSPT(G);
            long endTime = System.currentTimeMillis();
            totalTimeSeconds += (endTime-startTime)/1000.0;
            
            System.out.printf("Graph %d: Does MST = SPT? %b\n",graphNum,msteqspt);
        }
        graphNum--;
        System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}