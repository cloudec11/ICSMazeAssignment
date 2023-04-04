/**Authors: Eric Chen, Sam Lee
 * Description: Maze assignment for Mr A ICS4U class period 2
 * Date: 2023-04-03
 */

import javax.swing.*; //import java libraries containing code for the graphical user interface
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MazeAssignmentGUI implements ActionListener {

	//below are the global variables for use throughout the program
	static boolean north, south, east, west; //variables for valid direction
	static boolean hasStart = false, hasExit = false; //variables for if maze file has starting and exit cells
	static int[][] graph; //2d array for integer mapping of the character maze
	static boolean[] visited; //array for storing whether or not a number has been visited
	static char borderChar = 'B'; //character for the borders and barriers
	static char pathChar = 'O';//character for the open path
	static char startChar = 'S';//character for starting cell
	static char exitChar = 'X';//character for exit cell
	static boolean[][] cellChecked;//boolean 2d array to check if a cell has been checked by the path generation algorithm
	static int numCellChecked = 0;//integer for total checks by the path generation algorithm
	static ArrayList<ArrayList<Integer>> adjacencyList;//arraylist of arraylists of integers to store the integers connected to an integer in the mapped 2d integer array graph
	/**
	 * This list basically stores all connected integers at a certain index.
	 * That index will be a certain integer in the 2D array graph. So at index graph[y][x]
	 * the list will contain all integers connected to that number
	 */
	static ArrayList<Integer> shortest = new ArrayList<>();//arraylist storing the shortest path in integers
	static int min;//integer for the size of the current shortest path
	static char mainMaze[][]; //used to create a copy of the maze so that the user can toggle the shortest path

	static JButton OwnMazeButton = new JButton("Make your own Maze"); //creating global object that acts as a button for user input, the same applies for all other static JButtons
	static JButton FileMazeButton = new JButton("Open A File");
	static JButton DoneButton = new JButton("Enter");
	static JButton FileDoneButton = new JButton("Enter File");
	static JButton RandomMaze  = new JButton("Create Maze with no Guaranteed Path");
	static JButton FindPath = new JButton("Find Shortest Path");
	static JButton Legend = new JButton("Open Legend");
	static JButton ReturnMaze = new JButton("Return to Maze");



	static JTextField RowInput = new JTextField(); //creating global object that acts as a text field that allows users to input required numbers, in this case the number of rows for the creation of random maze
	static JTextField ColumnInput = new JTextField();//creating global object that acts as a text field that allows users to input required numbers, in this case the number of columns for the creation of random maze
	static JTextField EnterFileName = new JTextField();//creating global object that acts as a text field that allows users to input required numbers, in this case the name of the file of which they desire to access.

	static JLabel RowLabel = new JLabel("Enter how many rows you want in your maze"); //creating global object that acts as a label that displays text/prompts to users providing them instructions on how to operate the program. This applies for all labels
	static JLabel ColumnLabel = new JLabel("Enter how many columns you want in your maze");
	static JLabel FileLabel = new JLabel("Enter the File Name");
	static JLabel LegendLabel = new JLabel("Blue = Border           Red  = Starting Point            Green = Exit              White = Open Path          Yellow = Starting Point    ");
	static JLabel NoPathLabel = new JLabel("This maze has no path");




	static JFrame frame = new JFrame(); //creates window/frame for the program
	static JPanel panel = new JPanel(); //creates a panel that will be added to the frame which will allows for the positioning of other objects like buttons and labels.

	public MazeAssignmentGUI() { //start of the MazeAssignmentGUI GUI
		frame.setVisible(true); //sets the frame as visible thus allowing the user to actually see the window
		frame.setSize(2000, 1000); //sets the size of the frame to 2000, 1000 pixels
		frame.setTitle("Maze"); //sets the title of the window to the name maze
		frame.add(panel); //adds the panel to the frame
		panel.setLayout(null); //sets the panel's layout to null (declaring that the panel has no layouts) thus allowing the manual positioning of buttons and labels.
		RowLabel.setFont(new Font("Arial", Font.PLAIN, 20)); //setting the font of the labels to Arial, size 20. Same goes for the other labels that have set fonts.
		ColumnLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		FileLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		LegendLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		NoPathLabel.setFont(new Font("Arial", Font.PLAIN, 20));

		OwnMazeButton.setBounds(300, 350, 500, 300); //sets the bounds of the button, thus declaring the positioning of the button on the panel. x value of 300, y value of 350, width of 500, and height of 300
		FileMazeButton.setBounds(1100, 350, 500, 300); //all other setBounds follow the same logic, x, y, width, height
		panel.add(OwnMazeButton); //adding the OwnMazeButton and FileMazeButton to their respective positions onto the panel
		panel.add(FileMazeButton);
		OwnMazeButton.addActionListener(this); //adding action listeners that allows the program to detect if the corresponding button is clicked and then it will perform an action if it detects the user clicks the button.
		FileMazeButton.addActionListener(this);
		DoneButton.addActionListener(this);
		FileDoneButton.addActionListener(this);
		RandomMaze.addActionListener(this);
		FindPath.addActionListener(this);
		Legend.addActionListener(this);
		ReturnMaze.addActionListener(this);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets the default close operation to closing the JFrame window.
	}

	public static void main(String[] args) { //start of main method header
		MazeAssignmentGUI frame1 = new MazeAssignmentGUI(); //this line initiates the graphical user interface

	}

	public void actionPerformed(ActionEvent e) { //action performed method, this method will listen for if buttons are clicked and then will perform a suitable action
		int rows, cols; //initialization of variables used in later statements, rows to store row value for maze and cols to store column value for maze.
		String fileName; //declares a string variable that is used to store user input when asked for a file name

		String command = e.getActionCommand(); //declaration of string command that will store the string name of the button pressed.
		if(command.equals("Make your own Maze")) { // if they press the button that says Make your own Maze then perform the code. All command.equals do the same thing, whichever button is pressed, it will perform the if/else if statement that has the corresponding text located on the button.
			panel.remove(OwnMazeButton); //removes OwnMazeButton and FileMazeButton so as basically to refresh the screen so other thigns can be added.
			panel.remove(FileMazeButton);
			OwnMazeButton.setVisible(false); //sets them both to invisible so the user can't see them
			FileMazeButton.setVisible(false);

			
			RowLabel.setBounds(400, 100, 500, 500); //sets the position of the labels and buttons
			RowInput.setBounds(347, 380, 500, 50);
			ColumnLabel.setBounds(1200, 100, 500, 500);
			ColumnInput.setBounds(1160, 380, 500, 50);
			DoneButton.setBounds(750, 500, 500, 200);
			RandomMaze.setBounds(750, 750, 500, 200);

			panel.add(DoneButton); //adds the corresponding buttons and labels.
			panel.add(RowLabel);
			panel.add(RowInput);
			panel.add(ColumnLabel);
			panel.add(ColumnInput);
			panel.add(RandomMaze);
			
			frame.setSize(2000, 999); //this changes the frame size then changes it back to the original. This is done because without it, you would need to interact/click other parts of the window in order to perform the actions without delay. This way, it can refresh the page without delay
			frame.setSize(2000, 1000);




		}
		else if (command.equals("Open A File")) {
			panel.remove(OwnMazeButton); //removes OwnMazeButton and FileMazeButton and sets both to invisible
			OwnMazeButton.setVisible(false);
			panel.remove(FileMazeButton);
			FileMazeButton.setVisible(false);


			FileLabel.setBounds(860, 180, 500, 500); //sets position and adds to panel
			EnterFileName.setBounds(700, 450, 500, 50);
			FileDoneButton.setBounds(700, 550, 500, 200);
			panel.add(FileLabel);
			panel.add(EnterFileName);
			panel.add(FileDoneButton);

			frame.setSize(2000, 999);//for refreshing the page without delay
			frame.setSize(2000, 1000);


		}
		else if (command.equals("Enter File")) {
			panel.remove(FileLabel); //removing labels and buttons 
			panel.remove(EnterFileName);
			panel.remove(FileDoneButton);
			frame.remove(panel);
			FileDoneButton.setVisible(false); //setting labels and buttons invisible
			FileLabel.setVisible(false);
			EnterFileName.setVisible(false);
			panel.setVisible(false);
			rows = 0; //initializing rows and cols
			cols = 0;
			fileName = EnterFileName.getText(); //this grabs whatever they input into the text field asking them for user input
			File file = new File(fileName + ".txt"); //grabs the file that they entered from the computer. Needs to be in the same folder as the src contianing the class.
			try { //try and catch to read rows from the file, read columns from the file
				rows = readRows(file);
				cols = readCol(file);
				mainMaze = new char[rows][cols]; //once we have the rows and columns, a the two dimensional mainMaze array is now initialized with the corresponding rows and columns as dimensions.

				readChars(file);
				createArray(mainMaze, file);
			}

			catch (Exception event) {
				System.out.println();
			}
			
			
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(0, mainMaze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < mainMaze.length; row++) {
				for (int col = 0; col <= mainMaze[0].length-1; col++) {
					JLabel label = makeLabel(mainMaze[row][col]);
					frame.add(label);
				}
			}
			frame.add(FindPath);
			frame.add(Legend);
			frame.pack();
			frame.setVisible(true);
		}
		else if (command.equals("Create Maze with no Guaranteed Path")) {
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
			mainMaze = new char[rows][cols];
			noGuranteePathMaze(mainMaze);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(0, mainMaze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < mainMaze.length; row++) {
				for (int col = 0; col <= mainMaze[0].length-1; col++) {
					JLabel label = makeLabel(mainMaze[row][col]);
					frame.add(label);
				}
			}
			frame.add(FindPath);
			frame.add(Legend);
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
			mainMaze = new char[rows][cols];
			drawMaze(mainMaze);


			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(mainMaze.length + 1, mainMaze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < mainMaze.length; row++) {
				for (int col = 0; col <= mainMaze[0].length-1; col++) {
					JLabel label = makeLabel(mainMaze[row][col]);
					frame.add(label);
				}
			}
			frame.add(FindPath);
			frame.add(Legend);
			

			frame.pack();
			frame.setVisible(true);

		}
		else if (command.equals("Find Shortest Path")) {
			
			char tempMaze[][] = new char[mainMaze.length][mainMaze[0].length];
			for (int i = 0; i < tempMaze.length; i++) {
				for (int j = 0; j < tempMaze[0].length; j++) {
					tempMaze[i][j] = mainMaze[i][j];
				}
			}
			frame.setSize(2000, 999);
			frame.setSize(2000, 1000);
			findPath(tempMaze);
			if(shortest.size()==0){
				frame.getContentPane().removeAll();
				frame.setLayout(new GridLayout(0, 2));
				frame.add(NoPathLabel);
				frame.add(ReturnMaze);
			}
			else {
				frame.getContentPane().removeAll();
				

	
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new GridLayout(0, tempMaze[0].length));
				frame.setLocationRelativeTo(null);
				for (int row = 0; row < tempMaze.length; row++) {
					for (int col = 0; col <= tempMaze[0].length-1; col++) {
						JLabel label = makeLabel(tempMaze[row][col]);
						frame.add(label);
					}
				}
				frame.add(FindPath);
				frame.add(Legend);
				frame.pack();
			}
		}
		else if (command.equals("Open Legend")) {
			frame.getContentPane().removeAll();
			frame.setLayout(new GridLayout(0, 1));
			frame.setSize(2000, 999);
			frame.setSize(2000, 1000);

			frame.add(LegendLabel);
			frame.add(ReturnMaze);
		}
		else if (command.equals("Return to Maze")) {
			frame.getContentPane().removeAll();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(0, mainMaze[0].length));
			frame.setLocationRelativeTo(null);
			for (int row = 0; row < mainMaze.length; row++) {
				for (int col = 0; col <= mainMaze[0].length-1; col++) {
					JLabel label = makeLabel(mainMaze[row][col]);
					frame.add(label);
				}
			}
			frame.add(FindPath);
			frame.add(Legend);
			

			frame.pack();
			frame.setVisible(true);
		}
	}
	

	public JLabel makeLabel(char c) {
		JLabel label= new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setPreferredSize(new Dimension(40, 40));
		if(c==borderChar){
			label.setBackground(Color.BLUE);
		} else if(c==exitChar){
			label.setBackground(Color.GREEN);
		} else if(c==pathChar){
			label.setBackground(Color.WHITE);
		} else if(c==startChar) {
			label.setBackground(Color.RED);
		} else if(c=='+'){
			label.setBackground(Color.YELLOW);
		}
		label.setOpaque(true);
		label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		return label;
	}
	public static void readChars(File file) throws IOException{
		/**
		 * void method to read the characters for the border, open path, start, and exit
		 * @param file the file to read from
		 */
		Scanner input = new Scanner(file);
		input.nextLine();
		input.nextLine();
		//these grab the next line as a string and the first character of the string
		borderChar = input.nextLine().charAt(0);
		pathChar = input.nextLine().charAt(0);
		startChar = input.nextLine().charAt(0);
		exitChar = input.nextLine().charAt(0);
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
				if(c==startChar) hasStart = true;
				if(c==exitChar) hasExit = true;
				
			}
		}

	}


	static void drawMazeBarriers(char[][]maze){
		/**
		 * void method to draw the borders of the maze
		 * @param maze the 2d character array that contains the maze
		 */
		cellChecked = new boolean[maze.length][maze[0].length]; //intialize the boolean array that contains whether or not the cells that have been checked

		for (int i = 0; i < maze.length; i++) {//loop through the maze
			for (int j = 0; j < maze[i].length; j++) {
				if(j==0 || j==maze[i].length-1 || i==0 || i== maze.length-1){ //if the first or last column or row
					maze[i][j] = borderChar; //set the current element to the border character
					cellChecked[i][j] = true; //set the current cell to true since we checked it when we created the border
					numCellChecked++; //increment since we checked a cell
				}
			}
		}
	}

	static int[] drawExit(char[][]maze){
		/**
		 * draws the exit cell by randomly selecting one of the 4 borders and then randomly slecting an index in
		 * that border to be the exit cell. The random number cannot be the last or first element to prevent
		 * the exit being in a corder
		 *
		 * @param maze the 2d char array for the maze
		 * @return 		the x and y cooridnates stored in an arrya of size 2
		 * 				with thte y cooridnate first since to access a 2d array using x y coordinates,
		 * 				the y comes first
		 */
		hasExit = true; //true since we have an exit
		int exitPos = (int) (Math.random()*4+1); //random int from 1-4
		int[] exitCoord = new int[2]; //array for storing the x and y coordinates of the exit
		/**
		 * If exit position is 1, the exit will be at the top
		 * if exit position is 2, the exit wil be on the bottom
		 * if exit position is 3, the exit will be on the left
		 * if exit position is 4, the exit will on the right
		 */


		if(exitPos==1){ //exit will be on the top
			int exitCell = (int)(Math.random()*(maze[0].length-2)+1);
			maze[0][exitCell] = exitChar;
			exitCoord[0] = 0;
			exitCoord[1] = exitCell;

		} else if(exitPos==2){ //exit will be on the bottom
			int exitCell = (int)(Math.random()*(maze[0].length-2)+1);
			maze[maze.length-1][exitCell] = exitChar;
			exitCoord[0] = maze.length-1;
			exitCoord[1] = exitCell;

		}else if(exitPos==3){ //exit will be on the left
			int exitCell = (int)(Math.random()*(maze.length-2)+1);
			maze[exitCell][0] = exitChar;
			exitCoord[0] = exitCell;
			exitCoord[1] = 0;

		}else if(exitPos==4){ //exit will be on the right
			int exitCell = (int)(Math.random()*(maze.length-2)+1);
			maze[exitCell][maze[0].length-1] = exitChar;
			exitCoord[0] = exitCell;
			exitCoord[1] = maze[0].length-1;
		}
		numCellChecked++; //we checked a cell exit
		cellChecked[exitCoord[0]][exitCoord[1]] = true;//is true at exit since we checked it
		return exitCoord;//return the coordinates
	}

	static void drawMaze(char[][] maze){
		//void method that calls all the mehtod involved in drawing a maze
		drawMazeBarriers(maze);
		int exitCoord[] = drawExit(maze);
		int startCoord[] = drawExitPath(maze, exitCoord);


		loopDraw(maze);

		drawWalls(maze);

	}

	static void noGuranteePathMaze(char[][]maze){
		//voi dmethod that calls all the methods involved in drawing a maze except the guranteed exit path
		drawMazeBarriers(maze);
		int exitCoord[]=drawExit(maze);
		loopDraw(maze);
		selectStart(maze);
		drawWalls(maze);
	}
	static void selectStart(char[][]maze){
		//randomyl geenrates and x and y coordinate for the starting cell not on the border
		int y = (int)Math.random()*(maze.length-2)+1;
		int x = (int)Math.random()*(maze[0].length-2)+1;
		maze[y][x] = startChar;//set to the starting character
		hasStart = true;//we have a starting cell
	}
	static int[] drawExitPath(char[][] maze, int[] exitCoord){
		/**
		 * draws a path from the exit cell and returtns the end of the path as the starting cell
		 *
		 * @param maze the 2d character maze
		 * @param exitCoord the coordinates of the exit cell
		 * @returns the coordinates of the start cell in an integer array
		 */
		int[] currentPos = new int[2];

		currentPos[0] = exitCoord[0];
		currentPos[1] = exitCoord[1];

		drawFirstCell(maze, currentPos);//draws the first cell with the current cooridnates as the pass by reference array

		//after drwaing the first cell, the current position will have changed due to the nature of passing an array by reference

		int pathLength = (int) (Math.random()*(maze.length*maze[0].length)+(maze.length+ maze[0].length)/2);//random integer from half the row+col lengeths to the product of the row and columns

		drawPath(pathLength, currentPos, maze); //draws a path at current coordinates of size length in the maze

		int[] startPos = new int[2];
		startPos[0] = currentPos[0];
		startPos[1] = currentPos[1];
		maze[startPos[0]][startPos[1]] = startChar; //sets the last cell to the starting char
		cellChecked[startPos[0]][startPos[1]] = true;//chceked
		hasStart = true;//has a starting cell
		return startPos;//returns the start cooridnates

	}

	static int[] findExitSide(char[][]maze){
		/**
		 * finds the direction the exit cell is from the origin
		 * for example, if the exit is right border, the direction is 0, 1 since the change in y wil be 0 and the x needs to be increased
		 * aka the "delta" for the exit side
		 * @param maze the 2d char maze
		 * @return the delta integer array
		 */
		int[] currentPos = new int[2]; //array to store the iterated over coordinate
		int[] delta = new int[2]; //the array to store the delta

		for (int i = 0; i < maze.length; i++) { //loop through the maze
			for (int j = 0; j < maze[0].length; j++) {
				if(maze[i][j]==exitChar){ //if current element is the exit character
					currentPos[0] = i; //store the y and x coordinates
					currentPos[1] = j;
				}
			}
		}

		if(currentPos[0]== maze.length-1) { //if at the bottom row
			delta[0] = -1;

		} else if (currentPos[0]==0){ //if top row
			delta[0] = 1;

		} else if (currentPos[1]==0){ //if left col
			delta[1] = 1;

		} else if (currentPos[1]==maze[0].length-1){ //if last col
			delta[1] = -1;

		}
		return delta; //return the delta
	}

	static int[] findStartCell(char[][]maze){
		/**
		 * method to find the starting cell
		 */
		int[] startPos = new int[2];
		for (int i = 0; i < maze.length; i++) { //loop through the maze
			for (int j = 0; j < maze[0].length; j++) {
				if(maze[i][j]==startChar){ //if found the starting char
					startPos[0] = i; //stores its coordinates
					startPos[1] = j;
				}
			}
		}
		return startPos; //return the cooridnatesd in array
	}

	static void drawFirstCell(char[][]maze, int[]currentPos){
		//method to draw the first cell suing the delta

		int[] delta = findExitSide(maze);
		currentPos[0]+=delta[0]; //after appliyng the delta x and y coordinates, we will be at the firstmost cell that is connected to the exit
		currentPos[1]+=delta[1];
		maze[currentPos[0]][currentPos[1]] = pathChar; //now we can set that cell to be an open path
		cellChecked[currentPos[0]][currentPos[1]] = true; //checked the cell
	}

	static void loopDraw(char[][]maze){
		//void method to loop for all cells and draw paths for each cell if valid
		for (int i = 1; i < maze.length-1; i++) {//loop through maze exlcuding borders
			for (int j = 1; j < maze[0].length-1; j++) {
				if(!cellChecked[i][j]){ //if not checked
					cellChecked[i][j] = true; //check that cell
					int cur[] = new int[2];
					cur[0] = i;
					cur [1] = j;
					int pathLength = (int) (Math.random()*(maze.length+maze[0].length)+1); //random integer for pathj lenghth from 1 to the row+col
					drawPath(pathLength, cur, maze);//calls the drawing method
				}
			}
		}
	}

	static void drawPath(int pathLength, int[]currentPos, char [][]maze){
		/**
		 * draws the path from a current coordinate and attempts to draw a certian number of times
		 * @param pathLength the integer to attempt to draw this many cells from current coordinates
		 * @param currentPos the current coordinate
		 * @param maze the 2d char maze
		 */
		int[][] moves = {
				//array of movement maps
				/**
				 * for example, moves[1] is {0, 1} this means move y + 0 and x + 1 which will move the current cooridnate right by 1
				*/
				{0, 1},
				{0, -1},
				{1, 0},
				{-1, 0}
		};
		for (int i = 0; i < pathLength; i++) { //loop for pathLenth times

			int moveNum = (int)(Math.random()*3); //randomly gernate from 0-3 to access teh moves map array

			int [] temp = new int[2];
			temp[0]  = currentPos[0] + moves[moveNum][0]; //apply the movement in y to current coordinate
			temp[1] = currentPos[1] + moves[moveNum][1]; //apply yhe mvoement in x
			if(validCell(temp, maze, moves[moveNum])){ //check if the coordinates after movement are valid (not a border and not creating a clump of open paths)
				currentPos[0]+=moves[moveNum][0]; //if it is valid then we can move there
				currentPos[1]+=moves[moveNum][1];
				maze[currentPos[0]][currentPos[1]] = pathChar;//set the current coorindate to the open path character
			}

		}

	}
	static boolean validCell(int []currentPos, char[][]maze, int[]moveMap){
		/**
		 * checks if a coordinate is a valid cell for an open path in a certain direction
		 * @param currentPos the currewnt coordinate
		 * @param maze the 2D char maze
		 * @param moveMap the movement being applied to the y and x coordinate respectively to the current coordinates
		 */
		boolean notOnBorder = notBorder(currentPos, maze);
		//checks if teh currenty positon is on a border
		if(notOnBorder){
			//if not on a border we cna check for already open paths to avoid clumping
			int[] temp = new int[2];
			temp[0] = currentPos[0]+moveMap[0];
			temp[1] = currentPos[1]+moveMap[1];
			if(notBorder(temp, maze)){
				if(maze[temp[0]][temp[1]]==pathChar) return false; //if mvoement in the current direction will lead to antoher open path we return false since that will create a clump
				if(moveMap[0]!=0){ //if our movement was in the x directopn
					//check the left and right of current cooridnates for alrady open paths
					if(maze[currentPos[0]][currentPos[1]+1]==pathChar) return false;
					if(maze[currentPos[0]][currentPos[1]-1]==pathChar) return false;
				} else if(moveMap[1]!=0){
					//check the top and bottom of current cooridnates for alrady open paths
					if(maze[currentPos[0]+1][currentPos[1]]==pathChar) return false;
					if(maze[currentPos[0]-1][currentPos[1]]==pathChar) return false;
				}
			}
		} else if(!notOnBorder) return false;//if on a border it is false
		return true;//if passes all condition, it is valid so return true
	}
	static boolean notBorder(int []currentPos, char[][]maze){
		/**
		 * chjecks if a pair of coordinates is on the bordder
		 */
		if(maze[currentPos[0]][currentPos[1]] == borderChar || maze[currentPos[0]][currentPos[1]] == exitChar){ //if the currenty char is the exit or border char, it is a border
			return false; //so return false
		}
		else if(maze[currentPos[0]][currentPos[1]]==startChar){ //if we are on the starting char, it is also invalid
			return false;
		}
		else return true;//return true if passes all checks
	}

	static void drawWalls(char[][]maze){
		/**
		 * fills in empty space in the maze with barriers after drawing all the paths
		 * void since we dont return anything, maze is passed by reference
		 */
		for (int i = 0; i < maze.length; i++) {//loop through the maze
			for (int j = 0; j < maze[i].length; j++) {
				if(maze[i][j] != startChar && maze[i][j]!=pathChar && maze[i][j] != exitChar && maze[i][j] != borderChar){ //if currnet cell is not a path, exit, start or already a border
					maze[i][j] = borderChar; //make it a bvorder
				}
			}
		}
	}


	static void findPath(char[][]maze){
		//finds the path from sstart to exit in the maze
		if(hasStart && hasExit) {
			//if there is a start and exit we can setting up to search the maze
			int[] startPos = findStartCell(maze);
			//find the startin cell
			boolean startExitConnected = isStartExitConnected(maze, startPos[1], startPos[0]);
			//check if teh start is the first most cell to the exit
			if (startExitConnected) {
				//if the start is connected to the exit the shortest path is just from the start to the exit 1 cell
				maze[startPos[0]][startPos[1]] = '+'; //set that one cell to the + to sybmolize the path
				shortest.add(1); //add 1 to list because there is a shortest path of just the starting cell
			} else { //otherewise if not start is connected to exit
				visited = new boolean[maze.length * maze[0].length]; //intialize visted array to the size of the area of the maze
				/*
				this is because all the values we use for the index are in order from 1 and there will never be more than the area of the maze
				number of cells. This ensures we can always use the index for checking if a number has been visited.
				 */
				min = maze.length * maze[0].length; //there is at most, r * c total cells so the first path will always be shorter than min
				graph = createGraph(maze); //creates a 2d itneger array that maps each open path to an integer
				adjacencyList = createList(graph); //creates an arraylist for each number in the graph and sotres numbers connected to that number in an arraylist


				traverse(1);//traverse starting from 1 as the stating cell is always going to be assigned the valuie of 1 in graph

				if (shortest.size() == 0) { //if there was no path found after traversal
					//no path
					//Create text field that says "no path found"
				} else { //if there is a path
					shortest.remove(0); //remove the first index since that is the startin cell
					for (int i = 0; i < graph.length; i++) { //loop though the graph
						for (int j = 0; j < graph[0].length; j++) {
							if (shortest.contains(graph[i][j])) { //if the current element is in the shortest path, we knwo that number belongs to the shortest path
								maze[i][j] = '+'; //set the character to a + to symbolize being aprt of the shortest path
							}
						}
					}
				}
			}
		}
	}


	static int[][] createGraph (char[][]maze){
		/**
		 * creates a 2d integer the same size as the maze and assigns a number for each open path
		 * this makes it simple to traverse the graph using a searching algorthm based on integers as opposed to characters
		 * this is essiential since the characters were not unique so a cell could be identifed solely on characters
		 * using integers allows for solo indienification
		 * @return a 2d integer array representing the resulting graph after mapping an integer to each open path
		 */
		int nextCell = 3; //since 1 and 2 are reserved for start start and exit, the next number availbe will be 3
		int[] delta = findExitSide(maze); //find the delta for the exit cell
		int[][]graph = new int[maze.length][maze[0].length];//initalize the 2d array
		for (int i = 0; i < maze.length; i++) {//loop through maze
			for (int j = 0; j < maze[0].length; j++) {
				if(maze[i][j]==startChar)//if current cell is teh start,
				{
					graph[i][j] = 1;//set the number to 1
				}
				else if (maze[i][j]==exitChar) //if it is the exit
				{
					graph[i+delta[0]][j+delta[1]] = 2; //add the delta x and y to the y and x coordinate repsectivvely to get the firt most cell from the exit
					/*
					we dont set the exit itself to 2 because it is on the border and since we have the first cell connected to the eixt, we just need to reach that cell, not the
					eixt itself, to find the shortest path
					 */
					graph[i][j] = 0; //the exit beocmes 0 since it is on the border
				}
				else if(maze[i][j]==pathChar && graph[i][j]!=2) { //if the current cell is not a 2 and an open path
					graph[i][j] = nextCell; //set the number to the next unique integer as defined by nextCell
					nextCell++;//increment nextCell to maintian unique integers within graph only
				}
			}
		}
		return graph; //return the graph
	}
	static ArrayList<ArrayList<Integer>> createList(int[][]graph){
		/**
		 * creates and adjacncy list for each number in the grpah
		 * @param graph the 2d integer array representing the graph
		 */
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();//intialize new 2d arraylist type integer
		int maxCells = (graph.length * graph[0].length); //the max cells will be the area of the graph
		for (int i = 0; i < maxCells; i++) {//loop through each arraylist to initalize it
			adj.add(new ArrayList<>());
		}
		for(int i = 0; i<graph.length; i++){//loop through the graph
			for(int j = 0; j< graph[0].length; j++){
				int temp = graph[i][j]; //temp varialbe of the current intger
				if(temp!=0){ //if not a 0 since 0s are borders
					//perform 4 direction check to see which directions are valid
					checkDir(graph, j, i); //only valid if not a border (not  equal to 0)


					if(north) { //if cardinal direction is valid, get the idnex of that number in the graph in the list and add the connected number in said direction
						//for example, if north is valid, add the number above the current number inthe graph to its index in the adjacny list
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
		return adj; //returns the list
	}

	static void checkDir(int[][]graph, int x, int y){
		/**
		 * checks for valid dircetions
		 * @param graph the 2d integer graph
		 * @param x the x cooridnate
		 * @param y the y cooordinate
		 */
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
		/**
		 * boolean method to check if the start is connected to the exit
		 * if it is, then we know the shortest path is just 1 cell: the start
		 */
		boolean startNextToExit = false;
		//check each cardinal movement of the passed coordinates (up, down, left right), if it is equal to the exit character, the start is connected
		if(maze[y-1][x]==exitChar) startNextToExit = true;
		if(maze[y+1][x]==exitChar) startNextToExit = true;
		if(maze[y][x+1]==exitChar) startNextToExit = true;
		if(maze[y][x-1]==exitChar) startNextToExit = true;
		return startNextToExit; //return the boolean
	}

	static ArrayList<Integer> path = new ArrayList<>();//initalize new arrayliost to sotre the path during traversal

	static void traverse(int node){
		/**
		 * Traverses through all the connected numbers to integer node inside
		 * of the arraylist adjacency list using a loop to recruisvely call traverse again for a connected
		 * number. For each traversal we add the traversed number
		 * to an arraylist to record the route traveled so far. We also use a boolean array
		 * to track which numbers we have visited.
		 *
		 * Once the current node being searched is equal to 2 (the exit number),
		 * we check if the size of the route list is less than the current min which by default is the
		 * area of the maze so it will always be greater than the first path found.
		 *
		 * Once the recursive stack completes, we remove every traversed number up to the current number
		 * and reset the vistied array for those numbers. We do this so the traversal algorithm
		 * can check other paths that use those numbers.
		 *
		 * This algorithm is type void so that it can search every connection for a possible path
		 * and even when there is no path, the size of the shortest path list where the shortest path is stored
		 * will be zero.
		 *
		 * @param node integer to start the traversal from
		 */
		if(node==2){ //check if currnet number is the exit
			if(path.size()<min){ //if path size if less than min, it is the new shortest
				path.add(node); //add the exit to the path
				shortest.clear(); //clear the prevous shortest paht
				visited[2] = false; //unvisit the exit to check other paths to the exit
				for(int i = 0; i<path.size(); i++){ //loop though the path
					shortest.add(path.get(i)); //and add it to the shortest path list
				}
				min = shortest.size(); //new shortest path length
			}
		}
		else if(!visited[node]){ //if we didnt visit this number

			visited[node] = true;//visit it
			path.add(node); //add it to teh path
			for(int i: adjacencyList.get(node)){ //loop for all its connected neighbors
				traverse(i); //recursive call on the neighbor
			}

			if(path.size()!=0) { //if the path isn't empty

				int index = path.indexOf(node);//we store the current index
				int prevSize = path.size();//and the previous size
				for (int i = index; i < prevSize; i++) {//loop at the current index to the last index
					visited[path.get(index)] = false;//unvisit the number at the path at index
					path.remove(path.get(index));//remove it from the path
					/*
					this will remove all neighbors of the parent node from the current path so we can check for other paths that have those numbers
					as neighbors so we can compare each path
					 */
				}
			}
		}
	}
}




