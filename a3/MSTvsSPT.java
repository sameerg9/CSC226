import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MSTvsSPT class
public class MSTvsSPT{

    private static int MAX_VERTICES = 100;
    private static int[] SPTedgeTo;
    private static double[] SPTdistTo;
    private static int[] MSTedgeTo;
    private static double[] MSTdistTo;
    private static boolean[] marked;
    private static IndexMinPQ<Double> pq = new IndexMinPQ<Double>(MAX_VERTICES);
   
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
        int weight; //for dikstrkaks 
        int v0 ; // start vertex 
        
        pq.insert(0,SPTdistTo[0]);

        while(!pq.isEmpty()){
            v0 = pq.delMin();
            marked[v0] = true; 

            for(int v = 0 ; v < G.length  ; v++){ 
                ///dijkstras
                if (G[v0][v] != 0){
                   weight = G[v0][v];
                   relax(v0, v, weight);   //wil update SPTDistTO              
                }
               ///prims
               if(G[v0][v] != 0 ){
                    weight = G[v0][v] ;
                    if(marked[v])
                        continue; 

                    if(weight < MSTdistTo[v]){
                        MSTdistTo[v] = weight; 
                        MSTedgeTo[v] = v0 ; 
                    }

                    if (pq.contains(v)) {
                        pq.changeKey(v, MSTdistTo[v]);
                    }
                    else {                           
                        pq.insert(v, MSTdistTo[v]);
                    }

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
                else  pq.insert(w, SPTdistTo[w]);
        }
    }

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
