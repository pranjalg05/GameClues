package pg.projects.backend.Util;

import org.springframework.stereotype.Component;
import pg.projects.backend.Models.Game;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GameMapper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final GameNormalizer normalizer = new GameNormalizer();

    public static List<Game> map(String rawJson){
        List<Game> games = new ArrayList<>();

        JsonNode root = mapper.readTree(rawJson);
        if(!root.isArray()) return games;

        for(JsonNode node: root){

            Game game = new Game();

            game.setId(node.path("id").asLong());
            game.setName(node.path("name").asText(null));
            game.setRating((int) Math.round(node.path("total_rating").asDouble()));

            game.setGenres(extractNameSet(node.path("genres")));
            game.setFranchises(extractNameSet(node.path("franchises")));
            game.setPlatforms(extractNameSet(node.path("platforms")));

            if (node.has("first_release_date")) {
                long epoch = node.get("first_release_date").asLong();
                game.setReleaseYear(Instant.ofEpochSecond(epoch)
                        .atZone(ZoneId.systemDefault())
                        .getYear());
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
                game.setDeveloper(company.path("name").asText(null));
            }

            if (companyNode.path("publisher").asBoolean(false)) {
                game.setPublisher(company.path("name").asText(null));
            }
        }
    }
}
