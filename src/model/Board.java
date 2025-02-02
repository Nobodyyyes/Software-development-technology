package model;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Board {

    private int[][] grid;
    private Model currentModel;
    private Color[][] colorGrid;
    private int score = 0;

    public Board(int rows, int cols) {
        grid = new int[rows][cols];
        colorGrid = new Color[rows][cols];
    }

    public void spawnModel(Model model) {
        currentModel = model;
    }

    public Model getCurrentModel() {
        return currentModel;
    }

    public Color[][] getColorGrid() {
        return colorGrid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    /**
     * Checks if the current model can move left by one column.
     *
     * @return true if the model can move left, false otherwise.
     */
    public Boolean canMoveLeft() {
        int[][] shape = currentModel.getShape();
        int x = currentModel.getX();
        int y = currentModel.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                if (shape[row][col] != 0) {
                    if (x + col - 1 < 0) {
                        return false;
                    }
                    if (grid[y + row][x + col - 1] != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if the current model can move right by one column.
     *
     * @return true if the model can move right, false otherwise.
     */
    public Boolean canMoveRight() {
        int[][] shape = currentModel.getShape();
        int x = currentModel.getX();
        int y = currentModel.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                if (shape[row][col] != 0) {
                    if (x + col + 1 >= grid[0].length) {
                        return false;
                    }
                    if (grid[y + row][x + col + 1] != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if the current model can move down by one row.
     *
     * @return true if the model can move down, false otherwise.
     */
    public Boolean canMoveDown() {
        int[][] shape = currentModel.getShape();
        int x = currentModel.getX();
        int y = currentModel.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                if (shape[row][col] != 0) {
                    if (y + row + 1 >= grid.length) {
                        return false;
                    }
                    if (grid[y + row + 1][x + col] != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Moves the current model down by one row if possible.
     * If the model cannot move down, it will be placed on the board,
     * full rows will be cleared, and a new model will be spawned.
     */
    public void moveModelDown() {
        if (canMoveDown()) {
            currentModel.moveDown();
        } else {
            int[][] shape = currentModel.getShape();
            int x = currentModel.getX();
            int y = currentModel.getY();

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[0].length; col++) {
                    if (shape[row][col] != 0) {
                        grid[y + row][x + col] = 1;
                        colorGrid[y + row][x + col] = currentModel.getColor();
                    }
                }
            }

            clearFullLines();
            spawnModel(generateRandomFigure());
        }
    }

    /**
     * Generates a random Tetris figure.
     *
     * @return a new Model object representing a random Tetris figure.
     */
    public Model generateRandomFigure() {
        int[][][] figures = {
                {{1, 1, 1, 1}},  // "I"
                {{1, 1}, {1, 1}},  // "O"
                {{0, 1, 0}, {1, 1, 1}},  // "T"
                {{1, 0, 0}, {1, 1, 1}},  // "L"
                {{0, 0, 1}, {1, 1, 1}},  // "J"
                {{0, 1, 1}, {1, 1, 0}},  // "S"
                {{1, 1, 0}, {0, 1, 1}}   // "Z"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(figures.length);
        return new Model(figures[randomIndex]);
    }

    /**
     * Checks if the game is over.
     * <p>
     * The game is considered over if the current model overlaps with any non-empty cell
     * in the grid when it spawns.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        int[][] shape = currentModel.getShape();
        int x = currentModel.getX();
        int y = currentModel.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    int gridRow = y + row;
                    int gridCol = x + col;

                    if (gridRow >= 0 && gridRow < grid.length && gridCol >= 0 && gridCol < grid[0].length) {
                        if (grid[gridRow][gridCol] != 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Increases the score based on the number of lines cleared.
     *
     * @param lines the number of lines cleared
     */
    public void increaseScore(int lines) {
        if (lines == 1) score += 100;
        else if (lines == 2) score += 200;
        else if (lines == 3) score += 300;
        else if (lines == 4) score += 400;
    }

    /**
     * Clears all full lines from the board, increases the score based on the number of lines cleared,
     * and shifts the remaining lines down.
     */
    public void clearFullLines() {
        int linesCleared = 0;
        for (int y = 0; y < grid.length; y++) {
            if (isLineFull(y)) {
                removeLine(y);
                linesCleared++;
            }
        }
        if (linesCleared > 0) {
            increaseScore(linesCleared);
        }
    }

    /**
     * Checks if a line is full.
     *
     * @param y the index of the line to check
     * @return true if the line is full, false otherwise
     */
    public boolean isLineFull(int y) {
        for (int x = 0; x < grid[y].length; x++) {
            if (grid[y][x] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes a line from the grid and shifts all lines above it down by one.
     *
     * @param y the index of the line to remove
     */
    public void removeLine(int y) {
        for (int i = y; i > 0; i--) {
            grid[i] = grid[i - 1].clone();
        }
        Arrays.fill(grid[0], 0);
    }

    /**
     * Reset player score.
     */
    public void resetScore() {
        this.score = 0;
    }
}
