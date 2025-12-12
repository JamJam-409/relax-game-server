package org.relax.game.models;

import java.util.List;

/**
 * Class representing a cluster of symbols in the game grid.
 * consists of the symbol type, size of the cluster, and positions of the symbols in the cluster.
 */
public class Cluster {
    private final Symbol symbol;
    private final int size;
    private final List<Position> positions;

    public Cluster(Symbol symbol, int size, List<Position> positions) {
        this.symbol = symbol;
        this.size = size;
        this.positions = positions;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getSize() {
        return size;
    }

    public List<Position> getPositions() {
        return positions;
    }
    public void addPosition(Position position){
        this.positions.add(position);
    }
}
