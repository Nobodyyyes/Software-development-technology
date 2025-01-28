import model.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Board board = new Board(20, 15);
        Viewer viewer = new Viewer(board);
        Controller controller = new Controller(board, viewer);

        board.spawnModel(board.generateRandomFigure());
        board.moveModelDown();

        frame.add(viewer);
        frame.addKeyListener(controller);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            try {
                Thread.sleep(500);
                board.moveModelDown();

                if (board.isGameOver()) {
                    System.out.println("Games over!");
                    break;
                }

                viewer.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
