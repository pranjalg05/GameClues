package pg.projects.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pg.projects.backend.DTOs.GaveUpResponse;
import pg.projects.backend.DTOs.GuessRequest;
import pg.projects.backend.DTOs.GuessResponse;
import pg.projects.backend.DTOs.SessionId;
import pg.projects.backend.Repositories.GameRedisRepository;
import pg.projects.backend.Repositories.GameSessionRepository;
import pg.projects.backend.Util.GameMapper;


@Service
public class GuessService {

    @Autowired
    GameRedisRepository gameRepository;

    @Autowired
    GameSessionRepository sessionRepository;

    @Autowired
    GameMapper mapper;

    @Autowired
    private IgdbService igdbService;


    public GaveUpResponse giveUpGame(SessionId request) {
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

    public ResponseEntity<GuessResponse> makeGuess(GuessRequest request) {
        var optionalSession = sessionRepository.getSession(request.sessionId());

        if(optionalSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var session = optionalSession.get();
        if(session.getEnded() || session.getGaveUp()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String guessedGameName = request.guessedGameName().replaceAll("\\s+", " ").trim().toLowerCase().replaceAll(" ", "-");
        var guessedGame = gameRepository.getGameByName(guessedGameName);

        if(guessedGame == null){
            guessedGame = GameMapper.map(igdbService.fetchGameData(guessedGameName)).get(0);
        }

        sessionRepository.incrementAttempts(session.getSessionId());

        if(session.getTargetGameId().equals(guessedGame.getId().toString())){
            session.setEnded(true);
            sessionRepository.saveSession(session);
            return ResponseEntity.ok(
                    new GuessResponse(
                            true,
                            session.getAttemptsMade(),
                            guessedGame,
                            ComparisonService.allMatchCopy(guessedGame)
                    )
            );

        }

        var targetGame = gameRepository.getGameById(session.getTargetGameId());
        var comparison = ComparisonService.compareGames(guessedGame, targetGame);

        return ResponseEntity.ok(
                new GuessResponse(
                        false,
                        session.getAttemptsMade(),
                        guessedGame,
                        comparison
                )
        );

    }


}
