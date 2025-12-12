package org.relax.game.service;

import org.relax.game.models.Cluster;
import org.relax.game.models.Position;
import org.relax.game.models.Symbol;
import org.relax.game.utils.PayoutTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class to find clusters in a grid of symbols
 */
public class ClusterFinder {
    private final int rows;
    private final int cols;

    public ClusterFinder(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Find clusters in the grid using DFS
     * @param grid
     * @return list of clusters found
     */

    public List<Cluster> findClusters(Symbol[][] grid) {
        boolean[][] visited = new boolean[rows][cols];
        List<Cluster> clustersList = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c] && isValidSymbol(grid[r][c])) {
                    Symbol symbol = grid[r][c];
                    if(symbol == Symbol.WR){
                        continue;
                    }
                    List<Position> positions = new ArrayList<>();
                    boolean[][] localVisited = new boolean[rows][cols];
                    int clusterSize = dfs(grid, visited, localVisited, r, c, symbol, positions);
                    if (clusterSize >= 5) {
                        clustersList.add(new Cluster(symbol, clusterSize, positions));
                    }
                }
            }
        }
        return clustersList;
    }

    private int dfs(Symbol[][] grid, boolean[][] visited, boolean[][] localVisited, int r, int c, Symbol baseSymbol, List<Position> positions) {
        // return when out of bounds
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return 0;
        }

        Symbol current = grid[r][c];

        // return when hitting blocker
        if (current == Symbol.BLOCKER) {
            visited[r][c] = true;
            return 0;
        }

        // return when already visited and not a WR
        if(current != Symbol.WR && visited[r][c]) {
            return 0;
        }

        // return when WR is already visited in this search
        if(current == Symbol.WR && localVisited[r][c]) {
            return 0;
        }

        if (current == Symbol.WR) {
            localVisited[r][c] = true;
        }

        if (baseSymbol == Symbol.WR && current != Symbol.WR) {
            baseSymbol = current;
        }
        if (current != baseSymbol && current != Symbol.WR && baseSymbol != Symbol.WR) {
            return 0;
        }

        positions.add(new Position(r, c));
        if (current != Symbol.WR) visited[r][c] = true;
        int size = 1;

        // Visit all 4 directions
        size += dfs(grid, visited, localVisited, r + 1, c, baseSymbol, positions);
        size += dfs(grid, visited, localVisited, r - 1, c, baseSymbol, positions);
        size += dfs(grid, visited, localVisited, r, c + 1, baseSymbol, positions);
        size += dfs(grid, visited,localVisited,  r, c - 1, baseSymbol, positions);

        return size;
    }


    /**
     * Filter cluster list to remove cluster that shares wildcard but has less payout
     * @param clusters
     * @return filtered list of clusters
     */
    public List<Cluster> removeSharedWildcards(List<Cluster> clusters, Symbol[][] grid) {
        List<Cluster> filteredClusters = new ArrayList<>(clusters);
        List<Boolean> toRemove = IntStream.range(0, clusters.size())
                .mapToObj(i -> false)
                .collect(Collectors.toList());

        //set all to false
        for (int i = 0; i < clusters.size(); i++) {
            if(toRemove.get(i) == true){
                continue;
            }
            Cluster clusterA = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                if(toRemove.get(j) == true){
                    continue;
                }
                Cluster clusterB = clusters.get(j);

                boolean sharesWildcard = false;
                for (Position posA : clusterA.getPositions()) {
                    for (Position posB : clusterB.getPositions()) {
                        if (posA.equals(posB) && grid[posA.getRow()][posA.getCol()] == Symbol.WR) {
                            sharesWildcard = true;
                            break;
                        }
                    }
                }

                if (sharesWildcard) {
                    int payoutA = PayoutTable.calculatePayout(clusterA);
                    int payoutB = PayoutTable.calculatePayout(clusterB);
                    if (payoutA >= payoutB) {
                        toRemove.set(j, true);
                    } else {
                        toRemove.set(i, true);
                    }
                }
            }
        }
        //remove clusters marked for removal
        for (int i = 0; i < toRemove.size(); i++) {
            if (toRemove.get(i)) {
                filteredClusters.remove(clusters.get(i));
            }
        }
        return filteredClusters;
    }
    private boolean isValidSymbol(Symbol symbol) {
        return symbol != Symbol.BLOCKER;
    }
}
