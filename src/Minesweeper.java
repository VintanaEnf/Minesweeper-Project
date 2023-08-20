import java.util.Random;

public class Minesweeper {

    private int[][] board;
    private int[][] game;
    private final int Rows;
    private final int Cols;
    private final int Bombs;
    private boolean Won;
    Minesweeper(int Rows, int Cols, int Bombs){
        this.Rows = Rows;
        this.Cols = Cols;
        this.Bombs = Bombs;
        this.Won = false;

        innitBoard();

    }

    //public methods for interacting with the game.

    public void flag(int row, int col){
        if (game[row][col] == 0) {
            game[row][col] = 2;
        }
        else if (game[row][col] == 2) {
            game[row][col] = 0;
        }
    }

    public void touch(int row, int col){
        if (game[row][col] == 0) {
            game[row][col] = 1;
        }
        if (board[row][col] == 0) {
            __autoTouchLoop();
        }
        if (board[row][col] == 9) {
            endGame();
        }
    }

    public int getBoardAt(int row, int col){return board[row][col];}

    public int getGameAt(int row, int col){return game[row][col];}

    public boolean isWinner(){ return this.Won; }
    //important private methods.

    private void innitBoard(){
        // 9 for Bombs.
        // 0 - 8 for numbers.
        board = new int[Rows][Cols];
        int counter = Bombs;

        while(counter!=0){
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Cols; j++) {
                    if((counter!=0) && (__random()==1)){
                        board[i][j]=9;
                        counter--;
                    }
                }
            }
        }

        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                if(board[i][j]!=9){
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if(__isThisLegal((i-1+k), (j-1+l)) && (board[i-1+k][j-1+l] == 9)){
                                board[i][j] = board[i][j] + 1;
                            }
                        }
                    }
                }
            }
        }

        // 0 for unopened.
        // 1 for opened.
        // 2 for flagged.
        game = new int[this.Rows][this.Cols];
    }

    public void printConsoleGame(){
        System.out.println("PrintGameCalled");
        for (int i = 0; i < this.Rows; i++) {
            System.out.println();
            for (int j = 0; j < this.Cols; j++) System.out.print(game[i][j] + "  ");
        }
    }

    public void printConsoleBoard(){
        System.out.println("PrintBoardCalled");
        for (int i = 0; i < this.Rows; i++) {
            System.out.println();
            for (int j = 0; j < this.Cols; j++) {
                System.out.print(board[i][j] + "  ");
            }
        }
    }

    public boolean isWon(){
        int count = 0;
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                if(game[i][j]==1 && board[i][j]!=9){
                    count++;
                }
                if(game[i][j]==1 && board[i][j]==9){
                    loseGame();
                    break;
                }
            }
        }
        if(count == this.Rows*this.Cols - this.Bombs){
            winGame();
            return true;
        }
        return false;
    }

    //utility methods.
    private void __autoTouch(int row, int col){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if(__isThisLegal((row+i), (col+j)) && this.game[row+i][col+j] != 2){
                    game[row+i][col+j] = 1;
                }
            }
        }
    }

    private int __random(){
        Random random = new Random();
        return random.nextInt((int)((Rows*Cols) - Bombs));
    }

    private boolean __isThisLegal(int i, int j) {
        return (0 <= i && i < this.Rows) && (0 <= j && j < this.Cols);
    }

    private void __autoTouchLoop(){
        //This is the best algorithm that I could come up with and it looks disgusting.

        for (int k = 0; k < (int)((this.Cols*this.Rows)/2); k++) {
            for (int i = 0; i < this.Rows; i++) {
                for (int j = 0; j < this.Cols; j++) {
                    if(game[i][j] == 1 && board[i][j]==0){
                        __autoTouch(i, j);
                    }
                }
            }
        }
    }
    // Ending Games.

    private void winGame(){
        System.out.println("You won!");
        this.Won=true;
    }

    private void loseGame(){
        System.out.println("You lost the game!");
    }

    public void endGame(){
        System.out.println("The game ended.");
    }

}
