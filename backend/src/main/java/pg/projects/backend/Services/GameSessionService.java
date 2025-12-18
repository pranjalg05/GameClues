package pg.projects.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.projects.backend.DTOs.SessionId;
import pg.projects.backend.Models.GameSession;
import pg.projects.backend.Repositories.GameRedisRepository;
import pg.projects.backend.Repositories.GameSessionRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class GameSessionService {


    @Autowired
    GameRedisRepository gameRepository;

    @Autowired
    GameSessionRepository sessionRepository;


    public SessionId startNewGame() {
        var game = gameRepository.getGameById(gameRepository.getRandomGameIdFromPool());
        var session = new GameSession();

        session.setSessionId(UUID.randomUUID().toString());
        session.setTargetGameId(game.getId().toString());
        session.setAttemptsMade(0);
        session.setGaveUp(false);
        session.setStartedAt(Instant.now());
        session.setEnded(false);

        sessionRepository.saveSession(session);

        return new SessionId(
                session.getSessionId()
        );
    }


}
