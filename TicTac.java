import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTac extends Application {

    private static final int SIZE = 5; // Board size
    private Button[][] board = new Button[SIZE][SIZE];
    private boolean isPlayerXTurn = true; // Track current player
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        // Initialize the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button();
                button.setPrefSize(80, 80); // Set size of each button
                button.setStyle("-fx-font-size: 24;");
                board[row][col] = button;

                final int r = row;
                final int c = col;

                button.setOnAction(e -> handleMove(r, c));
                grid.add(button, col, row);
            }
        }

        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setTitle("Tic Tac Toe 5x5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(int row, int col) {
        Button button = board[row][col];
        if (!button.getText().isEmpty()) {
            return; // Ignore moves on already filled cells
        }

        // Mark the cell for the current player
        String currentPlayer = isPlayerXTurn ? PLAYER_X : PLAYER_O;
        button.setText(currentPlayer);

        // Check for a winner
        if (checkWinner(row, col, currentPlayer)) {
            showWinnerAlert(currentPlayer);
            resetBoard();
            return;
        }

        // Switch turns
        isPlayerXTurn = !isPlayerXTurn;
    }

    private boolean checkWinner(int row, int col, String player) {
        return checkRow(row, player) || checkColumn(col, player) || checkDiagonals(player);
    }

    private boolean checkRow(int row, String player) {
        for (int col = 0; col < SIZE; col++) {
            if (!board[row][col].getText().equals(player)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int col, String player) {
        for (int row = 0; row < SIZE; row++) {
            if (!board[row][col].getText().equals(player)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals(String player) {
        // Check main diagonal
        boolean mainDiagonal = true;
        for (int i = 0; i < SIZE; i++) {
            if (!board[i][i].getText().equals(player)) {
                mainDiagonal = false;
                break;
            }
        }

        boolean antiDiagonal = true;
        for (int i = 0; i < SIZE; i++) {
            if (!board[i][SIZE - 1 - i].getText().equals(player)) {
                antiDiagonal = false;
                break;
            }
        }

        return mainDiagonal || antiDiagonal;
    }

    private void showWinnerAlert(String player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("We have a winner!");
        alert.setContentText("Player " + player + " wins!");
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col].setText("");
            }
        }
        isPlayerXTurn = true; 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
