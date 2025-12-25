package pg.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pg.projects.backend.Services.IgdbService;

@SpringBootTest
class GameCluesBackendTests {

    @Autowired
    IgdbService guessService;

    @Test
    public void test(){

        String gameData = guessService.fetchGameData("god of war");
        int t = 1;



    }

}
