import model.Board;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class Viewer extends JPanel {

    private Board board;
    private static final int CELL_SIZE = 30;
    private static final int BORDER_WIDTH = 5;

    public Viewer(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(board.getGrid()[0].length * CELL_SIZE + 2 * BORDER_WIDTH, board.getGrid().length * CELL_SIZE + 2 * BORDER_WIDTH));
        setBackground(Color.GRAY);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);

        int[][] grid = board.getGrid();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(
                            x * CELL_SIZE + BORDER_WIDTH,
                            y * CELL_SIZE + BORDER_WIDTH,
                            CELL_SIZE,
                            CELL_SIZE
                    );
                }
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), BORDER_WIDTH);
        g.fillRect(0, 0, BORDER_WIDTH, getHeight());
        g.fillRect(0, getHeight() - BORDER_WIDTH, getWidth(), BORDER_WIDTH);
        g.fillRect(getWidth() - BORDER_WIDTH, 0, BORDER_WIDTH, getHeight());
    }

    private void drawBoard(Graphics g) {
        int[][] grid = board.getGrid();
        Model model = board.getCurrentModel();
        Color[][] colorGrid = board.getColorGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] != 0) {
                    g.setColor(colorGrid[row][col]);
                    drawBlock(g, col, row, g.getColor());
                }
            }
        }

        if (model != null) {
            int[][] shape = model.getShape();
            int x = model.getX();
            int y = model.getY();

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[0].length; col++) {
                    if (shape[row][col] != 0) {
                        drawBlock(g, x + col, y + row, model.getColor());
                    }
                }
            }
        }
    }

    private void drawBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * CELL_SIZE + BORDER_WIDTH, y * CELL_SIZE + BORDER_WIDTH, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x * CELL_SIZE + BORDER_WIDTH, y * CELL_SIZE + BORDER_WIDTH, CELL_SIZE, CELL_SIZE);
    }
}
