package org.relax.game.service;

import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import org.relax.game.models.Cluster;
import org.relax.game.models.Position;
import org.relax.game.models.Response;
import org.relax.game.models.Symbol;
import org.relax.game.utils.PayoutTable;

public class GameService {

    // game grid that is 8*8 in size
    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 8;
    private Symbol[][] grid = new Symbol[ROW_SIZE][COL_SIZE];
    private final GridGenerator gridGenerator = new GridGenerator();
    private static final Logger logger = Logger.getLogger(GameService.class.getName());
    List<Cluster> clusters;

    private int payout = 0;

    @VisibleForTesting
    public void simulateNewGame(){
        payout = 0;
        initGrid();

        calculateClusters();
        while( !clusters.isEmpty()){
            calculatePayout();
            updateGrid();
            calculateClusters();
        }
        finalRound();
    }

    /**
     * Initialize the game grid with random symbols based on defined weights
     */
    public void initGrid(){
        // generate initial 8*8 grid with random symbols
       grid = gridGenerator.generate(ROW_SIZE, COL_SIZE);
    }


     private void calculateClusters(){
        //find clusters of symbols in the grid using dfs, if cluster size >=5, add to clusters map
        //update the grid by marking the symbols in the cluster for removal
        ClusterFinder clusterFinder = new ClusterFinder(ROW_SIZE,COL_SIZE);
        List<Cluster> allClusters = clusterFinder.findClusters(grid);
        clusters =  clusterFinder.removeSharedWildcards(allClusters, grid);

    }

    /**
     * avalanche the grid by removing clusters and updating the grid
     * remove the grid and blockers connected to the clusters
     * shift symbols down and fill empty spaces with new symbols based on updated weights
     */
    public void updateGrid(){
        for(Cluster cluster : clusters){
            for(Position pos : cluster.getPositions()){
                grid[pos.getRow()][pos.getCol()] = Symbol.RM; //mark for removal
            }
        }
        grid = gridGenerator.avalanche(grid);

    }

    /**
     * Calculate payout based on clusters found in the grid
     */
     private int calculatePayout() {
        int roundPayout = 0;
        for (Cluster cluster : clusters) {
            roundPayout += PayoutTable.calculatePayout(cluster);
        }
        payout += roundPayout;
        return roundPayout;
    }

    /**
     * Calculate a single round: find clusters, calculate payout
     * @return payout for the round
     */
    public int calculateRound() {
        calculateClusters();
        return calculatePayout();
    }

    /**
     * Final round with 50% chance to double the payout
     */
    public void finalRound(){
        //50% chance to double the payout
        payout = Math.random() < 0.5 ? payout * 2 : payout;
    }

    /**
     * Get the total payout after the game ends
     * @return payout for the game
     */
    public int getPayout() {
        return payout;
    }

    /**
     * Get the current game grid
     * @return 2D array of Symbols representing the grid
     */
    public Symbol[][] getGrid() {
        return grid;
    }

    /**
     * Get current clusters
     * @return list of clusters
     */
    public List<Cluster> getClusters() {
        return clusters;
    }
}


