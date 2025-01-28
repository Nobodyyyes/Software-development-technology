package model;

import java.awt.*;
import java.util.Random;

public class Model {

    private int[][] figure;
    private int x, y;
    private Color color;

    public Model(int[][] figure) {
        this.figure = figure;
        this.x = 0;
        this.y = 0;
        this.color = generateRandomColor();
    }

    public int[][] getFigure() {
        return figure;
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

    public void rotate(int boardWidth, int boardHeight, int[][] grid) {
        int rows = figure.length;
        int cols = figure[0].length;
        int[][] rotatedFigure = new int[cols][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotatedFigure[col][rows - 1 - row] = figure[row][col];
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

        figure = rotatedFigure;
    }

    private Color generateRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public Color getColorCode(int colorCode) {
        return switch (colorCode) {
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            case 3 -> Color.YELLOW;
            case 4 -> Color.GRAY;
            case 5 -> Color.GREEN;
            case 6 -> Color.ORANGE;
            default -> Color.BLACK;
        };
    }
}

