package pg.projects.backend.Repositories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import pg.projects.backend.Models.GameSession;
import pg.projects.backend.Util.RedisJsonService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class GameSessionRepository {

    private StringRedisTemplate redisTemplate;
    private RedisJsonService service;

    public GameSessionRepository(
            StringRedisTemplate template,
            RedisJsonService service
    ) {
        this.redisTemplate = template;
        this.service = service;
    }

    public void saveSession(GameSession session){
        redisTemplate.opsForValue().set("session:"+session.getSessionId(), service.convertToString(session), 30, TimeUnit.MINUTES);
    }

    public Optional<GameSession> getSession(String key){
        String s = redisTemplate.opsForValue().get("session:" + key);
        return Optional.ofNullable(service.convertToObject(s, GameSession.class));
    }

}
