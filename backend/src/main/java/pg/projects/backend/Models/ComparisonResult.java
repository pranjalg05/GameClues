package pg.projects.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pg.projects.backend.Models.Enums.BinaryComparison;
import pg.projects.backend.Models.Enums.NumericComparison;
import pg.projects.backend.Models.Enums.PlatformComparison;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparisonResult {

    NumericComparison releaseYear;
    NumericComparison rating;
    BinaryComparison franchise;
    PlatformComparison platforms;
    Set<String> platformsMatched;
    PlatformComparison genres;
    Set<String> genresMatched;
    BinaryComparison developer;
    BinaryComparison publisher;


}
