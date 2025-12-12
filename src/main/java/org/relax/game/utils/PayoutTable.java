package org.relax.game.utils;

import org.relax.game.models.ClusterRange;
import org.relax.game.models.Symbol;
import org.relax.game.models.Cluster;

import java.util.Map;

/**
 * Payout table utility class to determine payouts based on symbol clusters.
 */
public class PayoutTable {

    private static final Map<Symbol, Map<ClusterRange, Integer>> payoutTable =  Map.of(
                Symbol.H1, Map.of(
                        ClusterRange.R5_8, 5,
                        ClusterRange.R9_12, 6,
                        ClusterRange.R13_16, 7,
                        ClusterRange.R17_20, 8,
                        ClusterRange.R21_PLUS, 10
                ),
                Symbol.H2, Map.of(
                        ClusterRange.R5_8, 4,
                        ClusterRange.R9_12, 5,
                        ClusterRange.R13_16, 6,
                        ClusterRange.R17_20, 7,
                        ClusterRange.R21_PLUS, 9
                ),
                Symbol.H3, Map.of(
                        ClusterRange.R5_8, 4,
                        ClusterRange.R9_12, 5,
                        ClusterRange.R13_16, 6,
                        ClusterRange.R17_20, 7,
                        ClusterRange.R21_PLUS, 9
                ),
                Symbol.H4, Map.of(
                        ClusterRange.R5_8, 3,
                        ClusterRange.R9_12, 4,
                        ClusterRange.R13_16, 5,
                        ClusterRange.R17_20, 6,
                        ClusterRange.R21_PLUS, 7
                ),
                Symbol.L5, Map.of(
                        ClusterRange.R5_8, 1,
                        ClusterRange.R9_12, 2,
                        ClusterRange.R13_16, 3,
                        ClusterRange.R17_20, 4,
                        ClusterRange.R21_PLUS, 5
                ),
                Symbol.L6, Map.of(
                        ClusterRange.R5_8, 1,
                        ClusterRange.R9_12, 2,
                        ClusterRange.R13_16, 3,
                        ClusterRange.R17_20, 4,
                        ClusterRange.R21_PLUS, 5
                ),
                Symbol.L7, Map.of(
                        ClusterRange.R5_8, 1,
                        ClusterRange.R9_12, 2,
                        ClusterRange.R13_16, 3,
                        ClusterRange.R17_20, 4,
                        ClusterRange.R21_PLUS, 5
                ),
                Symbol.L8, Map.of(
                        ClusterRange.R5_8, 1,
                        ClusterRange.R9_12, 2,
                        ClusterRange.R13_16, 3,
                        ClusterRange.R17_20, 4,
                        ClusterRange.R21_PLUS, 5
                )
        );

    public static int getPayout(Symbol symbol, ClusterRange range) {
        return payoutTable.get(symbol).get(range);
    }

    /**
     * Calculate payout based on clusters found in the grid
     * @param cluster cluster to calculate payout from
     */
    public static int calculatePayout(Cluster cluster) {
        int pay = 0;

        //get symbol and size of cluster
        Symbol symbol = cluster.getSymbol();
        int size = cluster.getSize();
        PayoutTable payoutTable = new PayoutTable();
        //determine payout based on symbol and size using payout table
        if (size >= 5 && size <= 8) {
            pay += getPayout(symbol, ClusterRange.R5_8);
        } else if (size >= 9 && size <= 12) {
            pay += getPayout(symbol, ClusterRange.R9_12);
        } else if (size >= 13 && size <= 16) {
            pay += getPayout(symbol, ClusterRange.R13_16);
        } else if (size >= 17 && size <= 20) {
            pay += getPayout(symbol, ClusterRange.R17_20);
        } else if (size >= 21) {
            pay += getPayout(symbol, ClusterRange.R21_PLUS);
        }

        return pay;
    }
}
