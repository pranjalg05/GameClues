package pg.projects.backend.Repositories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Util.RedisJsonService;

@Repository
public class GameRedisRepository{

    private StringRedisTemplate redisTemplate;
    private RedisJsonService service;

    public GameRedisRepository(StringRedisTemplate redisTemplate, RedisJsonService service){
        this.redisTemplate = redisTemplate;
        this.service = service;
    }

    public void saveGame(Game game){
        redisTemplate.opsForValue().set("game:" + game.getId().toString(), service.convertToString(game));
        addGameToPool(game.getId().toString());
    }

    public Game getGameById(String key){
        String game = redisTemplate.opsForValue().get("game:" + key);
        return service.convertToObject(game, Game.class);
    }

    public boolean existsById(String key){
        return redisTemplate.opsForSet().isMember("game:ids", key);
    }

    public void addGameToPool(String gameKey){
        redisTemplate.opsForSet().add("game:ids", gameKey);
    }

    public String getRandomGameIdFromPool(){
        return (String) redisTemplate.opsForSet().randomMember("game:ids");
    }

}
