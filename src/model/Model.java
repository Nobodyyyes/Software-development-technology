package model;

import java.awt.*;
import java.util.Random;

public class Model {

    private int[][] shape;
    private int x, y;
    private Color color;

    public Model(int[][] shape) {
        this.shape = shape;
        this.x = 0;
        this.y = 0;
        this.color = generateRandomColor();
    }

    public int[][] getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Rotates the current model 90 degrees clockwise if the rotation is valid.
     *
     * @param boardWidth  the width of the board
     * @param boardHeight the height of the board
     * @param grid        the current state of the board grid
     */
    public void rotate(int boardWidth, int boardHeight, int[][] grid) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotatedFigure = new int[cols][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotatedFigure[col][rows - 1 - row] = shape[row][col];
            }
        }

        for (int row = 0; row < rotatedFigure.length; row++) {
            for (int col = 0; col < rotatedFigure[0].length; col++) {
                if (rotatedFigure[row][col] != 0) {
                    int newX = x + col;
                    int newY = y + row;

                    if (newX < 0 || newX >= boardWidth || newY < 0 || newY >= boardHeight || grid[newY][newX] != 0) {
                        return;
                    }
                }
            }
        }

        shape = rotatedFigure;
    }

    /**
     * Generates a random color.
     *
     * @return a Color object with random RGB values.
     */
    private Color generateRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
