package app.services;
import app.models.Lobby;
import app.repositories.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


    @Service
    public class LobbyService {

        private final LobbyRepository lobbyRepository;

        @Autowired
        public LobbyService(LobbyRepository lobbyRepository) {
            this.lobbyRepository = lobbyRepository;
        }

        public List<Lobby> getAllLobbies() {
            return lobbyRepository.findAll();
        }

        public Optional<Lobby> getLobbyById(Long id) {
            return lobbyRepository.findById(id);
        }

        public Lobby createLobby(Lobby lobby) {
            return lobbyRepository.save(lobby);
        }

        public Lobby updateLobby(Lobby lobby) {
            return lobbyRepository.save(lobby);
        }

        public void deleteLobby(Long id) {
            lobbyRepository.deleteById(id);
        }
    }
