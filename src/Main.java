import model.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        while(true) {
            if (!startGame()) {
                break;
            }
        }
    }

    private static boolean startGame() {
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
                    int choice = showGameOverDialog(frame);

                    if (choice == 0) {
                        frame.dispose();
                        return true;
                    } else {
                        frame.dispose();
                        return false;
                    }
                }

                viewer.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int showGameOverDialog(JFrame jFrame) {
        String[] options = {"Рестарт", "Выйти"};
        return JOptionPane.showOptionDialog(
                jFrame,
                "Игра окончена! Что хотите сделать?",
                "Game over!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }
}
