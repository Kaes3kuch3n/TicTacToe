package de.kaes3kuch3n.tictactoe.game;

import java.util.Arrays;

public class Game {

    private Field[][] board;
    private int setFields;
    private GameState state;
    private Field activePlayer;

    private final int BOARD_SIZE = 3;

    public Game() {
        this(Field.CROSS);
    }

    public Game(Field startingPlayer) {
        board = new Field[BOARD_SIZE][BOARD_SIZE];
        for (Field[] f : board)
            Arrays.fill(f, Field.EMPTY);
        setFields = 0;
        state = GameState.RUNNING;
        activePlayer = startingPlayer;
    }

    public GameState getState() {
        return state;
    }

    public Field getActivePlayer() {
        return activePlayer;
    }

    public Field[][] getBoard() {
        return board;
    }

    public boolean draw(int positionX, int positionY) {
        if (!setField(positionX, positionY))
            return false;

        toggleActivePlayer();
        Field winner = checkForWin();

        if (setFields == 9 && winner == Field.EMPTY)
            state = GameState.OVER_DRAW;
        else if (winner == Field.CROSS)
            state = GameState.OVER_CROSS;
        else if (winner == Field.CIRCLE)
            state = GameState.OVER_CIRCLE;

        return true;
    }

    private boolean setField(int positionX, int positionY) {
        if (positionX < 0 || positionX >= BOARD_SIZE || positionY < 0 || positionY >= BOARD_SIZE)
            return false;
        if (!(board[positionX][positionY] == Field.EMPTY))
            return false;

        board[positionX][positionY] = activePlayer;
        setFields++;

        return true;
    }

    private void toggleActivePlayer() {
        activePlayer = (activePlayer == Field.CROSS) ? Field.CIRCLE : Field.CROSS;
    }

    private Field checkForWin() {
        //Check horizontal and vertical
        for (int i = 0; i < 3; i++) {
            //Check cross
            if (board[i][0] == Field.CROSS && board[i][1] == Field.CROSS && board[i][2] == Field.CROSS) {
                return Field.CROSS;
            }
            if (board[0][i] == Field.CROSS && board[1][i] == Field.CROSS && board[2][i] == Field.CROSS) {
                return Field.CROSS;
            }

            //Check circle
            if (board[i][0] == Field.CIRCLE && board[i][1] == Field.CIRCLE && board[i][2] == Field.CIRCLE) {
                return Field.CIRCLE;
            }
            if (board[0][i] == Field.CIRCLE && board[1][i] == Field.CIRCLE && board[2][i] == Field.CIRCLE) {
                return Field.CIRCLE;
            }
        }

        //Check diagonals: cross
        if (board[0][0] == Field.CROSS && board[1][1] == Field.CROSS && board[2][2] == Field.CROSS) {
            return Field.CROSS;
        }
        if (board[2][0] == Field.CROSS && board[1][1] == Field.CROSS && board[0][2] == Field.CROSS) {
            return Field.CROSS;
        }

        //Check diagonals: circle
        if (board[0][0] == Field.CIRCLE && board[1][1] == Field.CIRCLE && board[2][2] == Field.CIRCLE) {
            return Field.CIRCLE;
        }
        if (board[2][0] == Field.CIRCLE && board[1][1] == Field.CIRCLE && board[0][2] == Field.CIRCLE) {
            return Field.CIRCLE;
        }

        return Field.EMPTY;
    }

}
