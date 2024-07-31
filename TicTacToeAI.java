import java.util.Scanner;

public class TicTacToeAI {

    private static final char PLAYER = 'X';
    private static final char AI = 'O';
    private static final char EMPTY = ' ';
    
    private static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];
    
    public static void main(String[] args) {
        initializeBoard();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            printBoard();
            playerMove(scanner);
            if (checkWin(PLAYER)) {
                printBoard();
                System.out.println("Player wins!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a tie!");
                break;
            }
            
            aiMove();
            if (checkWin(AI)) {
                printBoard();
                System.out.println("AI wins!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a tie!");
                break;
            }
        }
        scanner.close();
    }
    
    private static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    private static void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("-----");
        }
    }
    
    private static void playerMove(Scanner scanner) {
        int row, col;
        while (true) {
            System.out.print("Enter row and column (0, 1, or 2) for your move: ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == EMPTY) {
                board[row][col] = PLAYER;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
    
    private static void aiMove() {
        int[] bestMove = minimax(board, AI);
        board[bestMove[0]][bestMove[1]] = AI;
    }
    
    private static int[] minimax(char[][] board, char player) {
        int[] bestMove = {-1, -1};
        int bestValue = (player == AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;
                    int boardValue = minimaxValue(board, player);
                    board[i][j] = EMPTY;
                    
                    if (player == AI) {
                        if (boardValue > bestValue) {
                            bestValue = boardValue;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    } else {
                        if (boardValue < bestValue) {
                            bestValue = boardValue;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
        }
        return bestMove;
    }
    
    private static int minimaxValue(char[][] board, char player) {
        if (checkWin(AI)) return 10;
        if (checkWin(PLAYER)) return -10;
        if (isBoardFull()) return 0;
        
        int bestValue = (player == AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;
                    int value = minimaxValue(board, (player == AI) ? PLAYER : AI);
                    board[i][j] = EMPTY;
                    
                    if (player == AI) {
                        bestValue = Math.max(bestValue, value);
                    } else {
                        bestValue = Math.min(bestValue, value);
                    }
                }
            }
        }
        return bestValue;
    }
    
    private static boolean checkWin(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }
    
    private static boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
