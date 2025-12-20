package pg.projects.backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IgdbConfig {

    @Bean
    WebClient igdbWebClient(
            @Value("${igdb.client-id}") String clientId,
            @Value("${igdb.access-token}") String accessToken,
            @Value("${igdb.base-url}/games") String baseUrl
            ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Client-ID", clientId)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();

    }
}
