import java.io.*;
class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m;
        int n;
        int noOfMoves = 0;
        Board b = new Board();
        b.printBoard();
        while(b.checkWinner(noOfMoves).equals("") && noOfMoves != 9) {
            Move move = noOfMoves%2==0 ? Move.O : Move.X;
            boolean inputValidity = true;
            do {
                System.out.println("Input two digit number to play your move, Player " + move);
            
                String playerInput = br.readLine();
                inputValidity = isInputValid(playerInput);
                if(inputValidity) {
                    m = Integer.parseInt(String.valueOf(playerInput.charAt(0)));
                    n = Integer.parseInt(String.valueOf(playerInput.charAt(1)));
                    if(!b.enterMove(move, m, n)) {
                        System.out.println("Spot already filled for " + m + "" + n +"!! Try Again Player " + move);
                        inputValidity = false; 
                    }
                } else {
                    System.out.println("Invalid Input!! Try Again, Player " + move);
                }
            } while(!inputValidity);

            System.out.println("============");

            b.printBoard();
            noOfMoves++;
        }

        if(!b.checkWinner(noOfMoves).equals("")) {
            System.out.println("Winner is " + b.checkWinner(noOfMoves));
        } else {
            System.out.println();
        }
    }

    public static boolean isInputValid(String playerInput) {
        if(playerInput.length() != 2) {
            return false;
        }
        try {
            int m = Integer.parseInt(String.valueOf(playerInput.charAt(0)));
            int n = Integer.parseInt(String.valueOf(playerInput.charAt(1)));
            if(m<0 || m>=3 || n<0 || n>=3) {
                return false;
            }

        } catch(Exception e) {
            return false;
        }
        return true;
    }
}

enum Move {
    X, O
}

class Board {
    String[][] playBoard;

    Board() {
        this.playBoard = new String[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                this.playBoard[i][j] = String.valueOf(i) + String.valueOf(j);
            }
        }
    }

    public boolean enterMove(Move xo, int m, int n) {
        if(m>=3 || m<0 || n>=3 || n<0) {
            return false;
        }
        if(this.playBoard[m][n].equals(String.valueOf(Move.O).concat(" ")) || this.playBoard[m][n].equals(String.valueOf(Move.X).concat(" "))) {
            return false;
        }
        this.playBoard[m][n] = String.valueOf(xo).concat(" ");
        return true;
    }

    public void printBoard() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print(this.playBoard[i][j] + " | ");
            }
            System.out.println("\n---------------");
        }
    }

    public String checkWinner(int noOfMoves) {
        if(noOfMoves < 5) {
            return "";
        }

        for(int i=0; i<3; i++) {
            if(this.playBoard[i][0].equals(this.playBoard[i][1]) && this.playBoard[i][1].equals(this.playBoard[i][2])) { // for horizontal
                return this.playBoard[i][0];
            }

            if(this.playBoard[0][i].equals(this.playBoard[1][i]) && this.playBoard[1][i].equals(this.playBoard[2][i])) { // for vertical
                return this.playBoard[0][i];
            }
        }

        if(this.playBoard[0][0].equals(this.playBoard[1][1]) && this.playBoard[1][1].equals(this.playBoard[2][2])) {
            return this.playBoard[1][1];
        }
        
        if(this.playBoard[0][2].equals(this.playBoard[1][1]) && this.playBoard[1][1].equals(this.playBoard[2][0])) {
            return this.playBoard[1][1];
        }
        
        return "";
    }
}