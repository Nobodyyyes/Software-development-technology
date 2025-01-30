import model.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        while (true) {
            if (!startGame()) {
                break;
            }
        }
    }

    /**
     * Starts the Tetris game.
     * <p>
     * This method initializes the game window, board, viewer, and controller.
     * It then enters the main game loop where the game state is updated and
     * rendered. If the game is over, a dialog is shown to the user with options
     * to restart or exit the game.
     *
     * @return a boolean indicating whether the game should be restarted (true) or exited (false)
     */
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

    /**
     * Displays a game over dialog with options to restart or exit the game.
     *
     * @param jFrame the parent frame for the dialog
     * @return an integer indicating the user's choice:
     * 0 for restart, 1 for exit
     */
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
