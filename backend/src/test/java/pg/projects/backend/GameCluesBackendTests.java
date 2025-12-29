package pg.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Services.IgdbService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
class GameCluesBackendTests {

    @Value("classpath:games.json")
    Resource resource;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws Exception{

        var gameList = mapper.readValue(resource.getInputStream(), new TypeReference<List<Game>>() {
        });
        int x = 10;

    }

}
