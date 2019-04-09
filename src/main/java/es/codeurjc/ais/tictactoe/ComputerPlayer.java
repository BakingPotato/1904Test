package es.codeurjc.ais.tictactoe;

public class ComputerPlayer {

    private Board board;

    public ComputerPlayer() {
    	board = new Board();
	}

    public int findBestMove(int movement, String label) {
        board.getCell(movement).value = label;
        board.getCell(movement).active = false;

        int bestValue = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            if (cell.active) {
                cell.active = false;
                cell.value = label;

                int moveValue = minimax(0, false, "X", "O");

                cell.active = true;
                cell.value = null;

                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

    public boolean hasMovesLeft() {
        return !board.checkDraw();
    }

    public int minimax(int depth, boolean maxTurn, String player1, String player2) {
        int score = evaluate(player1, player2);
        if (score == 10 || score == -10) {
            return score;
        }
        if (!hasMovesLeft()) {
            return 0;
        }
        if (maxTurn) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                TicTacToeGame.Cell cell = board.getCell(i);
                if (cell.active) {
                    cell.active = false;
                    cell.value = player2;

                    best = Math.max(best, minimax(depth++, !maxTurn, player1, player2));

                    cell.active = true;
                    cell.value = null;
                }
            }
            return best;
        }
        else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                TicTacToeGame.Cell cell = board.getCell(i);
                if (cell.active) {
                    cell.active = false;
                    cell.value = player1;

                    best = Math.min(best, minimax(depth++, !maxTurn, player1, player2));

                    cell.active = true;
                    cell.value = null;
                }
            }
            return best;
        }
    }

    public int evaluate(String player1, String player2) {
        int [] aux1 = board.getCellsIfWinner(player1);
        if (aux1 != null)
            return 10;
        else {
            aux1 = board.getCellsIfWinner(player2);
            if (aux1 != null)
                return -10;
            else
                return 0;
        }
    }
}

