package pg.projects.backend.DTOs;

import pg.projects.backend.Models.Game;

public record GaveUpResponse(
        Game correctGame,
        Integer attemptsUsed
) {
}
