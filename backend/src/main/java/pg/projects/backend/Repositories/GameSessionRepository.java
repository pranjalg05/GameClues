package pg.projects.backend.Repositories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import pg.projects.backend.Models.GameSession;
import pg.projects.backend.Util.RedisJsonService;

import java.util.concurrent.TimeUnit;

@Repository
public class GameSessionRepository {

    private static final String SESSION_PREFIX = "session:";
    private static final long SESSION_TTL_MINUTES = 30;

    private final StringRedisTemplate redisTemplate;
    private final RedisJsonService jsonService;

    public GameSessionRepository(
            StringRedisTemplate redisTemplate,
            RedisJsonService jsonService
    ) {
        this.redisTemplate = redisTemplate;
        this.jsonService = jsonService;
    }

    private String key(String sessionId) {
        return SESSION_PREFIX + sessionId;
    }

    public void saveSession(GameSession session) {
        redisTemplate.opsForValue().set(
                key(session.getSessionId()),
                jsonService.convertToString(session),
                SESSION_TTL_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public GameSession getSession(String sessionId) {
        String json = redisTemplate.opsForValue().get(key(sessionId));
        if (json == null) return null;
        return jsonService.convertToObject(json, GameSession.class);
    }

    public boolean incrementAttempts(String sessionId) {
        String redisKey = key(sessionId);
        String json = redisTemplate.opsForValue().get(redisKey);

        if (json == null) return false;

        GameSession session = jsonService.convertToObject(json, GameSession.class);
        session.setAttemptsMade(session.getAttemptsMade() + 1);

        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.MINUTES);

        redisTemplate.opsForValue().set(
                redisKey,
                jsonService.convertToString(session),
                ttl != null && ttl > 0 ? ttl : SESSION_TTL_MINUTES,
                TimeUnit.MINUTES
        );

        return true;
    }

    public void delete(String sessionId) {
        redisTemplate.delete(key(sessionId));
    }
}

