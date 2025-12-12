import org.relax.game.service.GridGenerator;
import org.relax.game.models.Symbol;
import org.junit.Test;

import java.util.Map;

/*
 * Unit test class for GridGenerator
 */
public class GridGeneratorTest {

    /**
     * Test generating a grid with default weights
     */
    @Test
    public void testGenerateGrid() {
        int rows = 5;
        int cols = 5;
        GridGenerator generator = new GridGenerator();
        Symbol[][] grid = generator.generate(rows, cols);

        // Verify the grid dimensions
        assert grid.length == rows : "Grid row count mismatch";
        assert grid[0].length == cols : "Grid column count mismatch";

        // Verify that all symbols are valid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                assert grid[r][c] != null : "Grid contains null symbol at (" + r + "," + c + ")";
            }
        }
    }

    /**
     * Test generating a grid with custom weights
     */
    @Test
    public void testCustomWeights() {
        int rows = 8;
        int cols = 8;
        GridGenerator generator = new GridGenerator();

        // Set custom weights
        generator.setWeights(Map.of(
                Symbol.H1, 100,
                Symbol.H2, 0,
                Symbol.H3, 0,
                Symbol.H4, 0,
                Symbol.L5, 0,
                Symbol.L6, 0,
                Symbol.L7, 0,
                Symbol.L8, 0,
                Symbol.WR, 0,
                Symbol.BLOCKER, 0
        ));

        Symbol[][] grid = generator.generate(rows, cols);

        // Verify the grid dimensions
        assert grid.length == rows : "Grid row count mismatch";
        assert grid[0].length == cols : "Grid column count mismatch";

        // Verify that all symbols are valid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                assert grid[r][c] == Symbol.H1 : "Grid contains unexpected symbol at (" + r + "," + c + ")";
            }
        }
    }
}
