
// This class prints game activity and stats to the console
// There are no function descriptions because the functions only contain print statements
public class Printing {

    // GAME

    public void gameMenu() {
        System.out.println("Blackjack Console Edition =======");
        System.out.println(" ");
        System.out.println(">>> Would you like to play Blackjack or run the Game Tests? ");
        System.out.println(">>> PLAY ( p ) or TEST ( t )  ");
    }

    public void askNumberOfPlayers() {
        System.out.println(" ");
        System.out.println("How many players would you like to compete against?");
        System.out.println("( 0 ) or ( 1 ) or ( 2 ) or ( 3 )");
    }

    public void welcomeText() {
        System.out.println(" ");
        System.out.println("Welcome to BLACKJACK ! ! !");
        System.out.println("Closest to 21 wins . . . ");
        System.out.println("Beat the dealer to double your wager . . .");
        System.out.println("Get 21 on your first two cards for a full Blackjack payout!");
    }

    public void introduceRound(int gameRound) {
        System.out.println(" ");
        System.out.println("=============================== GAME ");
        System.out.println("Round " + gameRound + " beginning . . .");
    }

    public void gameOver() {
        System.out.println(" ");
        System.out.println("Game Over. You ran out of credits.");
    }

    public void roundSummaryText() {
        System.out.println(" ");
        System.out.println("=================== ROUND SUMMARY");
    }

    public void offerAnotherRound() {
        System.out.println(" ");
        System.out.println("Do you want to play another round ? ( y ) or ( n )");
        System.out.println("> > > > > ");
    }

    public void goodbye() {
        System.out.println("Thanks for playing Blackjack");
    }


    // DECK

    public void shuffleDeck() {
        System.out.println(" ");
        System.out.println("========================== CARD DECK");
        System.out.println("Shuffling the Deck ...");
        System.out.println("Deck Shuffled.");
    }

    public void dealerDealing() {
        System.out.println(" ");
        System.out.println("The dealer is dealing their cards . . .");
    }


    // CREDITS

    public void allPlayerCredits(Player human, Player comp1, Player comp2, Player comp3) {
        System.out.println(" ");
        System.out.println("========================== CREDITS");
        System.out.println(human.getName() + " has " + human.getPlayerCredits() + " credits in the bank.");
        System.out.println(comp1.getName() + " has " + comp1.getPlayerCredits() + " credits in the bank.");
        System.out.println(comp2.getName() + " has " + comp2.getPlayerCredits() + " credits in the bank.");
        System.out.println(comp3.getName() + " has " + comp3.getPlayerCredits() + " credits in the bank.");
    }

    public void showPlayerCards(Player player, int active) {
        System.out.println(" ");
        System.out.println("=========================== " + player.getName() + " CARDS");
        System.out.println("=== Card Total : " + player.getCardsTotal());
        if (active == 1) {
            for (int i = 0; i < player.getPlayerCards().length; i++) {
                System.out.println(player.getPlayerCards()[i].displayCard());
            }

        } else {
            System.out.println(player.getName() + " is not in the game.");
        }
    }

    public void removeCredits(int credits) {
        System.out.println("Removed " + credits + " credits from Human's Bank");
    }


    // CARDS

    public void finalDealerCards(int dealerTotal, Card[] dealerCards) {
        System.out.println(" ");
        System.out.println("============================ Dealer CARDS");
        System.out.println("=== Card Total : " + dealerTotal + "");

        for (int i = 0; i < dealerCards.length; i++) {
            System.out.println(dealerCards[i].displayCard());
        }

    }

    public void showADealerCard(Card[] dealerCards) {
        System.out.println(" ");
        System.out.println("============================ Dealer CARDS");
        System.out.println("=== Card Total : " + dealerCards[0].getValue() + "");
        System.out.println(dealerCards[0].displayCard()); // only show first card at start of round
        System.out.println(" ");
    }


    // WAGERS

    public void askForWager() {
        System.out.println(" ");
        System.out.println("Please type in your wager then press enter . . .");
        System.out.println("( 250 ) or ( 500 ) or ( 750 ) or ( 1000 )");
        System.out.println("> > >");
    }

    public void compWagerAmount(String name, int wager) {
        System.out.println(name + " has wagered " + wager);
    }

    public void humanCurrentWager(int wager) {
        System.out.println("Current wager is " + wager + " credits");
    }

    public void playerWager(Player player) {
        System.out.println(" ");
        System.out.println(player.getName() + " has wagered " + player.getCurrentWager());    }


    // DOUBLE DOWN

    public void offerDoubleDown() {
        System.out.println(" ");
        System.out.println("Do you want to double down on your wager? ( y ) or ( n )");
        System.out.println("You will have to draw 1 (and only 1) more card . . .");
        System.out.println("> > >");
    }

    public void humanDoubleDown(int humanTotal, Card newCard) {
        System.out.println(" ");
        System.out.println(">>>>>> Your new card is " + newCard.displayCard());
        System.out.println(">>>>>> Your new card total : " + humanTotal);
        System.out.println(" ");
        System.out.println("Double down card has been played, continuing game ...");
        System.out.println(" ");
        System.out.println("Settling with computer players ...");
        System.out.println(" ");
    }

    public void compDoubleDown(String name) {
        System.out.println(name + " has doubled down their wager");
    }


    // HIT or STAND

    public void humanHitCard(int humanTotal, Card newCard) {
        System.out.println(" ");
        System.out.println(">>>>>> Your new card is " + newCard.displayCard());
        System.out.println(">>>>>> Your new card total : " + humanTotal + "");
        System.out.println(" ");
    }

    public void compHitCard(String name, Card newCard) {
        System.out.println(name + " has drawn a new card: " + newCard.displayCard());
    }

    public void humanStanding(int humanTotal) {
        System.out.println("You have chosen to stand on " + humanTotal);
        System.out.println(" ");
    }

    public void chooseHitOrStand() {
        System.out.println(" ");
        System.out.println("P1 Choose Your Action: HIT ( h ) or STAND ( s )");
        System.out.println("> > >");
    }


    // ACES

    public void aceChangeChoice() {
        System.out.println(" ");
        System.out.println("Do you want to change your ace value from 1 to 11? : press ( y ) or ( n )");
        System.out.println("> > >");
    }

    public void aceChanged(String name, int total) {
        System.out.println(name + " changed an ace in their deck. Their new card total is " + (total));
    }


    // END ROUND

    public void gameOutcomeWon(String name, int creditsWon, int cardTotal) {
        System.out.println(name + " won " + creditsWon + " (Total: " + cardTotal + ")");
    }

    public void gameOutcomeRefunded(String name, int creditsRefunded, int cardTotal) {
        System.out.println(name + " was refunded " + creditsRefunded + " (Total: " + cardTotal + ")");
    }

    public void gameOutcomeLost(String name, int creditsLost, int cardTotal) {
        System.out.println(name + " lost " + creditsLost + " (Total: " + cardTotal + ")");
    }

    public void gameOutcomeBlackjack(String name, int payout) {
        System.out.println(name + " has Blackjack! They win " + payout + " credits! (Total: 21)");
    }

    public void gameOutcomeBust(Player player) {
        System.out.println(player.getName() + " has lost " + (player.getCurrentWager()) + " after going bust (Total: " + player.getCardsTotal() + ")");
    }


    // ERRORS

    public void cardDeckError() {
        System.out.println("ERROR! cardName's value is out of bounds");
    }

    public void yesOrNoInputError() {
        System.out.println("Please enter 'y' or 'n' . . .");
    }

    public void hitStandInputError() {
        System.out.println(" ");
        System.out.println("Please press 'h' or 's' to choose");
        System.out.println(" ");
    }

    public void errorWager() {
        System.out.println("Please enter a correct wager (e.g. 250) . . .");
    }

    public void aceChoiceError() {
        System.out.println("Please press 'y' or 'n' to choose ... ");
    }

    public void activeCheckError() {
        System.out.println("Error in Active Check");
    }

    public void outOfGame(Player player) {
        System.out.println(player.getName() + " is out of the game.");
    }

    public void errorPlayerNumbers() {
        System.out.println(" ");
        System.out.println("Please enter a valid number from 0 to 3 . . .");
    }

    public void errorMenu() {
        System.out.println("I didn't recognise that input. Please type 'p' (PLAY) or 't' (TEST) ");
    }

    // PRINT TESTS =================

    // tests the string display of each card is correct by printing it to the screen
    void testDeckString(Card[] cardDeck) {
        for (int i = 0; i < cardDeck.length; i++) { // loop through deck
            System.out.println(cardDeck[i].displayCard()); // displayCard() is a function in the Card class
        }
    }
}
