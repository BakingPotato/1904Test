package es.codeurjc.ais.tictactoe;

public class ComputerPlayer {

    final String computerLabel = "C";
    final String player = "X";
    final int minusInf = Integer.MIN_VALUE;
    final int plusInf = Integer.MAX_VALUE;

    private Board board;

    public ComputerPlayer() {
    	board = new Board();
        this.board.enableAll();
	}

    public int findBestMove(int movement, String label) {
        board.getCell(movement).value = player;
        board.getCell(movement).active = false;

        int bestValue = minusInf;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            if (cell.active) {
                cell.active = false;
                cell.value = computerLabel;

                //int moveValue = minimax(0, false, "X", computerLabel);
				int moveValue = maxValor();

                cell.active = true;
                cell.value = null;

                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
		System.out.println("la funcion devuelve " + bestMove + "y el bestValue es " + bestValue);
        board.getCell(bestMove).value = computerLabel;
        board.getCell(bestMove).active = false;

        return bestMove;
    }
    public int evaluate() {
        int [] aux1 = board.getCellsIfWinner(player);
        if (aux1 != null)
            return -10;
        else {
            aux1 = board.getCellsIfWinner(computerLabel);
            if (aux1 != null)
                return 10;
            else
                return 0;
        }
    }

    public int maxValor() {
    	int best = minusInf;
    	int eval = this.evaluate();
    	if (eval != 0) { // si es terminal
    		return eval;
		}
		for (int i = 0; i < 9; i++) {

			TicTacToeGame.Cell cell = board.getCell(i);

			if (cell.active) {
				cell.active = false;
				cell.value = player;

				best = Math.max(best, minValor());

				cell.active = true;
				cell.value = null;
			}
		}
		return best;
	}

	public int minValor() {
		int best = plusInf;
		int eval = evaluate();
		if (eval != 0) { // si es terminal
			return eval;
		}
		for (int i = 0; i < 9; i++) {

			TicTacToeGame.Cell cell = board.getCell(i);

			if (cell.active) {
				cell.active = false;
				cell.value = computerLabel;

				best = Math.min(best, maxValor());

				cell.active = true;
				cell.value = null;
			}
		}
		return best;
	}



	public boolean isTerminal() {
		return !board.checkDraw();
	}

	public boolean isTerm() {
		for (int i = 0; i < 9; i++) {
			if (board.getCell(i).active)
				return false;
		}
		return true;
	}

    /*public int minimax(int depth, boolean maxTurn, String player1, String player2) {
        int score = evaluate();
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
    }*/

}

