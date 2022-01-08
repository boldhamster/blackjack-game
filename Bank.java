
// This class deals with player blackjacks, checking if double down is available and paying players
public class Bank {

    // Function checks if a player has blackjack and pays them
    public void checkPlayerBlackjack(Player player) {
        if (lookForBlackjack(player)) {
            payPlayerBlackjack(player);
            player.setBlackjack(true);
        }
    }

    // Function checks if a player's card value total is 21
    public boolean lookForBlackjack(Player player) {
        boolean blackjack = false;
        if (player.getCardsTotal() == 21) {
            player.setPlayerStanding(true);
            blackjack = true;
        }
        return blackjack;
    }

    // Function pays players for Blackjack wins
    public void payPlayerBlackjack(Player player) {
        int winnings = calculatePayout(player.getCurrentWager());
        player.addPlayerCredits(winnings);
    }


    // Function calculates player Blackjack payment based on their wager
    public int calculatePayout(int wager) {
        int payout = 0;

        if (wager == 250) {
            payout = 750;
        } else if (wager == 500) {
            payout = 1500;
        } else if (wager == 750) {
            payout = 2000;
        } else if (wager == 1000) {
            payout = 2500;
        }
        return payout;
    }

    // Function checks if a player has enough credits to double down
    public boolean canDoubleDown(Player player) {
        int credits = player.getPlayerCredits();
        int wager = player.getCurrentWager();

        if (credits > 0 && credits >= wager) {
            return true;
        } else {
            return false;
        }
    }

    // Function pays players for non-Blackjack wins
    public void payPlayer(Player player) {
        player.addPlayerCredits(player.getCurrentWager() * 2);
    }

    // Function refunds players for drawn rounds
    public void refundPlayer(Player player) {
        player.addPlayerCredits(player.getCurrentWager());
    }

}
