package pg.projects.backend.Util;

import org.springframework.stereotype.Component;
import pg.projects.backend.Models.Game;

import java.util.Objects;
import java.util.Set;

@Component
public class GameNormalizer {

    public String normalize(String name){
        if(name==null) return null;
        return name.trim().toLowerCase()
                .replaceAll("\\s+"," ");
    }

    public Game normalizeGame(Game game){
        game.setName(normalize(game.getName()));
        game.setDeveloper(normalize(game.getDeveloper()));
        game.setPublisher(normalize(game.getPublisher()));
        game.setFranchices(normalizeSet(game.getFranchices()));
        game.setPlatforms(normalizeSet(game.getPlatforms()));
        game.setGenres(normalizeSet(game.getGenres()));
        return game;
    }

    public Set<String> normalizeSet(Set<String> set){
        if(set==null) return null;
        return set.stream()
                .filter(Objects::nonNull)
                .map(this::normalize)
                .collect(java.util.stream.Collectors.toSet());
    }

}
