import java.io.*;
class TicTacToe {   
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to Tic-Tac-Toe!! Input the following:");
        System.out.println("Player 1 Name:");
        Player p1 = new Player(br.readLine(), Move.O);
        System.out.println("Player 2 Name:");
        Player p2 = new Player(br.readLine(), Move.X);
        int noOfMoves = 0;
        boolean playAgain;
        do {
            Board b = new Board();
            System.out.println("Lets Start the Game " + p1.getName() + " and " + p2.getName() + "!!");
            b.printBoard();
            Player winner = null;
            while (winner == null && noOfMoves != 9) {
                Player playing = noOfMoves % 2 == 0 ? p1 : p2;

                playing.takeTurn(b);

                System.out.println("============");
                winner = b.checkWinner(noOfMoves, p1, p2);

                b.printBoard();
                noOfMoves++;
            }

            if (winner != null) {
                System.out.println("Winner is " + winner.getName());
            } else {
                System.out.println("Its a Tie..");
            }

            System.out.println("Do you want to play again?? Input 1 for Yes and anything else for No...");
            String playAgainInput = br.readLine();
            try {
                int repeat = Integer.parseInt(playAgainInput);
                if (repeat == 1) {
                    playAgain = true;
                    noOfMoves = 0;
                    System.out.println("=======================");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Ending Game..");
                playAgain = false;
            }
        } while (playAgain);

    }
}

enum Move {
    X,
    O
}

class Board {
    private String[][] playBoard;

    Board() {
        this.playBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.playBoard[i][j] = String.valueOf(i) + String.valueOf(j);
            }
        }
    }

    public boolean enterMove(Move xo, int m, int n) {
        if (this.playBoard[m][n].trim().equals(String.valueOf(Move.O)) || this.playBoard[m][n].trim().equals(String.valueOf(Move.X))) {
            return false;
        }
        this.playBoard[m][n] = String.valueOf(xo).concat(" ");
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.playBoard[i][j] + " | ");
            }
            System.out.println("\n---------------");
        }
    }

    public Player checkWinner(int noOfMoves, Player p1, Player p2) {
        if (noOfMoves < 5) {
            return null;
        }

        for (int i = 0; i < 3; i++) {
            if (this.playBoard[i][0].equals(this.playBoard[i][1]) && this.playBoard[i][1].equals(this.playBoard[i][2])) {
                return findWinningPlayer(this.playBoard[i][0].trim(), p1, p2);
            }

            if (this.playBoard[0][i].equals(this.playBoard[1][i]) && this.playBoard[1][i].equals(this.playBoard[2][i])) {
                return findWinningPlayer(this.playBoard[0][i].trim(), p1, p2);
            }
        }

        if (this.playBoard[0][0].equals(this.playBoard[1][1]) && this.playBoard[1][1].equals(this.playBoard[2][2])) {
            return findWinningPlayer(this.playBoard[1][1].trim(), p1, p2);
        }

        if (this.playBoard[0][2].equals(this.playBoard[1][1]) && this.playBoard[1][1].equals(this.playBoard[2][0])) {
            return findWinningPlayer(this.playBoard[1][1].trim(), p1, p2);
        }

        return null;
    }

    private Player findWinningPlayer(String winnerChar, Player p1, Player p2) {
        if (p1.getMoveAsString().equals(winnerChar)) {
            return p1;
        } else {
            return p2;
        }
    }

    public boolean isMoveInputValid(String playerInput) {
        if (playerInput.length() != 2) {
            return false;
        }
        try {
            int m = Integer.parseInt(String.valueOf(playerInput.charAt(0)));
            int n = Integer.parseInt(String.valueOf(playerInput.charAt(1)));
            if (m < 0 || m >= 3 || n < 0 || n >= 3) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

class Player {
    private String name;
    private Move move;

    Player(String name, Move move) {
        this.name = name;
        this.move = move;
    }

    public String getName() {
        return this.name;
    }
    public Move getMove() {
        return this.move;
    }
    public String getMoveAsString() {
        return String.valueOf(this.move);
    }

    public void takeTurn(Board b) throws IOException {
        boolean moveValidity = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Input two digit number to play your move, " + this.getName() + "| move->`" + this.getMove() + "`");

            String moveSpaceInput = br.readLine();
            moveValidity = b.isMoveInputValid(moveSpaceInput);
            if (moveValidity) {
                int m = Integer.parseInt(String.valueOf(moveSpaceInput.charAt(0)));
                int n = Integer.parseInt(String.valueOf(moveSpaceInput.charAt(1)));
                if (!b.enterMove(this.getMove(), m, n)) {
                    System.out.println("Spot already filled for " + m + "" + n + "!! Try Again " + this.getName());
                    b.printBoard();
                    moveValidity = false;
                }
            } else {
                System.out.println("Invalid Input!! Try Again, " + this.getName());
                b.printBoard();
            }
        } while (!moveValidity);
    }
}