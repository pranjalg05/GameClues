package pg.projects.backend.Repositories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Util.RedisJsonService;

@Repository
public class GameRedisRepository{

    private StringRedisTemplate redisTemplate;
    private RedisJsonService service;

    private final String  GAME_KEY_PREFIX = "game:";
    private final String GAME_POOL_KEY = "game:ids";

    public GameRedisRepository(StringRedisTemplate redisTemplate, RedisJsonService service){
        this.redisTemplate = redisTemplate;
        this.service = service;
    }

    private String key(String id){
        return GAME_KEY_PREFIX + id;
    }

    public void saveGame(Game game){
        redisTemplate.opsForValue().set(key(game.getId().toString()), service.convertToString(game));
        addGameToPool(game.getId().toString());
    }

    public Game getGameById(String gameId){
        String game = redisTemplate.opsForValue().get(key(gameId));
        return service.convertToObject(game, Game.class);
    }

    public boolean existsById(String gameId){
        return redisTemplate.opsForSet().isMember(GAME_POOL_KEY, gameId);
    }

    public void addGameToPool(String gameKey){
        redisTemplate.opsForSet().add(GAME_POOL_KEY, gameKey);
    }

    public String getRandomGameIdFromPool(){
        return (String) redisTemplate.opsForSet().randomMember(GAME_POOL_KEY);
    }

}
