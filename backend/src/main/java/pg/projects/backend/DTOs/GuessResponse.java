package pg.projects.backend.DTOs;

import pg.projects.backend.Models.ComparisonResult;
import pg.projects.backend.Models.Game;

public record GuessResponse(
        Boolean isCorrect,
        Integer attemptsUsed,
        Game guessedGame,
        ComparisonResult comparison
) {
}
