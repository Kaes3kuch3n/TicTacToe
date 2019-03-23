package de.kaes3kuch3n.tictactoe.cli;

import de.kaes3kuch3n.tictactoe.game.Field;
import de.kaes3kuch3n.tictactoe.game.Game;
import de.kaes3kuch3n.tictactoe.game.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLI {

    public static void main(String[] args) {
        Game game;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("CROSS")) {
                game = new Game(Field.CROSS);
            } else if (args[0].equalsIgnoreCase("CIRCLE")) {
                game = new Game(Field.CIRCLE);
            } else {
                game = new Game();
            }
        } else if (args.length == 0) {
            game = new Game();
        } else {
            System.out.println("Usage: java -jar TicTacToe.jar [starting player]\n" +
                    "[starting player] can either be CROSS or CIRCLE");
            System.exit(0);
            return;
        }
        new CLI(game);
    }

    private CLI(Game game) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (game.getState() == GameState.RUNNING) {
            drawBoard(game.getBoard());
            System.out.println("Turn: " + game.getActivePlayer());

            boolean validInput = false;

            while (!validInput) {
                String colString = null;
                String rowString = null;

                // Read input
                try {
                    System.out.print("\nColumn: ");
                    colString = reader.readLine();
                    System.out.print("Row: ");
                    rowString = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Check column input
                int column = checkInput(colString);
                if (column == -1) {
                    printIllegalDrawError(DrawErrorType.COLUMN);
                    continue;
                }

                // Check row input
                int row = checkInput(rowString);
                if (row == -1) {
                    printIllegalDrawError(DrawErrorType.ROW);
                    continue;
                }

                // Check if field is occupied
                if (!game.draw(row - 1, column - 1))
                    System.out.println("This field is already occupied!");
                else
                    validInput = true;
            }
        }

        drawBoard(game.getBoard());
        printWinMessage(game.getState());
    }

    private static void drawBoard(Field[][] board) {
        char[][] convertedBoard = convertBoard(board);

        System.out.println();
        System.out.println(" " + convertedBoard[0][0] + " | " + convertedBoard[0][1] + " | " + convertedBoard[0][2] + " ");
        System.out.println("---+---+---");
        System.out.println(" " + convertedBoard[1][0] + " | " + convertedBoard[1][1] + " | " + convertedBoard[1][2] + " ");
        System.out.println("---+---+---");
        System.out.println(" " + convertedBoard[2][0] + " | " + convertedBoard[2][1] + " | " + convertedBoard[2][2] + " ");
        System.out.println();
    }

    private static char[][] convertBoard(Field[][] board) {
        char[][] convertedBoard = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                convertedBoard[i][j] = fieldToPlayerChar(board[i][j]);
            }
        }

        return convertedBoard;
    }

    private static char fieldToPlayerChar(Field field) {
        switch (field) {
            case CIRCLE: return 'O';
            case CROSS: return 'X';
            default: return ' ';
        }
    }

    private static int checkInput(String input) {
        try {
            int number = Integer.parseInt(input);
            if (number > 0 && number < 4) {
                return number;
            }
        } catch (NumberFormatException e) {
            return -1;
        }

        return -1;
    }

    private static void printIllegalDrawError(DrawErrorType errorType) {
        String error = errorType.toString().charAt(0) + errorType.toString().substring(1).toLowerCase();
        System.out.println("Invalid " + error.toLowerCase() + " input! " + error + " must be a number between 1 and 3");
    }

    private static void printWinMessage(GameState endState) {
        String winMessage;

        switch (endState) {
            case OVER_CROSS:
                winMessage = "And the winner is Player Cross!";
                break;
            case OVER_CIRCLE:
                winMessage = "And the winner is Player Circle!";
                break;
            case OVER_DRAW:
                winMessage = "The game ended in a draw";
                break;
            default:
                winMessage = "There was an error :(";
                break;
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println();
        System.out.println(winMessage);
        System.out.println();
        System.out.println("========================================");
        System.out.println();
    }

    private enum DrawErrorType {
        COLUMN, ROW
    }
}
