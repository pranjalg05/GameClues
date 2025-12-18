package pg.projects.backend.DTOs;

public record GuessRequest(
    String sessionId,
    String guessedGameName
) {
}
