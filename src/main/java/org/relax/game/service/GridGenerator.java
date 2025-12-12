package org.relax.game.service;

import org.relax.game.models.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class to generate a grid of symbols based on predefined weights or custom weights. Also handles avalanche logic.
 */
public class GridGenerator {

    private Map<Symbol, Integer> weights;

    public GridGenerator() {
        this.weights = Map.of(
                Symbol.H1, 100,
                Symbol.H2, 100,
                Symbol.H3, 100,
                Symbol.H4, 100,
                Symbol.L5, 100,
                Symbol.L6, 100,
                Symbol.L7, 100,
                Symbol.L8, 100,
                Symbol.WR, 100,
                Symbol.BLOCKER, 100
        );
    }

    /**
     * Set custom weights for symbols.
     * @param weights Map of symbols to their respective weights.
     */
    public void setWeights(Map<Symbol, Integer> weights) {
        this.weights = weights;
    }

    /**
     * Generate a grid of symbols based on predefined weights.
     * @param rows Number of rows in the grid.
     * @param cols Number of columns in the grid.
     * @return 2D array representing the generated grid of symbols.
     */
    public Symbol[][] generate(int rows, int cols) {
        Random rng = new Random();
        Symbol[][] grid = new Symbol[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = pickRandomSymbol(rng, weights);
            }
        }

        return grid;
    }

    private Symbol pickRandomSymbol(Random rng, Map<Symbol, Integer> customWeights) {
        int totalWeight = customWeights.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = rng.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (Map.Entry<Symbol, Integer> entry : customWeights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue < cumulativeWeight) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Perform avalanche on the grid after clusters have been removed.
     * @param grid
     * @return new grid after avalanche
     */
    public Symbol[][] avalanche(Symbol[][] grid) {
        Map<Symbol, Integer> avalancheWeights = new HashMap<>(weights);
        avalancheWeights.remove(Symbol.BLOCKER);
        int rows = grid.length;
        int cols = grid[0].length;
        Symbol[][] newGrid = new Symbol[rows][cols];
        for (int c = 0; c < cols; c++) {
            int newRow = rows - 1;
            for (int r = rows - 1; r >= 0; r--) {
                if (grid[r][c] != Symbol.RM && grid[r][c] != Symbol.BLOCKER) {
                    newGrid[newRow][c] = grid[r][c];
                    newRow--;
                }
            }
            // Fill remaining cells with new symbols
            for (int r = newRow; r >= 0; r--) {
                newGrid[r][c] = pickRandomSymbol(new Random(), avalancheWeights);
            }
        }
        return newGrid;
    }
}
