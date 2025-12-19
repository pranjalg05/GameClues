import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMapper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static GameNormalizer normalizer = new GameNormalizer();

    public static List<Game> map(String rawJson) throws Exception {
        List<Game> games = new ArrayList<>();

        JsonNode root = mapper.readTree(rawJson);
        if(!root.isArray()) return games;

        for(JsonNode node: root){

            Game game = new Game();

            game.id = node.path("id").asLong();
            game.name = node.path("name").asText(null);

            game.rating = (int) Math.round(node.path("total_rating").asDouble());

            game.genres = extractNameSet(node.path("genres"));
            game.franchises = extractNameSet(node.path("franchises"));
            game.platforms = extractNameSet(node.path("platforms"));

            if (node.has("first_release_date")) {
                long epoch = node.get("first_release_date").asLong();
                game.releaseYear = Instant.ofEpochSecond(epoch)
                        .atZone(ZoneId.systemDefault())
                        .getYear();
            }

            extractCompanies(node, game);

            normalizer.normalizeGame(game);

            games.add(game);

        }

        return games;

    }

    private static Set<String> extractNameSet(JsonNode arrayNode) {
        Set<String> result = new HashSet<>();
        if (!arrayNode.isArray()) return result;
        for (JsonNode node : arrayNode) {
            String name = node.path("name").asText(null);
            if (name != null && !name.isBlank()) {
                result.add(name);
            }
        }
        return result;
    }

    private static void extractCompanies(JsonNode node, Game game) {
        JsonNode companies = node.path("involved_companies");
        if (!companies.isArray()) return;

        for (JsonNode companyNode : companies) {
            JsonNode company = companyNode.path("company");

            if (companyNode.path("developer").asBoolean(false)) {
                game.developer = company.path("name").asText(null);
            }

            if (companyNode.path("publisher").asBoolean(false)) {
                game.publisher = company.path("name").asText(null);
            }
        }
    }

}
