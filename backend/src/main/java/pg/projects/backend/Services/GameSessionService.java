package pg.projects.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.projects.backend.DTOs.GaveUpResponse;
import pg.projects.backend.DTOs.StartGameResponse;
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


    public StartGameResponse startNewGame() {
        var game = gameRepository.getGameById(gameRepository.getRandomGameIdFromPool());
        var session = new GameSession();

        session.setSessionId(UUID.randomUUID().toString());
        session.setTargetGameId(game.getId().toString());
        session.setAttemptsMade(0);
        session.setGaveUp(false);
        session.setStartedAt(Instant.now());
        session.setEnded(false);

        sessionRepository.saveSession(session);

        return new StartGameResponse(
                session.getSessionId()
        );
    }

    public GaveUpResponse giveUpGame(StartGameResponse request) {
        var optionalSession = sessionRepository.getSession(request.sessionId());
        if (optionalSession.isEmpty()) {
            throw new IllegalArgumentException("Invalid session ID");
        }

        var session = optionalSession.get();
        var game = gameRepository.getGameById(session.getTargetGameId());
        session.setGaveUp(true);
        session.setEnded(true);
        sessionRepository.saveSession(session);

        return new GaveUpResponse(
                game,
                session.getAttemptsMade()
        );

    }
}
