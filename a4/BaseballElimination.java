import java.util.*;
import java.io.File;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;

//Do not change the name of the BaseballElimination class
public class BaseballElimination{

	// We use an ArrayList to keep track of te eliminated teams.
	public ArrayList<String> eliminated = new ArrayList<String>();

	/* BaseballElimination(s)
		Given an input stream connected to a collection of baseball division
		standings we determine for each division which teams have been eliminated
		from the playoffs. For each team in each division we create a flow network
		and determine the maxflow in that network. If the maxflow exceeds the number
		of inter-divisional games between all other teams in the division, the current
		team is eliminated.
	*/
	public BaseballElimination(Scanner s){

		int n = s.nextInt(); //total numher of teams in div
		int V = n*(n-1)/2 + 2; // number of vertices needed 
		int E = 0;

		int[][] games = new int[n][n];
		boolean[][] marked = new boolean[n][n];
		String[] teams = new String[n];
		int[] wins = new int[n];
		int[] remaining = new int[n];


		for (int i = 0; i < n; i++){
			
			teams[i] = s.next();
			wins[i] = s.nextInt();
			remaining[i] = s.nextInt();

			for (int j = 0; j < n; j++){
				int edgeIn = s.nextInt();
				
				if (edgeIn != 0 && !marked[i][j]){ // if non zero and not visited
					games[i][j] = edgeIn;
					marked[i][j] = true;
					marked[j][i] = true;
					E++;
				}
			}
		}

		FlowNetwork flownet;
		int source = 0;
		int t = V-1;

		int nuu[] = new int[(n-1)*(n-2)/2];
		int k;
		int index;
		boolean[][] marked0 ;
		int total;

		//populate flow network for every team
		for (int v = 0; v < n; v++){



			System.out.println("Flow Network for " + teams[v]);
			marked0 = new boolean[n][n];
			flownet = new FlowNetwork(V);
			index = 1;

			//add vertices 1 to 6
			for (int i = 0; i < n; i++){
				
				if (i != v){
					
					for (int j = 0; j < n; j++){

						if (j != v && i != j && !marked0[i][j]){
							
							nuu[index - 1] = i*10 + j;
							flownet.addEdge(new FlowEdge(source, index, games[i][j]));
							index++;

							marked0[j][i] = true;
							marked0[i][j] = true;
						
						}
					}
				}
			}

		
			for (int i = 0; i < nuu.length; i++){
				
				int team1_ix = nuu[i] / 10 + index;
				int team2_ix = nuu[i] % 10 + index;
				flownet.addEdge(new FlowEdge(i+1, team1_ix, Double.POSITIVE_INFINITY));
				flownet.addEdge(new FlowEdge(i+1, team2_ix, Double.POSITIVE_INFINITY));
			}

			
			int count = 0;
			int value = 0;
			
			for (int i = 0; i < n; i++){
				if (i != v){
					total = wins[v] + remaining[v] - wins[i]; 
					
					if(total < 0){
						eliminated.add(teams[v]);
						continue;
					}
					
					flownet.addEdge(new FlowEdge(count + index, t, total) );
					count++;
					value += total;
				}
			}

			//find maxflow
			FordFulkerson maxflow = new FordFulkerson(flownet, source, t);

			System.out.println(flownet.toString());

			int maxFlowValue = (int)maxflow.value(); 

			
			if(maxFlowValue >= value){
				

				if(!eliminated.contains(teams[v]) ) //dont add duplicates
					
					eliminated.add(teams[v]);
			
			}

			
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

		BaseballElimination be = new BaseballElimination(s);

		if (be.eliminated.size() == 0)
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}