package de.kaes3kuch3n.tictactoe.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Field firstPlayer = Field.CIRCLE;
    private Field secondPlayer = Field.CROSS;
    private GameState firstPlayerWin;
    private GameState secondPlayerWin;
    private Game game;

    @BeforeEach
    void setUp() {
        firstPlayerWin = (firstPlayer == Field.CIRCLE) ? GameState.OVER_CIRCLE : GameState.OVER_CROSS;
        secondPlayerWin = (secondPlayer == Field.CIRCLE) ? GameState.OVER_CIRCLE : GameState.OVER_CROSS;
        game = new Game();
    }

    @Test
    void gameStarted() {
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerSwitch() {
        game = new Game(firstPlayer);
        assertEquals(firstPlayer, game.getActivePlayer());
        game.draw(0, 0);
        assertEquals(secondPlayer, game.getActivePlayer());
        game.draw(1, 1);
        assertEquals(firstPlayer, game.getActivePlayer());
        game.draw(2, 2);
        assertEquals(secondPlayer, game.getActivePlayer());
    }

    @Test
    void boardBoundaries() {
        assertTrue(game.draw(0, 0));
        assertTrue(game.draw(2, 2));

        assertFalse(game.draw(-1, 2));
        assertFalse(game.draw(3, 2));
        assertFalse(game.draw(1, -1));
        assertFalse(game.draw(0, 4));
    }

    @Test
    void drawOnOccupiedField() {
        assertTrue(game.draw(1, 1));
        assertFalse(game.draw(1, 1));
    }

    @Test
    void winHorizontal() {
        for (int i = 0; i < 3; i++) {
            game = new Game(firstPlayer);

            game.draw(0, i);
            game.draw(0, (i + 1) % 3);
            game.draw(1, i);
            game.draw(1, (i + 1) % 3);
            game.draw(2, i);

            assertEquals(firstPlayerWin, game.getState());
        }

        for (int i = 0; i < 3; i++) {
            game = new Game(secondPlayer);

            game.draw(0, i);
            game.draw(0, (i + 1) % 3);
            game.draw(1, i);
            game.draw(1, (i + 1) % 3);
            game.draw(2, i);

            assertEquals(secondPlayerWin, game.getState());
        }
    }

    @Test
    void winVertical() {
        for (int i = 0; i < 3; i++) {
            game = new Game(firstPlayer);

            game.draw(i, 0);
            game.draw((i + 1) % 3, 0);
            game.draw(i, 1);
            game.draw((i + 1) % 3, 1);
            game.draw(i, 2);

            assertEquals(firstPlayerWin, game.getState());
        }

        for (int i = 0; i < 3; i++) {
            game = new Game(secondPlayer);

            game.draw(i, 0);
            game.draw((i + 1) % 3, 0);
            game.draw(i, 1);
            game.draw((i + 1) % 3, 1);
            game.draw(i, 2);

            assertEquals(secondPlayerWin, game.getState());
        }
    }

    @Test
    void winDiagonal() {
        game = new Game(firstPlayer);

        for (int i = 0; i < 3; i++) {
            game.draw(i, i);
            game.draw((i + 1) % 3, i);
        }
        assertEquals(firstPlayerWin, game.getState());

        game = new Game(firstPlayer);

        for (int i = 0; i < 3; i++) {
            game.draw(i, 2 - i);
            game.draw((i + 1) % 3, i);
        }
        assertEquals(firstPlayerWin, game.getState());

        game = new Game(secondPlayer);

        for (int i = 0; i < 3; i++) {
            game.draw(i, i);
            game.draw((i + 1) % 3, i);
        }
        assertEquals(secondPlayerWin, game.getState());

        game = new Game(secondPlayer);

        for (int i = 0; i < 3; i++) {
            game.draw(i, 2 - i);
            game.draw((i + 1) % 3, i);
        }
        assertEquals(secondPlayerWin, game.getState());
    }

    @Test
    void gameOverDraw() {
        game.draw(1, 0);
        game.draw(0, 0);
        game.draw(0, 1);
        game.draw(1, 1);
        game.draw(2, 1);
        game.draw(2, 0);
        game.draw(0, 2);
        game.draw(1, 2);
        game.draw(2, 2);

        assertEquals(GameState.OVER_DRAW, game.getState());
    }
}