package org.relax.game.models;

/**
 * Class representing the response of a game action, including the resulting grid and pay amount.
 */
public class Response {
    Symbol[][] grid;
    int totalWin;
    int roundWin;
    public Response(Symbol[][] grid, int totalWin, int roundWin) {
        this.grid = grid;
        this.totalWin = totalWin;
        this.roundWin = roundWin;
    }

    public int getPay() {
        return totalWin;
    }

    public Symbol[][] getGrid() {
        return grid;
    }

    public int getRoundWin() {
        return roundWin;
    }
}
