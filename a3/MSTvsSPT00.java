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

	answer for question 4 : 
		Single-source longest paths problem in edge-weighted DAGs. We can solve the single-source longest paths problems in edge-weighted DAGs by initializing the distTo[] values to negative infinity and switching the sense of the inequality in relax(). AcyclicLP.java implements this approach. 





*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MSTvsSPT class
public class MSTvsSPT00{

	private static int MAX_VERTICES = 100;
	private static int[] SPTedgeTo;
	private static double[] SPTdistTo;
	private static int[] MSTedgeTo;
	private static double[] MSTdistTo;
	private static boolean[] marked;
	private static IndexMinPQ<Double> pq = new IndexMinPQ<Double>(MAX_VERTICES);
	private static IndexMinPQ<Double> pq1 = new IndexMinPQ<Double>(MAX_VERTICES);
	private static IndexMinPQ<Double> pqMST = new IndexMinPQ<Double>(MAX_VERTICES);

	/* MSTvsSPT(G)
		Given an adjacency matrix for graph G, determine if the minimum spanning
		tree of G equals the single-source shortest path tree from vertex 0.

		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative and all will be distinct.
	*/
	static boolean MSTvsSPT(int[][] G){
		
		SPTdistTo = new double[G.length];
		SPTedgeTo = new int[G.length];

		MSTdistTo = new double[G.length];
		MSTedgeTo = new int[G.length];

		marked = new boolean[G.length];

		//set mst and spt distances to inf /// runtime: O(n)
		for (int v = 0; v < G.length; v++){
					MSTdistTo[v] = Double.POSITIVE_INFINITY;
					SPTdistTo[v] = Double.POSITIVE_INFINITY;
			}
		
		SPTdistTo[0] = 0.0;
		//MSTdistTo[s] = 0.0 ; 
		int weight; //for dikstrkaks 
		int v0 ; // start vertex 
		
		
		pq.insert(0,SPTdistTo[0]);


		while(!pq.isEmpty()){
			v0 = pq.delMin();

			for(int v = 0 ; v < G.length  ; v++){ 
				///dijkstras
				if (G[v0][v] != 0){
						weight = G[v0][v];
						relax(v0, v, weight);	//wil update SPTDistTO				
				}
					///prims
				if (!marked[v]) {
					prim(G, v);
					//scan(G, v0); 
				}
				
				if(SPTedgeTo[v0] != MSTedgeTo[v0])
					return false ; 		
			}
		}
		return true; 
	}
	

	private static void relax(int v, int w, int weight){
			if (SPTdistTo[w] > SPTdistTo[v] + weight){
				SPTdistTo[w] = SPTdistTo[v] + weight;
				SPTedgeTo[w] = v;
				if (pq.contains(w)) pq.changeKey(w, SPTdistTo[w]);
				else								pq.insert(w, SPTdistTo[w]);
		}
	}
	//run prims from start vertex s, on G 
	private static void prim(int[][] G, int s) {
			MSTdistTo[s] = 0.0;
			pq.insert(s, MSTdistTo[s]);
			while (!pq.isEmpty()) { 	//log v 
					int v = pq.delMin();
					scan(G, v);
			}
	}

	// scan vertex v
	private static void scan(int[][] G, int v) {
			marked[v] = true;
			int weight;
			int w;
			for (int i = 0; i < G[v].length; i++) { 
			    if (G[v][i] != 0){
					weight = G[v][i];
					w = i;
					if (marked[w]) 
						continue;         // v-w is obsolete edge
					if (weight < MSTdistTo[w]) {
							MSTdistTo[w] = weight;
							MSTedgeTo[w] = v;
							if (pq.contains(w)) 
								pq.changeKey(w, MSTdistTo[w]);
							else
				                pq.insert(w, MSTdistTo[w]);
					}
				}
			}
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
