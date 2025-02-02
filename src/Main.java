import model.Board;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        while (true) {
            if (!startGame()) {
                break;
            }
        }
    }

    private static boolean startGame() {
        String playerName;
        do {
            playerName = getPlayerName();
            if (playerName == null) {
                return false;
            }
            if (playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Имя не должно быть пустым или содержать только пробелы!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } while (playerName.trim().isEmpty());

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Board board = new Board(20, 15);
        Viewer viewer = new Viewer(board, playerName);
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
                    saveScore(playerName, board.getScore());
                    int choice = showGameOverDialog(frame);

                    if (choice == 0) {
                        board.resetScore();
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

    private static String getPlayerName() {
        List<String> lastScores = getLastScores();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Последние 3 результата:"));

        for (String score : lastScores) {
            JLabel label = new JLabel(score);
            panel.add(label);
        }

        JTextField textField = new JTextField();
        panel.add(textField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Введите ваше имя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return (result == JOptionPane.OK_OPTION) ? textField.getText() : null;
    }

    private static List<String> getLastScores() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("scores.txt"));
            List<String> records = new ArrayList<>();
            StringBuilder currentRecord = new StringBuilder();

            for (String line : lines) {
                if (line.startsWith("Игрок: ")) {
                    if (currentRecord.length() > 0) {
                        records.add(currentRecord.toString().trim());
                    }
                    currentRecord = new StringBuilder();
                }
                currentRecord.append(line).append("\n");
            }
            if (currentRecord.length() > 0) {
                records.add(currentRecord.toString().trim());
            }

            int count = Math.min(3, records.size());
            return records.subList(records.size() - count, records.size());
        } catch (IOException e) {
            return List.of("Нет сохраненных результатов");
        }
    }

    private static void saveScore(String playerName, int score) {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("\nИгрок: " + playerName + "\nСчет: " + score + "\nДата: " + timeStamp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
