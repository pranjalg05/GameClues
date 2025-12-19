package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Game {

    public Long id;
    public String name;
    public Integer releaseYear;
    public Integer rating;
    public Set<String> franchises;
    public Set<String> platforms;
    public Set<String> genres;
    public String developer;
    public String publisher;


}

