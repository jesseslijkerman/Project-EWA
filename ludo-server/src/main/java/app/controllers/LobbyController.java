package app.controllers;


import app.models.Lobby;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Martijn Mulder
 * Controller voor het lobby systeem
 */


@Controller
public class LobbyController extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    // mapping point to retrieve the lobbies

    @GetMapping("/lobbies")
    public Collection<Lobby> getLobbies() {
        return lobbies.values();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Process incoming WebSocket messages, such as lobby creation or updates
        try {
            Lobby lobbyRequest = objectMapper.readValue(message.getPayload(), Lobby.class);
            String sessionId = UUID.randomUUID().toString();
            Lobby newLobby = new Lobby(lobbyRequest.getName(), sessionId, lobbyRequest.getMaxPlayers(), lobbyRequest.getCreated());
            lobbies.put(sessionId, newLobby);
            notifyClients();
        } catch (IOException e) {
            // Handle deserialization exception, e.g., send an error message back to the client
        }
    }

    public void Test() {
        
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    private void notifyClients() {
        try {
            String lobbyList = objectMapper.writeValueAsString(lobbies.values());
            TextMessage message = new TextMessage(lobbyList);

            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    // Handle exception, e.g., remove the session from the list
                }
            }
        } catch (JsonProcessingException e) {
            // Handle serialization exception
        }
    }
}
