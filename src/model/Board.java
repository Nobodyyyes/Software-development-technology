package model;

import java.awt.*;
import java.util.Random;

public class Board {

    private int[][] grid;
    private Model currentModel;
    private Color[][] colorGrid;

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

    public int[][] getGrid() {
        return grid;
    }

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

            checkAndClearFullRows();
            spawnModel(generateRandomFigure());
        }
    }

    private void checkAndClearFullRows() {
        for (int row = 0; row < grid.length; row++) {
            boolean isFullRow = true;

            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0) {
                    isFullRow = false;
                    break;
                }
            }

            if (isFullRow) {
                clearRow(row);
            }
        }
    }

    private void clearRow(int row) {
        for (int r = row; r > 0; r--) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[r][col] = grid[r - 1][col];
            }
        }

        for (int col = 0; col < grid[0].length; col++) {
            grid[0][col] = 0;
        }
    }

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
}
