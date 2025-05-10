import java.util.Scanner;

public class GeneralSudokuSolver {

    private static int SIZE;
    private static int SUBGRID_SIZE;
    private static int[][] board;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get board size
        System.out.print("Enter the size of Sudoku (e.g., 4, 9, 16): ");
        SIZE = scanner.nextInt();

        double sqrt = Math.sqrt(SIZE);
        if (sqrt - Math.floor(sqrt) != 0) {
            System.out.println("Invalid size. The size must be a perfect square.");
            return;
        }

        SUBGRID_SIZE = (int) sqrt;
        board = new int[SIZE][SIZE];

        System.out.println("Enter the Sudoku puzzle row by row (use 0 for empty cells):");
        for (int row = 0; row < SIZE; row++) {
            System.out.print("Row " + (row + 1) + ": ");
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = scanner.nextInt();
            }
        }

        System.out.println("\nSolving Sudoku...\n");

        if (solveSudoku()) {
            printBoard();
        } else {
            System.out.println("No solution exists for the given Sudoku puzzle.");
        }

        scanner.close();
    }

    private static boolean solveSudoku() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num;

                            if (solveSudoku()) {
                                return true;
                            }

                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int row, int col, int num) {
        // Check row
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num) return false;
        }

        // Check column
        for (int x = 0; x < SIZE; x++) {
            if (board[x][col] == num) return false;
        }

        // Check subgrid
        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;

        for (int r = startRow; r < startRow + SUBGRID_SIZE; r++) {
            for (int d = startCol; d < startCol + SUBGRID_SIZE; d++) {
                if (board[r][d] == num) return false;
            }
        }

        return true;
    }

    private static void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            if (row % SUBGRID_SIZE == 0 && row != 0) {
                printHorizontalDivider();
            }

            for (int col = 0; col < SIZE; col++) {
                if (col % SUBGRID_SIZE == 0 && col != 0) {
                    System.out.print(" | ");
                } else if (col != 0) {
                    System.out.print(" ");
                }
                System.out.print(String.format("%2d", board[row][col]));
            }
            System.out.println();
        }
    }

    private static void printHorizontalDivider() {
        for (int i = 0; i < SIZE + SUBGRID_SIZE - 1; i++) {
            System.out.print("----");
        }
        System.out.println();
    }
}
