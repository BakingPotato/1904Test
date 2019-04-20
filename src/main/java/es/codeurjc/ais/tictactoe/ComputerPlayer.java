package es.codeurjc.ais.tictactoe;

public class ComputerPlayer {

    //Los identificadores de cada jugador
    final String computerLabel = "C";
    final String player = "X";
    //Para el algoritmo minmax
    final int minusInf = Integer.MIN_VALUE;
    final int plusInf = Integer.MAX_VALUE;

    private Board board; //La IA tendra una Board propia que servira de espejo de la del usuario

    public ComputerPlayer() {
        //Crea el Board y activa todas las casillas
    	board = new Board();
        this.board.enableAll();
	}

    public int findBestMove(int movement) {
        //Primero obtiene el movimiento del usuario y lo pone en su Board privada
        board.getCell(movement).value = player;
        board.getCell(movement).active = false;
        int bestValue = minusInf; //Lo pone al minimo
        int bestMove = -1;
        //Recorre todas las casillas
        for (int i = 0; i < 9; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            //Si la celda esta libre la marca y prueba a partir de ella
            if (cell.active) {
                cell.active = false;
                cell.value = computerLabel;
                //Empieza a probar con el algoritmo minmax
				int moveValue = minValor();
                cell.active = true;
                cell.value = null;
                //Si moveValue es mayor que el bestValue reemplaza el movimiento anterior por este
                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
        //Finalmente devuelve el mejor movimiento encontrado
		System.out.println("la funcion devuelve " + bestMove + " y el bestValue es " + bestValue);
        board.getCell(bestMove).value = computerLabel;
        board.getCell(bestMove).active = false;
        return bestMove;
    }

    public int evaluate() { //evalua el valor del resultado actual
        // Si gana el usuario devuelve -10, al ser el peor resultado para la maquina
        int [] aux = board.getCellsIfWinner(player);
        if (aux != null)
            return -10;
        else { // Si gana la IA devuelve 10
            aux = board.getCellsIfWinner(computerLabel);
            if (aux != null)
                return 10;
            else
                return 0;
        }
    }

    public int maxValor() { //Calcula el mejor movimiento desde el punto de vista del usuario
    	int best = minusInf;
    	int eval = this.evaluate();
    	if (eval != 0) // si es terminal
    		return eval;
    	if (board.checkDraw())
    		return 0;
        //Recorre las casillas libres buscando los mejores movimientos que elegiria el usuario
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

	public int minValor() { //Calcula el mejor movimiento desde el punto de vista de la IA
		int best = plusInf;
		int eval = evaluate();
		if (eval != 0) // si es terminal
			return eval;
		if (board.checkDraw())
			return 0;
        //Recorre las casillas libres buscando los mejores movimientos para ella misma
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

