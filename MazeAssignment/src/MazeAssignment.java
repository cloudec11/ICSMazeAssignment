import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MazeAssignment {

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
            int exitCell = (int)(Math.random()*(maze[0].length-1)+1);
            maze[0][exitCell] = 'X';
            exitCoord[0] = 0;
            exitCoord[1] = exitCell;

        } else if(exitPos==2){
            int exitCell = (int)(Math.random()*(maze[0].length-1)+1);
            maze[maze.length-1][exitCell] = 'X';
            exitCoord[0] = maze.length-1;
            exitCoord[1] = exitCell;

        }else if(exitPos==3){
            int exitCell = (int)(Math.random()*(maze.length-1)+1);
            maze[exitCell-1][0] = 'X';
            exitCoord[0] = exitCell;
            exitCoord[1] = 0;

        }else if(exitPos==4){
            int exitCell = (int)(Math.random()*(maze.length-1)+1);
            maze[exitCell-1][maze[0].length-1] = 'X';
            exitCoord[0] = exitCell;
            exitCoord[1] = maze[0].length-1;
        }
        return exitCoord;
    }

    static void drawMaze(char[][] maze){
        drawMazeBarriers(maze);
        int exitCoord[] = drawExit(maze);
        int startCoord[] = drawPath(maze, exitCoord);

    }

    static int[] drawPath(char[][] maze, int[] exitCoord){
        int[] currentPos = new int[2];

        currentPos[0] = exitCoord[0];
        currentPos[1] = exitCoord[1];

        int Xchange = 0;
        int Ychange = 0;

        int direction = 0;

        int deltaXY = (int)(Math.random()*2)+1;



        if(deltaXY == 1){
            //if deltaXY is 1, create path in Y

            if(Ychange==1) direction = 1;
            if(Ychange==-1) direction = 2;

            if(Ychange==0) {
                int increaseOrDecrease = (int) (Math.random() * 2 + 1);
                if (increaseOrDecrease == 1) {
                    direction = 1;

                }
                //direction is 1(north) if increase
                else if (increaseOrDecrease == 2) {
                    direction = 2;

                }
                //direction is 2(south) if decrease
            }
        }
        else if(deltaXY == 2){
            //if deltaXY is 2, create path in X

            if(Xchange==1) direction = 3;
            if(Xchange==-1) direction = 4;



            if(Xchange==0) {
                int increaseOrDecrease = (int) (Math.random() * 2 + 1);
                if (increaseOrDecrease == 1) {
                    direction = 3;

                } else if (increaseOrDecrease == 2) {
                    direction = 4;

                }
            }

        }

        int pathLength = (int)((Math.random()*maze[0].length-2)+1);

        boolean firstPass = true;

        for (int i = 0; i < pathLength; i++) {
            if(i==0){
                currentPos[0]--;
                //decrement because 2d array is bottom to top (max row number is the bottom)
                //if max row number is at the bottom, going north means decrementing the row number aka the Xcoord
                maze[currentPos[0]][currentPos[1]] = 'O';
                Ychange = 1;
                firstPass = false;

            }

            while(!firstPass) {
                switch (direction) {
                    case 1:
                        //north
                        currentPos[0]--;
                        maze[currentPos[0]][currentPos[1]] = 'O';
                        Ychange = 1;

                        break;
                    case 2:
                        //south
                        currentPos[0]++;
                        maze[currentPos[0]][currentPos[1]] = 'O';
                        Ychange = -1;

                        break;
                    case 3:
                        //east
                        currentPos[1]--;
                        maze[currentPos[0]][currentPos[1]] = 'O';
                        Xchange = 1;

                        break;
                    case 4:
                        //west
                        currentPos[1]++;
                        maze[currentPos[0]][currentPos[1]] = 'O';
                        Xchange = -1;

                        break;
                }
            }
        }
        int[] startPos = new int[2];
        startPos[0] = currentPos[0];
        startPos[1] = currentPos[1];

        return startPos;
    }

}