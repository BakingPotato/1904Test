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

    public int findBestMove(int movement) {
        board.getCell(movement).value = player;
        board.getCell(movement).active = false;
        int bestValue = minusInf;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            if (cell.active) {
                cell.active = false;
                cell.value = computerLabel;
				int moveValue = minValor();
                cell.active = true;
                cell.value = null;
                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
		System.out.println("la funcion devuelve " + bestMove + " y el bestValue es " + bestValue);
        board.getCell(bestMove).value = computerLabel;
        board.getCell(bestMove).active = false;
        return bestMove;
    }

    public int evaluate() {
        int [] aux = board.getCellsIfWinner(player);
        if (aux != null)
            return -10;
        else {
            aux = board.getCellsIfWinner(computerLabel);
            if (aux != null)
                return 10;
            else
                return 0;
        }
    }

    public int maxValor() {
    	int best = minusInf;
    	int eval = this.evaluate();
    	if (eval != 0) // si es terminal
    		return eval;
    	if (board.checkDraw())
    		return 0;
		for (int i = 0; i < 9; i++) {
			TicTacToeGame.Cell cell = board.getCell(i);
			if (cell.active) {
				cell.active = false;
				cell.value = computerLabel;
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
		if (eval != 0) // si es terminal
			return eval;
		if (board.checkDraw())
			return 0;
		for (int i = 0; i < 9; i++) {
			TicTacToeGame.Cell cell = board.getCell(i);
			if (cell.active) {
				cell.active = false;
				cell.value = player;
				best = Math.min(best, maxValor());
				cell.active = true;
				cell.value = null;
			}
		}
		return best;
	}
}

