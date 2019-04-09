package es.codeurjc.ais.tictactoe;


public class IA {

    /**
     * Returns the best move that can be done.
     * This method has to be called every time the machine has to make a move.
     *
     * @param   board the actual game board
     * @param   player1label the label attribute of the first player
     * @param   player2label the label attribute of the second player
     * @return  [x,y] the coordinates of the best move
     */
    public static int findBestMove(Board board, String player1label, String player2label) {
        int bestValue = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            if (cell.active) {
                cell.active = false;
                cell.value = player1label;

                int moveValue = minimax(board, 0, false, player1label, player2label);

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

    public static boolean hasMovesLeft(Board board) {
        for (int i = 0; i < 9; i++) {
            if (board.getCell(i).active)
                return true;
        }
        return false;
    }

    public static int minimax(Board board, int depth, boolean maxTurn, String player1, String player2) {
        int score = evaluate(board, player1, player2);
        if (score == 10 || score == -10) {
            return score;
        }
        if (!hasMovesLeft(board)) {
            return 0;
        }
        if (maxTurn) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                TicTacToeGame.Cell cell = board.getCell(i);
                if (cell.active) {
                    cell.active = false;
                    cell.value = player2;

                    best = Math.max(best, minimax(board, depth++, !maxTurn, player1, player2));

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

                    best = Math.min(best, minimax(board, depth++, !maxTurn, player1, player2));

                    cell.active = true;
                    cell.value = null;
                }
            }
            return best;
        }
    }

    /**
     * Returns the value of the board game.
     * If it is favourable to the first player, returns +10.
     * If it is favourable to the second player, returns -10.
     * If it is favourable to no one, returns 0.
     *
     * @param   board the actual game board
     * @param   player1 the first player's label
     * @param   player2 the second player's label
     * @return  the value of the actual board
     */
    public static int evaluate(Board board, String player1, String player2) {
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

