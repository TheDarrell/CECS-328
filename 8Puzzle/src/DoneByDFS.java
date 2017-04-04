/*
 * Darrell Theng 012722695
 * CECS 328 
 * Problem Set #5 Programming Assignment: 8-Puzzle by DFS
 * Find a solution to an 8-Puzzle with the given initial state
 * and goal state by using Depth First Search.
 * Note: Modified code found at 
 * https://harundharmawan.wordpress.com/2011/05/14/depth-fs-8-puzzle-using-java/
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DoneByDFS {

	String str = ""; // initial state
	String goal = ""; //goal state

	LinkedList <String> openList;//openList
	Map<String,Integer> levelDepth;
	Map<String,String> stateHistory;

	int limit = 100; //counter for limit
	int unique = -1;
	int newValue;//counter for level depth
	int a;

	static String currState;//current State
	boolean solution = false;//flag if solution is exist or not

	/**
	 * Constructor of class
	 * @param str	The representation of initial state
	 * @param goal	The representation of goal state
	 */
	DoneByDFS(String str,String goal){
		openList = new LinkedList <String> ();
		levelDepth = new HashMap<String, Integer>();
		stateHistory = new HashMap<String,String>();
		this.str = str;
		this.goal = goal;
		addToOpenList(str,null);//add root
	}
	
	/**
	 * Find the solution to the 8-puzzle and call to printSolution if found
	 */
	void doSearch (){
		while (!openList.isEmpty()){
			currState = openList.removeFirst();//Retrieve then remove first node of our openList
			if (currState.equals(goal)){ // check if current state is goal state
				solution = true;
				printSolution(currState);// print solutions
				break;
			}else {
				//expand currState then add expanded node to the of openList if not reach limit depth
				//do backtrack if reach limit depth; it is basically just not generate any more node and retrieve head of openList
				if (levelDepth.get(currState) < limit){
					a = currState.indexOf("0");// get index position of 0 (blank)
					//left
					while (a != 0 && a != 3 && a != 6){//check to see if it can move left
						String nextState = currState.substring(0,a-1)+"0"+currState.charAt(a-1)+currState.substring(a+1);//swap blank with destination
						addToOpenList(nextState, currState);//add expanded node to openList
						break;
					}
					//up
					while (a!=0 && a!=1 && a!=2){//check to see if it can move up
						String nextState = currState.substring(0,a-3)+"0"+currState.substring(a-2,a)+currState.charAt(a-3)+currState.substring(a+1);//swap blank with destination
						addToOpenList(nextState, currState);//add expanded node to openList
						break;
					}
					//right
					while(a != 2 && a != 5 && a != 8){//check to see if it can move right
						String nextState = currState.substring(0,a)+currState.charAt(a+1)+"0"+currState.substring(a+2);//swap blank with destination
						addToOpenList(nextState, currState);//add expanded node to openList
						break;
					}
					//down
					while (a != 6 && a != 7 && a != 8) {//check to see if it can move down
						String nextState = currState.substring(0,a)+currState.substring(a+3,a+4)+currState.substring(a+1,a+3)+"0"+currState.substring(a+4);//swap blank with destination
						addToOpenList(nextState, currState);//add expanded node to openList
						break;
					}
				}//end if limit
			}
		}
		if (!solution){
			System.out.println("Solution not yet found!");
		}
	}
	/**
	 * Adding new item to the OpenList
	 * @param newState	present state of the puzzle to add
	 * @param oldState	previous state of the puzzle
	 */
	private void addToOpenList (String newState, String oldState){
		if(!levelDepth.containsKey(newState)){//check repeated state
			newValue = oldState == null ? 0 : levelDepth.get(oldState) + 1;
			unique ++;
			levelDepth.put(newState, newValue);
			openList.addFirst(newState);//add node at the beginning of openList (DepthFS Algorithm)
			stateHistory.put(newState, oldState);
		}
	}
	
	/**
	 * Print out the solution to the 8-puzzle
	 * @param currState	current state of the puzzle
	 */
	void printSolution (String currState){
		if (solution){
			System.out.println("Solution found in " +levelDepth.get(currState)+" step(s)");
		}else{
			System.out.println("Solution not found!");
		}
		String traceState = currState;
		while (traceState != null) {
			System.out.println("Step " + levelDepth.get(traceState) + ":");
			try{
				System.out.print("  ");
				for(int z=0;z<9;z++){
					System.out.print(String.valueOf(traceState.charAt(z)));
					if ((z+1) % 3 == 0){System.out.println();if(z!=8) System.out.print("  ");}
				}
			}catch (NullPointerException e) {}
			traceState = stateHistory.get(traceState);
		}
	}
	public static void main(String[] args)
	{
		/*	String = "012345678"
		 *  Puzzle = 012
		 *           345
		 *           678
		 *  0 is blank space
		 */
		String start = "283164705";
		String goal = "123804765";
		DoneByDFS puzzle = new DoneByDFS(start, goal);
		puzzle.doSearch();
	}
}

