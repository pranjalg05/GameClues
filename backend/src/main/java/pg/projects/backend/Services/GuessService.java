package pg.projects.backend.Services;

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
import pg.projects.backend.Util.GameNormalizer;


@Service
public class GuessService {

    final
    GameRedisRepository gameRepository;

    final
    GameSessionRepository sessionRepository;

    private final IgdbService igdbService;

    public GuessService(GameRedisRepository gameRepository, GameSessionRepository sessionRepository, IgdbService igdbService) {
        this.gameRepository = gameRepository;
        this.sessionRepository = sessionRepository;
        this.igdbService = igdbService;
    }


    public ResponseEntity<GaveUpResponse> giveUpGame(SessionId request) {
        var session = sessionRepository.getSession(request.sessionId());
        if (session == null) {
            return ResponseEntity.notFound().build();
        }

        if(session.getEnded() || session.getGaveUp()) {
            return ResponseEntity.notFound().build();
        }
        var game = gameRepository.getGameById(session.getTargetGameId());
        session.setGaveUp(true);
        session.setEnded(true);
        sessionRepository.saveSession(session);

        GaveUpResponse gaveUpResponse = new GaveUpResponse(
                game,
                session.getAttemptsMade()
        );
        return ResponseEntity.ok(gaveUpResponse);

    }

    public ResponseEntity<GuessResponse> makeGuess(GuessRequest request) {
        var session = sessionRepository.getSession(request.sessionId());

        if(session == null){
            return ResponseEntity.notFound().build();
        }

        if(session.getEnded() || session.getGaveUp()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        String guessedGameName = request.guessedGameName();
        var guessedGame = gameRepository.getGameByName(GameNormalizer.normalizeGameName(guessedGameName));
        sessionRepository.incrementAttempts(session.getSessionId());


        if(guessedGame == null){
            String gameData = igdbService.fetchGameData(guessedGameName);
            guessedGame = GameMapper.map(gameData).getFirst();
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
