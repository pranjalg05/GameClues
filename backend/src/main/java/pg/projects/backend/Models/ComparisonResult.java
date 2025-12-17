package pg.projects.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pg.projects.backend.Models.Enums.BinaryComparison;
import pg.projects.backend.Models.Enums.NumericComparison;
import pg.projects.backend.Models.Enums.PlatformComparison;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparisonResult {

    NumericComparison releaseYear;
    NumericComparison rating;
    BinaryComparison franchise;
    PlatformComparison platforms;
    BinaryComparison genres;
    BinaryComparison developer;
    BinaryComparison publisher;


}
