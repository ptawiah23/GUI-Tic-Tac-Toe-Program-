import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeFrame());
    }
}

class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons;
    private char currentPlayer;
    private TicTacToeGame game;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        game = new TicTacToeGame();
        currentPlayer = 'X';

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        buttons = new TicTacToeButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TicTacToeButton(i, j);
                buttons[i][j].addActionListener(new ButtonClickListener());
                boardPanel.add(buttons[i][j]);
            }
        }

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();
            if (button.getState() == ' ') {
                button.setState(currentPlayer);
                game.makeMove(button.getRow(), button.getCol(), currentPlayer);

                if (game.checkWin(currentPlayer)) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetBoard();
                    return;
                } else if (game.isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                    resetBoard();
                    return;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move. Try again.");
            }
        }
    }

    private void resetBoard() {
        game.reset();
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setState(' ');
            }
        }
    }
}

class TicTacToeButton extends JButton {
    private int row, col;
    private char state;

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = ' ';
        setFont(new Font("Arial", Font.BOLD, 40));
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getState() { return state; }

    public void setState(char state) {
        this.state = state;
        setText(String.valueOf(state));
    }
}

class TicTacToeGame {
    private char[][] board;

    public TicTacToeGame() {
        board = new char[3][3];
        reset();
    }

    public void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    public boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
}
