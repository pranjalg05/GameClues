import okhttp3.*;

import java.io.IOException;

public class IgdbClient {

    private static final String IGDB_API_URL = "https://api.igdb.com/v4/games";
    private final OkHttpClient client = new OkHttpClient();

    private final String clientId;
    private final String accessToken;

    public IgdbClient(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    public String fetchGames(int limit, int offset) throws IOException {
        String body = """
                        fields id, name, first_release_date, total_rating, aggregated_rating, game_type,
                               franchises.name,
                               platforms.name,
                               genres.name,
                               involved_companies.company.name,
                               involved_companies.developer,
                               involved_companies.publisher;
                
                        where (game_type = 0 | game_type = 8) & (aggregated_rating != null & aggregated_rating_count >= 5);
                        sort aggregated_rating desc;
                        limit %d;
                        offset %d;
                """.formatted(limit, offset);

        Request request = new Request.Builder()
                .url(IGDB_API_URL)
                .post(RequestBody.create(body, MediaType.parse("text/plain")))
                .addHeader("Client-ID", clientId)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        return client.newCall(request).execute().body().string();
    }

}
