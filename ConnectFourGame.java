import java.util.Scanner;

public class ConnectFourGame{
    private int[][] board;

    // starts game if user(s) want to play
    public void promptStart(){
        System.out.println("Do you want to play ConnectFour?");
        Scanner input = new Scanner(System.in);
        String response = input.nextLine();

        // get a valid input
        while(!(response.equals("yes") || response.equals("no") || response.equals("y"
        ) || response.equals("n"))){
            System.out.println("Please enter 'yes'/'no'/'y'/'n'");
            response = input.nextLine();
        }

        if(response.equals("yes") || response.equals("y")){
            startGame();
        }
    }

    // start game
    public void startGame(){
        int winner = 0; // 0 corresponds to no winner
        int player = 1;
        int numTurns = 0;
        int[] turn = new int[2];
        makeBoard();

        for(int i = 0; i < 50; i++){
            System.out.println();
        }
        printBoard();

        while(winner == 0 && numTurns < board.length * board[0].length){
            turn = playerTurn(player);
            numTurns++;

            for(int i = 0; i < 50; i++){
                System.out.println();
            }
            printBoard();

            if(checkWin(player, turn[0], turn[1])){
                winner = player;
            }

            player = 3 - player;
        }
        System.out.println("Player " + winner + " wins!");
        promptStart();
    }

    // creates board array
    public void makeBoard(){
        board = new int[6][7];
    }

    // prints board and tokens
    public void printBoard(){
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_BLUE_BACKGROUND = "\u001B[44m";

        for(int[] row: board){
            for(int i = 0; i < board[0].length; i++){
                System.out.print(ANSI_BLUE + "█▀▀▀");
            }
            System.out.println("█ ");
            System.out.print("█ ");
            for(int player: row){
                switch(player){
                    case 0:
                        System.out.print("  █ ");
                        break;
                    case 1:
                        System.out.print(ANSI_YELLOW + "█" + ANSI_BLUE + " █ ");
                        break;
                    case 2:
                        System.out.print(ANSI_RED + "█" + ANSI_BLUE + " █ ");
                        break;
                }
            }
            System.out.println();
        }
        for(int i = 1; i <= board[0].length; i++){
            System.out.print("██" + ANSI_RESET + ANSI_BLUE_BACKGROUND + i + ANSI_BLUE + "█");
        }
        System.out.println("█" + ANSI_RESET);
    }

    // places token
    public int[] playerTurn(int player){
        int chosenColumn = promptColumn(player);
        int[] turn = new int[2];
        for(int row = board.length - 1; row >= 0; row--){
            if(board[row][chosenColumn] == 0){
                board[row][chosenColumn] = player;
                turn[0] = row;
                turn[1] = chosenColumn;
                break;
            }
        }
        return turn;
    }

    // gets column number from player
    public int promptColumn(int player){
        Scanner input = new Scanner(System.in);
        int response;
        System.out.print("Player " + player + ", choose a column: ");

        // get a valid input
        while(true){
            try{
                response = input.nextInt() - 1;
                if(response < 0 || response >= board[0].length){
                    throw new ArrayIndexOutOfBoundsException("Chosen column does not exist.");
                }
                else if(board[0][response] != 0){
                    throw new ArrayIndexOutOfBoundsException("Chosen column is full.");
                }
                break;
            }
            catch(Exception e){
                System.out.print("Please input a valid column: ");
                input.nextLine();
            }
        }
        return response;
    }

    // checks if the given turn wins the game
    public boolean checkWin(int player, int turnRow, int turnColumn){        
        return checkWinRows(player, turnRow, turnColumn) || checkWinColumns(player, turnRow, turnColumn) || checkWinDiagonals(player, turnRow, turnColumn);
    }

    public boolean checkWinRows(int player, int turnRow, int turnColumn){
        for(int col = 0; col <= board[0].length - 4; col++){
            if(board[turnRow][col] == player && board[turnRow][col + 1] == player && board[turnRow][col + 2] == player && board[turnRow][col + 3] == player){
                return true;
            }
        }
        return false;
    }

    public boolean checkWinColumns(int player, int turnRow, int turnColumn){
        if(turnRow > board.length - 4){
            return false;
        }
        for(int row = 0; row <= board.length - 4; row++){
            if(board[row][turnColumn] == player && board[row + 1][turnColumn] == player && board[row + 2][turnColumn] == player && board[row + 3][turnColumn] == player){
                return true;
            }
        }
        return false;
    }

    public boolean checkWinDiagonals(int player, int turnRow, int turnColumn){
        for(int row = 0; row <= board.length - 4; row++){
            for(int col = 0; col <= board[0].length - 4; col++){
                if(board[row][col] == player && board[row + 1][col + 1] == player && board[row + 2][col + 2] == player && board[row + 3][col + 3] == player){
                    return true;
                }
            }
            for(int col = board[0].length - 1; col >= 3; col--){
                if(board[row][col] == player && board[row + 1][col - 1] == player && board[row + 2][col - 2] == player && board[row + 3][col - 3] == player){
                    return true;
                }
            }
        }
        return false;
    }
}