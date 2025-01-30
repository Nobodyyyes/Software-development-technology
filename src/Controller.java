import model.Board;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    private Board board;
    private Viewer viewer;

    public Controller(Board board, Viewer viewer) {
        this.board = board;
        this.viewer = viewer;
    }

    /**
     * Handles key press events to control the game.
     *
     * @param e the KeyEvent triggered by a key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (board.canMoveLeft()) {
                    board.getCurrentModel().moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (board.canMoveRight()) {
                    board.getCurrentModel().moveRight();
                }
                break;
            case KeyEvent.VK_DOWN:
                board.moveModelDown();
                break;
            case KeyEvent.VK_UP:
                board.getCurrentModel().rotate(board.getGrid()[0].length, board.getGrid().length, board.getGrid());
                break;
        }
        viewer.repaint();
    }
}
