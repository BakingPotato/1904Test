package es.codeurjc.ais.tictactoe;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;

//This class is a component. It can autowire any other component using @Autowired annotation 
public class TicTacToeHandler extends TextWebSocketHandler {

	enum ClientToServerAction {
		JOIN_GAME, MARK, RESTART, MODE_CHANGE
	}

	static class ServerToClientMsg {
		EventType action;
		Object data;
	}

	static class ClientToServerMsg {
		ClientToServerAction action;
		Data data;
	}

	static class Data {
		int playerId;
		int cellId;
		String name;
	}

	private ObjectMapper json = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

	private TicTacToeGame game;
	private ConcurrentMap<WebSocketSession, Connection> connections = new ConcurrentHashMap<>();

	private StatisticsService stats = new StatisticsService();

	public TicTacToeHandler() {
		newGame();
	}

	private void newGame() {
		game = new TicTacToeGame();
	}

	@Override
	public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
		if (this.connections.size() < 2) {
			Connection connection = new Connection(json, session);
			this.connections.put(session, connection);
			this.game.addConnection(connection);
		} else {
			System.err.println(
					"Error: Trying to connect more than 2 players at the same time. Rejecting incoming client");
			session.close();
		}
	}

	@Override
	public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		this.connections.remove(session);
		
		if (!this.connections.isEmpty()) {
			Event reconnectEvent = new Event();
			reconnectEvent.type = EventType.RECONNECT;
			this.connections.values().iterator().next().sendEvent(reconnectEvent);
		}

		this.newGame();
	}

	@Override
	public synchronized void handleTextMessage(WebSocketSession session, TextMessage wsMsg) throws Exception {

		String jsonMsg = wsMsg.getPayload();

		System.out.println("Received message '" + jsonMsg + "' from client " + session.getId());

		ClientToServerMsg msg;

		try {
			msg = json.readValue(jsonMsg, ClientToServerMsg.class);
		} catch (Exception e) {
			showError(jsonMsg, e);
			return;
		}

		try {

			switch (msg.action) {

			case JOIN_GAME:
				int numPlayers = game.getPlayers().size();
				String letter = numPlayers == 0 ? "X" : "O";
				Player player = new Player(numPlayers + 1, letter, msg.data.name);
				Set<Player> players = stats.getPlayers();
				for (Player playerIt: players) {
					if (playerIt.getName().equals(player.getName())) {
						player = playerIt;
					}
				}
				game.addPlayer(player);
				break;

			case MODE_CHANGE:
				break;

			case MARK:
				if (game.checkTurn(msg.data.playerId)) {
					game.mark(msg.data.cellId);
				}
				break;

			case RESTART:
				//Antes de hacer restart se obtiene el resultado de la partida de ambos jugadores y se actualizan sus stats
				for (Player p: game.getPlayers() ) {
					if (stats.getPlayerStats(p) == null) {
						stats.addPlayerStats(p, new PlayerStats(p.getName() , 0, 0, 0));
					}
					if (game.checkDraw()) {
						stats.addDraw(p);
					}
					else if (game.checkTurn(p.getId())){
						stats.addWin(p);
					}
					else{
						stats.addLoss(p);
					}
				}
				game.restart();
				break;
			}

		} catch (Exception e) {
			showError(jsonMsg, e);
		}
	}

	public StatisticsService getStats(){
		return this.stats;
	}
	

	private void showError(String jsonMsg, Exception e) {
		System.err.println("Exception processing message: " + jsonMsg);
		e.printStackTrace(System.err);
	}
}
