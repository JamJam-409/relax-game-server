import org.relax.game.service.GameService;
import org.junit.Test;

/**
 * Test class for GameService, check if game can be played correctly and calculate return to player
 */
public class GameServiceTest {

    GameService gameService= new GameService();

    /**
     * Test playing multiple game rounds and calculating the RTP (Return to Player).
     * Test result for 1M runs:
     *      Total Bet: 10000000
     *      Total Win: 8196490
     *      RTP: 81.9649%
     */
    @Test
    public void testPlayGameAndCalculateRTP() {
        int rounds = 1000; // put 1000 here quicker unit test
        long totalBet = 0;
        long totalWin = 0;

        for (int i = 0; i < rounds; i++) {
            totalBet += 10; // a bet is 10 euros
            gameService.simulateNewGame();
            totalWin += gameService.getPayout();

        }

        double rtp = ((double) totalWin / totalBet) * 100;
        System.out.println("Total Bet: " + totalBet);
        System.out.println("Total Win: " + totalWin);
        System.out.println("RTP: " + rtp + "%");

        assert rtp <= 100 : "RTP out of expected range: " + rtp;
    }

}
