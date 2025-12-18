package pg.projects.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameSession {

    private String sessionId;
    private String targetGameId;
    private Integer attemptsMade;
    private Instant startedAt;
    private Boolean gaveUp;
    private Boolean ended;


}
