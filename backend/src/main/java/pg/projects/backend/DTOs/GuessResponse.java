package pg.projects.backend.DTOs;

import pg.projects.backend.Models.ComparisonResult;

public record GuessResponse(
        Boolean isCorrect,
        Integer attemptsUsed,
        ComparisonResult comparison
) {
}
