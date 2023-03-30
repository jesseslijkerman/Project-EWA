package app.controllers;

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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LobbyController extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @GetMapping("/lobbies")
    public Collection<Lobby> getLobbies() {
        return lobbies.values();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Process incoming WebSocket messages, such as lobby creation or updates
        try {
            Lobby lobbyRequest = objectMapper.readValue(message.getPayload(), Lobby.class);