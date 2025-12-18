package pg.projects.backend.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.projects.backend.DTOs.GaveUpResponse;
import pg.projects.backend.DTOs.StartGameResponse;
import pg.projects.backend.Services.GameSessionService;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameSessionService gameSessionService;

    public GameController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/start")
    public StartGameResponse startGame() {
        return gameSessionService.startNewGame();
    }

    @PostMapping("/give-up")
    public GaveUpResponse giveUpGame(@RequestBody StartGameResponse request) {
        return gameSessionService.giveUpGame(request);
    }

    @PostMapping("/guess")
    public String makeGuess(@RequestBody String guessRequest) {
        // Placeholder for guess handling logic
        return "Guess received: " + guessRequest;
    }

}
