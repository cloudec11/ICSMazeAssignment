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
            maze[0][exitCell-1] = 'X';
            exitCoord[0] = 0;
            exitCoord[1] = exitCell-1;

        } else if(exitPos==2){
            int exitCell = (int)(Math.random()*(maze[0].length-1)+1);
            maze[maze.length-1][exitCell-1] = 'X';
            exitCoord[0] = maze.length-1;
            exitCoord[1] = exitCell-1;

        }else if(exitPos==3){
            int exitCell = (int)(Math.random()*(maze.length-1)+1);
            maze[exitCell-1][0] = 'X';
            exitCoord[0] = exitCell-1;
            exitCoord[1] = 0;

        }else if(exitPos==4){
            int exitCell = (int)(Math.random()*(maze.length-1)+1);
            maze[exitCell-1][maze[0].length-1] = 'X';
            exitCoord[0] = exitCell-1;
            exitCoord[1] = maze[0].length-1;
        }
        return exitCoord;
    }

    static void drawMaze(char[][] maze){
        drawMazeBarriers(maze);
        int exitCoord[] = drawExit(maze);
        int startCoord[] = new int[2];
        startCoord[0] = (int)(Math.random()* maze.length-2)+1;
        startCoord[1] = (int)(Math.random()* maze[0].length-2)+1;
//        drawPath(maze, exitCoord, startCoord);
    }

    static void drawPath(char[][]maze, int[] exitCoord, int[] startCoord){
        
    }

}