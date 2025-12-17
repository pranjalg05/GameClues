package pg.projects.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuessResult {

    private boolean isCorrect;
    private ComparisonResult comparison;
    private Integer attemptsUsed;
    private Boolean gameOver;
    private Game correctGame;

}
