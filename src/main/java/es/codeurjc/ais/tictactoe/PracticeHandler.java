package es.codeurjc.ais.tictactoe;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static es.codeurjc.ais.tictactoe.TicTacToeHandler.ClientToServerMsg;

public class PracticeHandler extends TextWebSocketHandler {

    private ObjectMapper json = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private TicTacToeGame game;
    private ConcurrentMap<WebSocketSession, Connection> connections = new ConcurrentHashMap<>();

    private ComputerPlayer computerPlayer;


    public PracticeHandler() {
        newGame();
    }

    private void newGame() {
        game = new TicTacToeGame();
        computerPlayer = new ComputerPlayer();
    }

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (this.connections.size() < 1) {
            Connection connection = new Connection(json, session);
            this.connections.put(session, connection);
            this.game.addConnection(connection);
        } else {
            System.err.println(
                    "Error: Trying to connect more than 1 player at the same time. Rejecting incoming client");
            session.close();
        }
    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.connections.remove(session);

        if (!this.connections.isEmpty()) {
            TicTacToeGame.Event reconnectEvent = new TicTacToeGame.Event();
            reconnectEvent.type = TicTacToeGame.EventType.RECONNECT;
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
                    int rand = (int) Math.random()*2;
                    String letter = rand == 0 ? "X" : "O";
                    String c_letter = rand != 0 ? "X" : "O";
                    Player player = new Player(numPlayers + 1, letter, msg.data.name);
                    Player computer = new Player(numPlayers + 2, c_letter, "computer");
                    game.addPlayer(player);
                    game.addPlayer(computer);
                    break;

                case MARK:
                    if (game.checkTurn(msg.data.playerId)) {
                        game.mark(msg.data.cellId);
                        // Si la partida no ha acabado
                        if (game.checkTurn(msg.data.playerId + 1 )){
                            game.mark(computerPlayer.findBestMove(msg.data.cellId, game.getPlayers().get(1).getLabel()));
                        }
                    }
                    break;

                case RESTART:

                    game.restart();
                    break;
            }

        } catch (Exception e) {
            showError(jsonMsg, e);
        }
    }



    private void showError(String jsonMsg, Exception e) {
        System.err.println("Exception processing message: " + jsonMsg);
        e.printStackTrace(System.err);
    }
}
