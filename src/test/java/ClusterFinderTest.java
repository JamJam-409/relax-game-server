import org.relax.game.models.Cluster;
import org.relax.game.service.ClusterFinder;
import org.relax.game.models.Symbol;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for the ClusterFinder class.
 */
public class ClusterFinderTest {

    /**
     * Test method for finding clusters in a grid.
     */
    @Test
    public void testFindClusters() {
        Symbol[][] grid = {
            {Symbol.H1, Symbol.H1, Symbol.H1, Symbol.H1},
            {Symbol.H1, Symbol.H2, Symbol.H2, Symbol.H1},
            {Symbol.H1, Symbol.H1, Symbol.H1, Symbol.L5},
            {Symbol.L6, Symbol.L6, Symbol.L6, Symbol.L5},
        };
        ClusterFinder cf = new ClusterFinder(4,4);
        List<Cluster> clusters = cf.findClusters(grid);
        assert clusters.size() == 1;
        Cluster cluster = clusters.get(0);
        assert cluster.getSymbol() == Symbol.H1;
        assert cluster.getSize() == 9;
    }

    /**
     * Test method for finding clusters in a grid with wildcards
     */
    @Test
    public void testFindClustersWithWR() {
        Symbol[][] grid = {
                {Symbol.H1, Symbol.H1, Symbol.WR, Symbol.H2},
                {Symbol.WR, Symbol.H2, Symbol.H2, Symbol.H2},
                {Symbol.H1, Symbol.H1, Symbol.H1, Symbol.L5},
                {Symbol.L6, Symbol.L6, Symbol.L6, Symbol.L5},
        };
        ClusterFinder cf = new ClusterFinder(4,4);
        List<Cluster> clusters = cf.findClusters(grid);
        assert clusters.size() == 2;
        Cluster cluster = clusters.get(0);
        assert cluster.getSymbol() == Symbol.H1;
        assert cluster.getSize() == 7;
    }


    /**
     * Test method for finding clusters in a grid with multiple wildcards
     */
    @Test
    public void testFindClustersWithMultiWR() {
        Symbol[][] grid = {
                {Symbol.H1, Symbol.H1, Symbol.WR, Symbol.H2, Symbol.H2},
                {Symbol.L5, Symbol.H4, Symbol.WR, Symbol.L5, Symbol.H4},
                {Symbol.H3, Symbol.WR, Symbol.WR, Symbol.WR, Symbol.H2},
                {Symbol.L7, Symbol.L7, Symbol.WR, Symbol.L7, Symbol.WR},
                {Symbol.H3, Symbol.L7, Symbol.BLOCKER, Symbol.L7, Symbol.H3},
        };
        ClusterFinder cf = new ClusterFinder(5,5);
        List<Cluster> clusters = cf.findClusters(grid);
        assert clusters.size() == 6;
        List<Cluster> filteredCluster = cf.removeSharedWildcards(clusters, grid);
        assert filteredCluster.size() == 1;
        assert filteredCluster.get(0).getSymbol() == Symbol.H1;
    }
}
