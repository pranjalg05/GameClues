import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int PAGE_SIZE = 100;
    private static final int TARGET_COUNT = 500;

    public static void main(String[] args) throws Exception {

        String clientId = System.getenv("CLIENT_ID");
        String token = System.getenv("ACCESS_TOKEN");

        IgdbClient igdb = new IgdbClient(clientId, token);
        ObjectMapper mapper = new ObjectMapper();

        List<Game> allGames = new ArrayList<>();
        int offset = 0;

        while (allGames.size() < TARGET_COUNT) {
            System.out.println("Fetching offset " + offset);

            String json = igdb.fetchGames(PAGE_SIZE, offset);
            List<Game> games = GameMapper.map(json);

            if (games.isEmpty()) break;

            allGames.addAll(games);
            offset += PAGE_SIZE;
        }

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("games.json"), allGames);

        System.out.println("Saved " + allGames.size() + " games.");
    }
}
