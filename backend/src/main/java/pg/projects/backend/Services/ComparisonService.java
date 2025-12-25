package pg.projects.backend.Services;

import org.springframework.stereotype.Service;
import pg.projects.backend.Models.ComparisonResult;
import pg.projects.backend.Models.Enums.BinaryComparison;
import pg.projects.backend.Models.Enums.NumericComparison;
import pg.projects.backend.Models.Enums.PlatformComparison;
import pg.projects.backend.Models.Game;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComparisonService {

    public static ComparisonResult allMatchCopy(Game game) {
        return new ComparisonResult(
                NumericComparison.EQUAL,
                NumericComparison.EQUAL,
                BinaryComparison.SAME,
                PlatformComparison.FULL_MATCH,
                game.getPlatforms(),
                PlatformComparison.FULL_MATCH,
                game.getGenres(),
                BinaryComparison.SAME,
                BinaryComparison.SAME
        );
    }

    public static ComparisonResult compareGames(Game guessedGame, Game targetGame) {
        ComparisonResult comparison = new ComparisonResult();

        // Release Year Comparison
        if (guessedGame.getReleaseYear() != null && targetGame.getReleaseYear() != null) {
            if (guessedGame.getReleaseYear().equals(targetGame.getReleaseYear())) {
                comparison.setReleaseYear(NumericComparison.EQUAL);
            } else if (guessedGame.getReleaseYear() < targetGame.getReleaseYear()) {
                comparison.setReleaseYear(NumericComparison.LOWER);
            } else {
                comparison.setReleaseYear(NumericComparison.HIGHER);
            }
        } else {
            comparison.setReleaseYear(NumericComparison.UNKNOWN);
        }

        // Rating Comparison
        if (guessedGame.getRating() != null && targetGame.getRating() != null) {
            if (guessedGame.getRating().equals(targetGame.getRating())) {
                comparison.setRating(NumericComparison.EQUAL);
            } else if (guessedGame.getRating() < targetGame.getRating()) {
                comparison.setRating(NumericComparison.LOWER);
            } else {
                comparison.setRating(NumericComparison.HIGHER);
            }
        } else {
            comparison.setRating(NumericComparison.UNKNOWN);
        }

        // Franchise Comparison
        if (guessedGame.getFranchises() != null && targetGame.getFranchises() != null) {
            if (guessedGame.getFranchises().equals(targetGame.getFranchises())) {
                comparison.setFranchise(BinaryComparison.SAME);
            } else {
                comparison.setFranchise(BinaryComparison.DIFFERENT);
            }
        } else {
            comparison.setFranchise(BinaryComparison.UNKNOWN);
        }

        // Developer Comparison
        if (guessedGame.getDeveloper() != null && targetGame.getDeveloper() != null) {
            if (guessedGame.getDeveloper().equals(targetGame.getDeveloper())) {
                comparison.setDeveloper(BinaryComparison.SAME);
            } else {
                comparison.setDeveloper(BinaryComparison.DIFFERENT);
            }
        } else {
            comparison.setDeveloper(BinaryComparison.UNKNOWN);
        }

        // Publisher Comparison
        if (guessedGame.getPublisher() != null && targetGame.getPublisher() != null) {
            if (guessedGame.getPublisher().equals(targetGame.getPublisher())) {
                comparison.setPublisher(BinaryComparison.SAME);
            } else {
                comparison.setPublisher(BinaryComparison.DIFFERENT);
            }
        } else {
            comparison.setPublisher(BinaryComparison.UNKNOWN);
        }

        // Platform Comparison
        if (guessedGame.getPlatforms() != null && targetGame.getPlatforms() != null) {
            if (guessedGame.getPlatforms().equals(targetGame.getPlatforms())) {
                comparison.setPlatforms(PlatformComparison.FULL_MATCH);
            } else if (guessedGame.getPlatforms().stream().anyMatch(targetGame.getPlatforms()::contains)) {
                Set<String> matchedPlatforms = guessedGame.getPlatforms().stream().filter(targetGame.getPlatforms()::contains).collect(Collectors.toSet());
                comparison.setPlatformsMatched(matchedPlatforms);
                comparison.setPlatforms(PlatformComparison.PARTIAL_MATCH);
            } else {
                comparison.setPlatforms(PlatformComparison.NO_MATCH);
            }
        } else {
            comparison.setPlatforms(PlatformComparison.UNKNOWN);
        }

        // Genre Comparison
        if (guessedGame.getGenres() != null && targetGame.getGenres() != null) {
            if (guessedGame.getGenres().equals(targetGame.getGenres())) {
                comparison.setGenres(PlatformComparison.FULL_MATCH);
            } else if (guessedGame.getGenres().stream().anyMatch(targetGame.getGenres()::contains)) {
                Set<String> matchedGenres = guessedGame.getGenres().stream().filter(targetGame.getGenres()::contains).collect(Collectors.toSet());
                comparison.setGenresMatched(matchedGenres);
                comparison.setGenres(PlatformComparison.PARTIAL_MATCH);
            } else {
                comparison.setGenres(PlatformComparison.NO_MATCH);
            }
        } else {
            comparison.setGenres(PlatformComparison.UNKNOWN);
        }

        return comparison;
    }


}
