import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class MazeAssignmentGUI implements ActionListener {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;


    static boolean north, south, east, west;
    static int[][] graph;
    static boolean[] visited;

    static boolean[][] genVis;
    static int gen = 0;
    static ArrayList<ArrayList<Integer>> adj;
    static ArrayList<Integer> shortest = new ArrayList<>();
    static int min;
    static int[] lastCoord = new int[2];
    
	static JButton OwnMazeButton = new JButton("Make your own Maze");
	static JButton FileMazeButton = new JButton("Open A File");
	static JButton DoneButton = new JButton("Enter");
	static JButton FileDoneButton = new JButton("Enter File");

	
	static JTextField RowInput = new JTextField();
	static JTextField ColumnInput = new JTextField();
	static JTextField EnterFileName = new JTextField();
	
	static JLabel RowLabel = new JLabel("Enter how many rows you want in your maze");
	static JLabel ColumnLabel = new JLabel("Enter how many columns you want in your maze");
	static JLabel FileLabel = new JLabel("Enter the File Name");


	
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	public MazeAssignmentGUI () {
		frame.setVisible(true);
		frame.setSize(2000, 1000);
		frame.setTitle("Maze");
		frame.add(panel);
		panel.setLayout(null);
		RowLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		ColumnLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		FileLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		
		OwnMazeButton.setBounds(300, 350, 500, 300);
		FileMazeButton.setBounds(1100, 350, 500, 300);
		panel.add(OwnMazeButton);
		panel.add(FileMazeButton);
		OwnMazeButton.addActionListener(this);
		FileMazeButton.addActionListener(this);
		DoneButton.addActionListener(this);
		FileDoneButton.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		MazeAssignmentGUI frame1 = new MazeAssignmentGUI();

	}
	
	public void actionPerformed(ActionEvent e) {
		int choice, rows, cols, rowsFile;
		String fileName;
		
		String command = e.getActionCommand();
		if(command.equals("Make your own Maze")) {
			panel.remove(OwnMazeButton);
			panel.remove(FileMazeButton);
			OwnMazeButton.setVisible(false);
			FileMazeButton.setVisible(false);

			choice = 1;
			RowLabel.setBounds(400, 100, 500, 500);
			RowInput.setBounds(347, 380, 500, 50);
			ColumnLabel.setBounds(1200, 100, 500, 500);
			ColumnInput.setBounds(1160, 380, 500, 50);
			DoneButton.setBounds(750, 500, 500, 200);
			
			panel.add(DoneButton);
			panel.add(RowLabel);
			panel.add(RowInput);
			panel.add(ColumnLabel);
			panel.add(ColumnInput);
			frame.setSize(2000, 999);
			frame.setSize(2000, 1000);
			
			
			
			
		}
		else if (command.equals("Open A File")) {
			panel.remove(OwnMazeButton);
			OwnMazeButton.setVisible(false);
			panel.remove(FileMazeButton);
			FileMazeButton.setVisible(false);

			choice = 2;
			
			FileLabel.setBounds(860, 180, 500, 500);
			EnterFileName.setBounds(700, 450, 500, 50);
			FileDoneButton.setBounds(700, 550, 500, 200);
			panel.add(FileLabel);
			panel.add(EnterFileName);
			panel.add(FileDoneButton);
			
			frame.setSize(2000, 999);
			frame.setSize(2000, 1000);
			
			
		}
		else if (command.equals("Enter File")) {
			panel.remove(FileLabel);
			panel.remove(EnterFileName);
			panel.remove(FileDoneButton);
			FileDoneButton.setVisible(false);
			FileLabel.setVisible(false);
			EnterFileName.setVisible(false);
			frame.remove(panel);
			panel.setVisible(false);
			rows = 0;
			cols = 0;
			fileName = EnterFileName.getText();
			File file = new java.io.File(fileName + ".txt");
			try {
				rows = readRows(file);
			}
			catch (Exception event) {
				System.out.println();
			}
			try {
				cols = readCol(file);
			}
			catch (Exception event) {
				System.out.println();
			}
			char maze[][] = new char[rows][cols];
			try {

				createArray(maze, file);
				findPath(maze);
			}
			
			catch (Exception event) {
				System.out.println();
			}

			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(maze.length, maze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < maze.length; row++) {
	            for (int col = 0; col <= maze[0].length-1; col++) {
	                JLabel label = makeLabel(maze[row][col]);
	                frame.add(label);
	            }
	        }

			frame.pack();
	        frame.setVisible(true);
		}
		else if (command.equals("Enter")) {
			rows = Integer.parseInt(RowInput.getText());
			cols = Integer.parseInt(ColumnInput.getText());
			panel.remove(RowLabel);
			panel.remove(ColumnLabel);
			panel.remove(RowInput);
			panel.remove(ColumnInput);
			panel.remove(DoneButton);
			frame.remove(panel);
			RowLabel.setVisible(false);
			ColumnLabel.setVisible(false);
			RowInput.setVisible(false);
			ColumnInput.setVisible(false);
			DoneButton.setVisible(false);
			panel.setVisible(false);
			char [][] maze = new char[rows][cols];
			drawMaze(maze);
			findPath(maze);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(maze.length, maze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < maze.length; row++) {
	            for (int col = 0; col <= maze[0].length-1; col++) {
	                JLabel label = makeLabel(maze[row][col]);
	                frame.add(label);
	            }
	        }

			
			frame.pack();
	        frame.setVisible(true);

		}
	}
	public JLabel makeLabel(char c) {

        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(c) {
            case 'B':
            	label.setBackground(Color.BLUE);
                break;
            case 'X':
                label.setBackground(Color.GREEN);
                break;
            case 'O':
            	label.setBackground(Color.WHITE);
            	break;
            case 'S': label.setBackground(Color.RED);
            	break;
            case '+': label.setBackground(Color.YELLOW);
            	break;
            default:
            	label.setBackground(Color.YELLOW);
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }
	public static String input() throws IOException {
        //method to split input into tokens to allow faster input
        while (st == null || !st.hasMoreTokens()) {
            //while there no tokens or the string tokenizer does not have more tokens left
            st = new StringTokenizer(br.readLine().trim());
            //string tokenizer set to read line and trim whitespace
        }
        return st.nextToken();
        //returns the next token
    }

	public static int readRows(File file) throws Exception {
		int rowsFile;
		Scanner input = new Scanner(file);
		rowsFile = Integer.parseInt(input.nextLine());
		return rowsFile;
	}

	public static int readCol(File file) throws Exception {
		int columnsFile;
		Scanner input = new Scanner(file);
		input.nextLine();
		columnsFile = Integer.parseInt(input.nextLine());
		return columnsFile;
	}

	public static void createArray(char[][] maze, File file) throws Exception {
		char c;
		Scanner input = new Scanner(file);
		for (int i = 0; i < 5; i++) {
			input.nextLine();
		}
		for (int i = 0; i < maze.length; i++) {
			input.nextLine();
			for (int j = 0; j < maze[0].length; j++) {
				c = input.findInLine(".").charAt(0);
				maze[i][j] = c;
			}
		}

	}

	 public static int readInt() throws IOException {
	        return Integer.parseInt(input());
	    }

	    static void drawMazeBarriers(char[][]maze){
	        genVis = new boolean[maze.length][maze[0].length];
	        for (int i = 0; i < maze.length; i++) {
	            for (int j = 0; j < maze[i].length; j++) {
	                if(j==0 || j==maze[i].length-1 || i==0 || i== maze.length-1){
	                    maze[i][j] = 'B';
	                    genVis[i][j] = true;
	                    gen++;
	                }
	            }
	        }
	    }

	    static int[] drawExit(char[][]maze){

	        int exitPos = (int) (Math.random()*4+1);
	        int[] exitCoord = new int[2];
	        /**
	         * If exit position is 1, the exit will be at the top
	         * if exit position is 2, the exit wil be on the bottom
	         * if exit position is 3, the exit will be on the left
	         * if exit position is 4, the exit will on the right
	         */


	        if(exitPos==1){ //exit will be on the top
	            int exitCell = (int)(Math.random()*(maze[0].length-2)+1);
	            maze[0][exitCell] = 'X';
	            exitCoord[0] = 0;
	            exitCoord[1] = exitCell;

	        } else if(exitPos==2){
	            int exitCell = (int)(Math.random()*(maze[0].length-2)+1);
	            maze[maze.length-1][exitCell] = 'X';
	            exitCoord[0] = maze.length-1;
	            exitCoord[1] = exitCell;

	        }else if(exitPos==3){
	            int exitCell = (int)(Math.random()*(maze.length-2)+1);
	            maze[exitCell][0] = 'X';
	            exitCoord[0] = exitCell;
	            exitCoord[1] = 0;

	        }else if(exitPos==4){
	            int exitCell = (int)(Math.random()*(maze.length-2)+1);
	            maze[exitCell][maze[0].length-1] = 'X';
	            exitCoord[0] = exitCell;
	            exitCoord[1] = maze[0].length-1;
	        }
	        gen++;
	        genVis[exitCoord[0]][exitCoord[1]] = true;
	        return exitCoord;
	    }

	    static void drawMaze(char[][] maze){
	        drawMazeBarriers(maze);
	        int exitCoord[] = drawExit(maze);
	        int startCoord[] = drawExitPath(maze, exitCoord);
	        lastCoord[0] = startCoord[0];
	        lastCoord[1] = startCoord[1];

	        loopDraw(maze);

	        drawWalls(maze);

	    }

	    static int[] drawExitPath(char[][] maze, int[] exitCoord){
	        int[] currentPos = new int[2];

	        currentPos[0] = exitCoord[0];
	        currentPos[1] = exitCoord[1];

	        drawFirstCell(maze, currentPos);

	        int pathLength = (int) (Math.random()*(maze.length*maze[0].length)+(maze.length+ maze[0].length)/2);

	        drawPath(pathLength, currentPos, maze);

	        int[] startPos = new int[2];
	        startPos[0] = currentPos[0];
	        startPos[1] = currentPos[1];
	        maze[startPos[0]][startPos[1]] = 'S';
	        genVis[startPos[0]][startPos[1]] = true;
	        return startPos;

	    }

	    static int[] findExitSide(char[][]maze){
	        int[] currentPos = new int[2];
	        int[] delta = new int[2];
	        for (int i = 0; i < maze.length; i++) {
	            for (int j = 0; j < maze[0].length; j++) {
	                if(maze[i][j]=='X'){
	                    currentPos[0] = i;
	                    currentPos[1] = j;
	                }
	            }
	        }

	        if(currentPos[0]== maze.length-1) {
	            delta[0] = -1;

	        } else if (currentPos[0]==0){
	            delta[0] = 1;

	        } else if (currentPos[1]==0){
	            delta[1] = 1;

	        } else if (currentPos[1]==maze[0].length-1){
	            delta[1] = -1;

	        }
	        return delta;
	    }

	    static int[] findStartCell(char[][]maze){
	        int[] startPos = new int[2];
	        for (int i = 0; i < maze.length; i++) {
	            for (int j = 0; j < maze[0].length; j++) {
	                if(maze[i][j]=='S'){
	                    startPos[0] = i;
	                    startPos[1] = j;
	                }
	            }
	        }
	        return startPos;
	    }

	    static void drawFirstCell(char[][]maze, int[]currentPos){
	        int[] delta = findExitSide(maze);
	        currentPos[0]+=delta[0];
	        currentPos[1]+=delta[1];
	        maze[currentPos[0]][currentPos[1]] = 'O';
	        genVis[currentPos[0]][currentPos[1]] = true;
	    }

	    static void loopDraw(char[][]maze){
	        for (int i = 1; i < maze.length-1; i++) {
	            for (int j = 1; j < maze[0].length-1; j++) {
	                if(!genVis[i][j]){
	                    genVis[i][j] = true;
	                    int cur[] = new int[2];
	                    cur[0] = i;
	                    cur [1] = j;
	                    int pathLength = (int) (Math.random()*(maze.length+maze[0].length)+1);
	                    drawPath(pathLength, cur, maze);
	                }
	            }
	        }
	    }

		static void drawPath(int pathLength, int[]currentPos, char [][]maze){
		int[][] moves = {
				{0, 1},
				{0, -1},
				{1, 0},
				{-1, 0}
		};
		for (int i = 0; i < pathLength; i++) {

			int moveNum = (int)(Math.random()*3);

			int [] temp = new int[2];
			temp[0]  = currentPos[0] + moves[moveNum][0];
			temp[1] = currentPos[1] + moves[moveNum][1];
			if(validCell(temp, maze, moves[moveNum])){
				currentPos[0]+=moves[moveNum][0];
				currentPos[1]+=moves[moveNum][1];
				maze[currentPos[0]][currentPos[1]] = 'O';
			}

		}

	}
	    static boolean validCell(int []currentPos, char[][]maze, int[]moveMap){
	        boolean notOnBorder = notBorder(currentPos, maze);
	        if(notOnBorder){
	            int[] temp = new int[2];
	            temp[0] = currentPos[0]+moveMap[0];
	            temp[1] = currentPos[1]+moveMap[1];
	            if(notBorder(temp, maze)){
	                if(maze[temp[0]][temp[1]]=='O') return false;
	                if(moveMap[0]!=0){
	                    if(maze[currentPos[0]][currentPos[1]+1]=='O') return false;
	                    if(maze[currentPos[0]][currentPos[1]-1]=='O') return false;
	                } else if(moveMap[1]!=0){
	                    if(maze[currentPos[0]+1][currentPos[1]]=='O') return false;
	                    if(maze[currentPos[0]-1][currentPos[1]]=='O') return false;
	                }
	            }
	        } else if(!notOnBorder) return false;
	        return true;
	    }
	    static boolean notBorder(int []currentPos, char[][]maze){
	        if(maze[currentPos[0]][currentPos[1]] == 'B' || maze[currentPos[0]][currentPos[1]] == 'X'){
	            return false;
	        }
	        else if(maze[currentPos[0]][currentPos[1]]=='S'){
	            return false;
	        }
	        else return true;
	    }

	    static void drawWalls(char[][]maze){
	        for (int i = 0; i < maze.length; i++) {
	            for (int j = 0; j < maze[i].length; j++) {
	                if(maze[i][j] != 'S' && maze[i][j]!='O' && maze[i][j] != 'X' && maze[i][j] != 'B'){
	                    maze[i][j] = 'B';
	                }
	            }
	        }
	    }


	    static void findPath(char[][]maze){

	        int[] startPos = findStartCell(maze);
	        boolean startExitConnected = isStartExitConnected(maze, startPos[1], startPos[0]);
	        if(startExitConnected){
	            maze[startPos[0]][startPos[1]] = '+';
	        }
	        else {
	            visited = new boolean[maze.length * maze[0].length];
	            min = maze.length * maze[0].length; //there is at most, r * c total cells
	            graph = createGraph(maze);
	            adj = createList(graph);


	            if (adj.get(2).size() == 0) {
	                shortest.add(1);
	            } else if (adj.get(1).size() == 0) {
	                shortest.add(2);
	            } else traverse(1);

	            System.out.println();
	            shortest.remove(0);
	            for (int i = 0; i < graph.length; i++) {
	                for (int j = 0; j < graph[0].length; j++) {
	                    if(shortest.contains(graph[i][j])){
	                        maze[i][j] = '+';
	                    }
	                }
	            }
	        }
	    }

	    static int[][] createGraph (char[][]maze){
	        int nextCell = 3;
	        int[] delta = findExitSide(maze);
	        int[][]graph = new int[maze.length][maze[0].length];
	        for (int i = 0; i < maze.length; i++) {
	            for (int j = 0; j < maze[0].length; j++) {
	                if(maze[i][j]=='S')
	                {
	                    graph[i][j] = 1;
	                }
	                else if (maze[i][j]=='X')
	                {
	                    graph[i+delta[0]][j+delta[1]] = 2;
	                    graph[i][j] = 0;
	                }
	                else if(maze[i][j]=='O' && graph[i][j]!=2) {
	                    graph[i][j] = nextCell;
	                    nextCell++;
	                }
	            }
	        }
	        return graph;
	    }
	    static ArrayList<ArrayList<Integer>> createList(int[][]graph){
	        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
	        int maxCells = (graph.length * graph[0].length);
	        for (int i = 0; i < maxCells; i++) {
	            adj.add(new ArrayList<>());
	        }
	        for(int i = 0; i<graph.length; i++){
	            for(int j = 0; j< graph[0].length; j++){
	                int temp = graph[i][j];
	                if(temp!=0){
	                    //perform 4 direction check for adjacent
	                    checkDir(graph, j, i);


	                    if(north) {

	                        adj.get(graph[i][j]).add(graph[i-1][j]);
	                    }
	                    if(south){

	                        adj.get(graph[i][j]).add(graph[i+1][j]);
	                    }if(east){

	                        adj.get(graph[i][j]).add(graph[i][j+1]);
	                    }if(west){

	                        adj.get(graph[i][j]).add(graph[i][j-1]);
	                    }
	                }
	            }
	        }
	        return adj;
	    }

	    static void checkDir(int[][]graph, int x, int y){
	        north = false;
	        south = false;
	        east = false;
	        west = false;
	        if(graph[y-1][x]!=0) north = true;
	        if(graph[y+1][x]!=0) south = true;
	        if(graph[y][x+1]!=0) east = true;
	        if(graph[y][x-1]!=0) west = true;
	    }

	    static boolean isStartExitConnected(char[][]maze, int x, int y){
	        boolean startNextToExit = false;
	        if(maze[y-1][x]=='X') startNextToExit = true;
	        if(maze[y+1][x]=='X') startNextToExit = true;
	        if(maze[y][x+1]=='X') startNextToExit = true;
	        if(maze[y][x-1]=='X') startNextToExit = true;
	        return startNextToExit;
	    }

	    static ArrayList<Integer> path = new ArrayList<>();

	    static void traverse(int node){
	        if(node==2){
	            if(path.size()<min){
	                path.add(node);
	                shortest.clear();
	                visited[2] = false;
	                for(int i = 0; i<path.size(); i++){
	                    shortest.add(path.get(i));
	                }
	                min = shortest.size();
	            }
	        }
	        else if(!visited[node]){

	            visited[node] = true;
	            path.add(node);
	            for(int i:adj.get(node)){
	                traverse(i);
	            }

	            if(path.size()!=0) {
	                //dont want last element or when path is empty
	                int index = path.indexOf(node);
	                int prevSize = path.size();
	                for (int i = index; i < prevSize; i++) {
	                    visited[path.get(index)] = false;
	                    path.remove(path.get(index));
	                }
	            }
	        }
	    }
}



