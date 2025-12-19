package pg.projects.backend.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Long id;
    private String name;
    private Integer releaseYear;
    private Integer rating;
    private Set<String> franchises;
    private Set<String> platforms;
    private Set<String> genres;
    private String developer;
    private String publisher;


}
