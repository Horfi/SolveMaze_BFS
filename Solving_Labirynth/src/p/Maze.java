package p;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Maze {
	static int width=0;
	static int height=0;
	static Node goal;
	static Node start;
	static File file;
	static char[][] array = new char[width+2][height+2];

	

	public static void main(String[] args) throws FileNotFoundException {
		// directions x,y     x=rows  y = col 
		File();
		Labirynth();
		initialState();
		System.out.println("start : "+start+"  "+"goal : "+ goal);
		solve(start,goal);

	}
	private static void File() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Give file name that contains maze");
		String filee = scan.nextLine();
		file = new File(filee);
	}
	
	public static void Labirynth() throws FileNotFoundException {
		Scanner s = new Scanner(file);
		while(s.hasNextLine()) {
			System.out.println(s.nextLine());	
		}
		System.out.println();
	}
	
	public static void initialState() throws FileNotFoundException {
	    Scanner s = new Scanner(file);
	    
	    // setting width and height
	    while(s.hasNextLine()) {
			String line = s.nextLine();
			height++;
			width = line.length();
		}
	    array = new char[height + 2][width + 2]; // Initialize the array with correct dimensions

	    for (int i = 0; i < height + 2; i++) {
	        for (int j = 0; j < width + 2; j++) {
	            array[i][j] = 'H';
	        }
	    }
	    
	    s=new Scanner(file);
	    
	    for (int i = 1; i < height + 1; i++) { // Use height in the loop condition
	        String line = s.nextLine();
	        for (int j = 1; j < width + 1; j++) {
	            array[i][j] = line.charAt(j - 1);
	            if (array[i][j] == 'A') goal(i, j);
	            if (array[i][j] == 'B') start(i, j);
	        }
	    }
	}

	
	public static void goal(int i, int j) {
		 goal = new Node(i,j);
	}
	
	public static void start(int i, int j) {
		 start = new Node(i,j);
	}
	
	public static void solve(Node start,Node goal){
		Queue<Node> frontier = new LinkedList<>();
		Map<Node,Node> parentMap = new HashMap<Node,Node>();
		frontier.add(start);
		parentMap.put(start, null);
		
		while(!frontier.isEmpty()) {
			Node current = frontier.poll();
			if(current.equals(goal)) {
				reconstructPath(parentMap,start,goal);
				break;
			}
			
			//add valid nodes to frontier
			for(Node neighbour: getNeighbours(current)) {
				if(!parentMap.containsKey(neighbour)) {
					frontier.add(neighbour);
					parentMap.put(neighbour, current);
				}
			}
		}
		
	}
	private static void reconstructPath(Map<Node,Node> parentMap,Node start2, Node goal2) {
		List<Node> path = new ArrayList<>();
		Node current = goal;
		while(current != null) {
			path.add(current);
			current = parentMap.get(current);
		}
		
		Collections.reverse(path);
		// show solution path
		System.out.println("steps taken  : "+path.size());
		
		
		highightPath(path);
		
	}
	 private static void highightPath(List<Node> path) {
		 final String red_back = "\u001B[41m";
		 final String ANSI_RESET = "\u001B[0m";
		for(int i=1;i<path.size()-1;i++) {
			int row = path.get(i).row;
			int col = path.get(i).col;
			array[row][col]='^';
		}
		System.out.println();
		for (int i = 1; i < height + 1; i++) { 
	        for (int j = 1; j < width + 1; j++) {
	        	if(array[i][j]=='^')System.out.print(red_back+array[i][j]+ANSI_RESET);
	        	else System.out.print(array[i][j]);
	        }
	        System.out.println();
	    }
		
	}
	static List<Node> getNeighbours(Node current) {
	    List<Node> neighbours = new ArrayList<>();
	    int i = (int)current.row;
	    int j = (int)current.col;
	    if (i + 1 < width + 2 && array[i + 1][j] != '#'&& array[i + 1][j] != 'H') neighbours.add(new Node(i + 1, j));
	    if (j - 1 >= 0 && array[i][j - 1] != '#'&& array[i][j - 1] != 'H') neighbours.add(new Node(i, j - 1));
	    if (j + 1 < height + 2 && array[i][j + 1] != '#'&& array[i][j + 1] != 'H') neighbours.add(new Node(i, j + 1));
	    if (i - 1 >= 0 && array[i - 1][j] != '#'&& array[i - 1][j] != 'H') neighbours.add(new Node(i - 1, j));
	    return neighbours;
	}
	
	
	
}


