package pg.projects.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import pg.projects.backend.Util.GameMapper;
import tools.jackson.databind.ObjectMapper;

@Service
public class IgdbService {

    @Autowired
    private WebClient igdbWebClient;




    private String getQuery(String gameName) {
        return """
                search "%s";
                fields id, name, first_release_date, total_rating, aggregated_rating, game_type,
                    franchises.name,
                    platforms.name,
                    genres.name,
                    involved_companies.company.name,
                    involved_companies.developer,
                    involved_companies.publisher;    
                    limit 1;                  
                """.formatted(gameName);
    }

    public String fetchGameData(String gameName) {
        String query = getQuery(gameName);
        return igdbWebClient.post()
                .bodyValue(query)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


}
