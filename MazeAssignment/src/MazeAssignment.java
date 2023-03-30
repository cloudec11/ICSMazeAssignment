import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MazeAssignment {

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static boolean north, south, east, west;
    static int[][] graph;
    static boolean[] visited;
    static ArrayList<ArrayList<Integer>> adj;
    static ArrayList<Integer> shortest = new ArrayList<>();
    static int min;
    static int start = 1;
    static int exit = 2;
    public static void main(String[] args) throws IOException {
        System.out.println("Enter rows");
        int rows = readInt();
        System.out.println("Enter columns");
        int cols = readInt();

        char [][] maze = new char[rows][cols];

        drawMaze(maze);

        for(char[] c : maze){
            for(char x:c){
                System.out.print(x+" ");
            }
            System.out.println();
        }

        findPath(maze);

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

    public static int readInt() throws IOException {
        return Integer.parseInt(input());
    }

    static void drawMazeBarriers(char[][]maze){

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if(j==0 || j==maze[i].length-1 || i==0 || i== maze.length-1){
                    maze[i][j] = 'B';
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
        return exitCoord;
    }

    static void drawMaze(char[][] maze){
        drawMazeBarriers(maze);
        int exitCoord[] = drawExit(maze);
        int startCoord[] = drawExitPath(maze, exitCoord);
        int numPaths = (int) (Math.random()*(maze.length+maze[0].length)/2)+2;
        for (int i = 0; i < numPaths; i++) {
            int pathLength = (int) (Math.random()*Math.max(maze.length*2, maze[0].length)+1);
            drawPath(pathLength, startCoord, maze);
        }
        drawWalls(maze);
    }

    static int[] drawExitPath(char[][] maze, int[] exitCoord){
        int[] currentPos = new int[2];

        currentPos[0] = exitCoord[0];
        currentPos[1] = exitCoord[1];

        drawFirstCell(maze, currentPos);

        int pathLength = (int) (Math.random()*(maze.length+maze[0].length)+1);


        drawPath(pathLength, currentPos, maze);

        int[] startPos = new int[2];
        startPos[0] = currentPos[0];
        startPos[1] = currentPos[1];
        maze[startPos[0]][startPos[1]] = 'S';
        return startPos;

    }

    static int[] findExitSide(char[][]maze){
        int[] currentPos = new int[2];
        int[] delta = new int[2];
        delta[0] = 0;
        delta[1] = 0;
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

    static void drawFirstCell(char[][]maze, int[]currentPos){
        int Xchange = 0;
        int Ychange = 0;
        int direction = 0;
        int[] delta = findExitSide(maze);
        currentPos[0]+=delta[0];
        currentPos[1]+=delta[1];
        maze[currentPos[0]][currentPos[1]] = 'O';
    }

    static void drawPath(int pathLength, int[]currentPos, char [][]maze){

        int Xchange = 0;
        int Ychange = 0;

        int direction = 0;
        for (int i = 0; i < pathLength; i++) {

            int deltaXY = (int)(Math.random()*2)+1;

            if(deltaXY == 1){
                //if deltaXY is 1, create path in Y

                if(maze[currentPos[0]+Ychange][currentPos[1]]=='B'){
                    if(Ychange==1) direction = 1;
                    if(Ychange==-1) direction = 2;
                }

                if(Ychange==0){
                    int move = (int)(Math.random()*2+1);
                    if(move==1) direction = 1;
                    if(move==2) direction = 2;
                }

                if(Ychange==1) direction = 2;
                if(Ychange==-1) direction = 1;

            }
            else if(deltaXY == 2){
                //if deltaXY is 2, create path in X

                if(maze[currentPos[0]][currentPos[1]+Xchange]=='B'){
                    if(Xchange==1) direction = 3;
                    if(Xchange==-1) direction = 4;
                }

                if(Xchange==0){
                    int move = (int)(Math.random()*2+1);
                    if(move==1) direction = 3;
                    if(move==2) direction = 4;
                }

                if(Xchange==1) direction = 4;
                if(Xchange==-1) direction = 3;

            }


            switch (direction) {
                case 1:
                    //north
                    Xchange = 0;
                    currentPos[0]--;
                    if(validCell(currentPos, maze)){
                        maze[currentPos[0]][currentPos[1]] = 'O';
                    } else currentPos[0]++;
                    Ychange = -1;

                    break;
                case 2:
                    //south
                    Xchange = 0;
                    currentPos[0]++;
                    if(validCell(currentPos, maze)){
                        maze[currentPos[0]][currentPos[1]] = 'O';
                    } else currentPos[0]--;
                    Ychange = 1;

                    break;
                case 3:
                    //west
                    Ychange = 0;
                    currentPos[1]--;
                    if(validCell(currentPos, maze)){
                        maze[currentPos[0]][currentPos[1]] = 'O';
                    } else currentPos[1]++;
                    Xchange = -1;

                    break;
                case 4:
                    //east
                    Ychange = 0;
                    currentPos[1]++;
                    if(validCell(currentPos, maze)){
                        maze[currentPos[0]][currentPos[1]] = 'O';
                    } else currentPos[1]--;
                    Xchange = 1;

                    break;
            }

        }

    }

    static boolean validCell(int []currentPos, char[][]maze){
        if(maze[currentPos[0]][currentPos[1]] == 'B' || maze[currentPos[0]][currentPos[1]] == 'X'){
            return false;
        }
        else if(maze[currentPos[0]][currentPos[1]]=='S'){
            return false;
        } else return true;
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
        visited = new boolean[maze.length* maze[0].length];
        min = maze.length*maze[0].length; //there is at most, r * c total cells
        graph = createGraph(maze);
        adj = createList(graph);

        for(int[] i:graph){
            for (int g:i){
                System.out.print(g+", ");
            }
            System.out.println();
        }

        if(adj.get(2).size()==0){
            shortest.add(1);
        } else if(adj.get(1).size()==0){
            shortest.add(2);
        } else traverse(1);

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