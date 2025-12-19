import model.Game;

import java.util.Set;

public class GameNormalizer {

    public String normalize(String name){
        if(name==null) return null;
        return name.trim().toLowerCase()
                .replaceAll("\\s+"," ");
    }

    public Set<String> normalizeSet(Set<String> set){
        if(set==null) return null;
        return set.stream()
                .filter(java.util.Objects::nonNull)
                .map(this::normalize)
                .collect(java.util.stream.Collectors.toSet());
    }

    public Game normalizeGame(Game game){
        game.setName(normalize(game.getName()));
        game.setDeveloper(normalize(game.getDeveloper()));
        game.setPublisher(normalize(game.getPublisher()));
        game.setFranchises(normalizeSet(game.getFranchises()));
        game.setPlatforms(normalizeSet(game.getPlatforms()));
        game.setGenres(normalizeSet(game.getGenres()));
        return game;
    }

}
