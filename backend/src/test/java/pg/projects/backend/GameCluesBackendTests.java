package pg.projects.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class GameCluesBackendTests {


    @Disabled
    public void test(){
        template.opsForValue().set("123", "345");
        int k = 0;
    }

}
