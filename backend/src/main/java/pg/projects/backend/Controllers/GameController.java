package pg.projects.backend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.projects.backend.DTOs.GaveUpResponse;
import pg.projects.backend.DTOs.GuessRequest;
import pg.projects.backend.DTOs.GuessResponse;
import pg.projects.backend.DTOs.SessionId;
import pg.projects.backend.Services.GameSessionService;
import pg.projects.backend.Services.GuessService;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameSessionService gameSessionService;
    private final GuessService guessService;

    public GameController(GameSessionService gameSessionService, GuessService guessService) {
        this.gameSessionService = gameSessionService;
        this.guessService = guessService;
    }

    @PostMapping("/start")
    public SessionId startGame() {
        return gameSessionService.startNewGame();
    }

    @PostMapping("/give-up")
    public GaveUpResponse giveUpGame(@RequestBody SessionId request) {
        return guessService.giveUpGame(request);
    }

    @PostMapping("/guess")
    public ResponseEntity<GuessResponse> makeGuess(@RequestBody GuessRequest guessRequest) {
        return guessService.makeGuess(guessRequest);
    }

}
