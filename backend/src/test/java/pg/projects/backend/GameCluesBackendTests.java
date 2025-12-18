package pg.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pg.projects.backend.Models.GameSession;
import pg.projects.backend.Repositories.GameRedisRepository;
import pg.projects.backend.Repositories.GameSessionRepository;

@SpringBootTest
class GameCluesBackendTests {


    @Autowired
    GameRedisRepository repository;

    @Autowired
    GameSessionRepository sessionRepository;

    @Test
    public void test(){
        GameSession session = new GameSession();
        session.setSessionId("test-session-123");
        session.setTargetGameId("game-456");
        session.setMaxAttempts(5);
        session.setAttemptsMade(0);
        sessionRepository.saveSession(session);
        int pause = 0;
        sessionRepository.incrementAttempts(session.getSessionId());
        int debughere = 0;
    }

}
