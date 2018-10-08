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
public class MSTvsSPT0{

	private static int MAX_VERTICES = 100;
	private static int[] SPTedgeTo;
	private static double[] SPTdistTo;
	private static int[] MSTedgeTo;
	private static double[] MSTdistTo;
	private static boolean[] marked;
	private static IndexMinPQ<Double> pq1 = new IndexMinPQ<Double>(MAX_VERTICES);
	private static IndexMinPQ<Double> pq1 = new IndexMinPQ<Double>(MAX_VERTICES);
	/* MSTvsSPT(G)
		Given an adjacency matrix for graph G, determine if the minimum spanning
		tree of G equals the single-source shortest path tree from vertex 0.

		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative and all will be distinct.
	*/
	static boolean MSTvsSPT(int[][] G){
		/* Determine if the MST equals the SPT by any method */
		/* (You may add extra functions if necessary) */
		SPTdistTo = new double[G.length];
		SPTedgeTo = new int[G.length];
		MSTedgeTo = new int[G.length];
		MSTdistTo = new double[G.length];
		marked = new boolean[G.length];
		pq1 = new IndexMinPQ<Double>[G.length];
		pq2 = new IndexMinPQ<Double>[G.length];

		for (int v = 0; v < G.length; v++){
				MSTdistTo[v] = Double.POSITIVE_INFINITY;
				SPTdistTo[v] = Double.POSITIVE_INFINITY;
		}

		for (int v = 0; v < G.length; v++)      // run from each vertex to find
				if (!marked[v]) prim(G, v);      // minimum spanning forest

		pq.insert(s, SPTdistTo[s]);
		while (!pq.isEmpty()) {
				v = pq.delMin();
				for (int i = 0; i < G.length; i++){
						if (G[v][i] != 0){
								weight = G[v][i];
								relax(v, i, weight);
						}
				}
		}

		/* ... Your code here ... */

		// -------- Build the SPT -------- \\
		DijkstraUndirectedSP(G, 0);

		// -------- Build the MST -------- \\
		PrimMST(G);

		/* Add the weight of each edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/

		//SPT weight
		int v;
		int w;
		int MSTtotalWeight = 0;
		int SPTtotalWeight = 0;
		int arr[][] = new int[G.length][G.length];

		for (int i = 0; i < G.length; i++){
			for (int j = 0; j < arr[i].length; j++){
				arr[i][j] = 0;
			}
		}

		for (int i = 0; i < SPTedgeTo.length; i++){
			v = i;
			w = SPTedgeTo[i];
			if (arr[v][w] == 0){
				arr[v][w] = 1;
				arr[w][v] = 1;
				SPTtotalWeight += G[v][w];
			}
		}

		for (int i = 0; i < G.length; i++){
			for (int j = 0; j < arr[i].length; j++){
				arr[i][j] = 0;
			}
		}

		for (int i = 0; i < MSTedgeTo.length; i++){
			v = i;
			w = MSTedgeTo[i];
			if (arr[v][w] == 0){
				arr[v][w] = 1;
				arr[w][v] = 1;
				MSTtotalWeight += G[v][w];
			}
		}

		boolean mstEqSpt;
		/* ... Your code here ... */
		if (SPTtotalWeight == MSTtotalWeight){
			mstEqSpt = true;
		} else {
			mstEqSpt = false;
		}

		return mstEqSpt;

	}

	public static void DijkstraUndirectedSP(int[][] G, int s) {
			/*for (Edge e : G.edges()) {
					if (e.weight() < 0)
							throw new IllegalArgumentException("edge " + e + " has negative weight");
			}*/

			SPTdistTo = new double[G.length];
			SPTedgeTo = new int[G.length];

			//validateVertex(s);

			for (int v = 0; v < G.length; v++)
					SPTdistTo[v] = Double.POSITIVE_INFINITY;
			SPTdistTo[s] = 0.0;

			// relax vertices in order of distance from s
			int weight;
			int v;
			pq.insert(s, SPTdistTo[s]);
			while (!pq.isEmpty()) {
					v = pq.delMin();
					for (int i = 0; i < G.length; i++){
							if (G[v][i] != 0){
									weight = G[v][i];
									relax(v, i, weight);
							}
					}
			}

			// check optimality conditions
			//assert check(G, s);
	}

	private static void relax(int v, int w, int weight){
			if (SPTdistTo[w] > SPTdistTo[v] + weight){
				SPTdistTo[w] = SPTdistTo[v] + weight;
				SPTedgeTo[w] = v;
				if (pq.contains(w)) pq.changeKey(w, SPTdistTo[w]);
				else								pq.insert(w, SPTdistTo[w]);
		}
	}

	public static void PrimMST(int[][] G) {
			MSTedgeTo = new int[G.length];
			MSTdistTo = new double[G.length];

			marked = new boolean[G.length];
			for (int v = 0; v < G.length; v++){
					MSTdistTo[v] = Double.POSITIVE_INFINITY;
			}

			for (int v = 0; v < G.length; v++)      // run from each vertex to find
					if (!marked[v]) prim(G, v);      // minimum spanning forest

			// check optimality conditions
			//assert check(G);
	}

	// run Prim's algorithm in graph G, starting from vertex s
	private static void prim(int[][] G, int s) {
			MSTdistTo[s] = 0.0;
			pq.insert(s, MSTdistTo[s]);
			while (!pq.isEmpty()) {
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
					if (marked[w]) continue;         // v-w is obsolete edge
					if (weight < MSTdistTo[w]) {
							MSTdistTo[w] = weight;
							MSTedgeTo[w] = v;
							if (pq.contains(w)) pq.decreaseKey(w, MSTdistTo[w]);
							else                pq.insert(w, MSTdistTo[w]);
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
