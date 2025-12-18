package pg.projects.backend.Repositories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Util.GameNormalizer;
import pg.projects.backend.Util.RedisJsonService;

@Repository
public class GameRedisRepository{

    private StringRedisTemplate redisTemplate;
    private RedisJsonService service;
    private GameNormalizer normalizer;

    private final String  GAME_KEY_PREFIX = "game:";
    private final String GAME_POOL_KEY = "game:ids";
    private final String GAME_NAME_POOL_KEY = "game:name:";

    public GameRedisRepository(StringRedisTemplate redisTemplate,
                               RedisJsonService service,
                               GameNormalizer normalizer) {
        this.redisTemplate = redisTemplate;
        this.service = service;
        this.normalizer = normalizer;
    }

    private String IDkey(String id){
        return GAME_KEY_PREFIX + id;
    }

    private String nameKey(String name){
        return GAME_NAME_POOL_KEY + name;
    }

    public void saveGame(Game game){
        game = normalizer.normalizeGame(game);
        redisTemplate.opsForValue().set(IDkey(game.getId().toString()), service.convertToString(game));
        redisTemplate.opsForValue().set(nameKey(game.getName()), game.getId().toString());
        addGameToPool(game.getId().toString());
    }

    public Game getGameById(String gameId){
        String game = redisTemplate.opsForValue().get(IDkey(gameId));
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

    public Game getGameByName(String name){
        String gameId = redisTemplate.opsForValue().get(nameKey(name));
        if(gameId == null) return null;
        return getGameById(gameId);
    }
}
