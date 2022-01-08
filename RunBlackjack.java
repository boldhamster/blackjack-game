
// This class creates instances of game objects and uses them to run the game
public class RunBlackjack {

    private int[] activePlayers = new int[4]; // keeps track of which players still have credits
    private CardDeck cardDeck;
    private Player human = new Player("Human");
    private Player comp1 = new Player("Comp1");
    private Player comp2 = new Player("Comp2");
    private Player comp3 = new Player("Comp3");
    private Dealer dealer = new Dealer();
    private PlayerInput playerInput = new PlayerInput(); // holds all player input functions
    private Bank bank = new Bank(); // bank for credit payout, blackjack and double down checks
    private int gameRound = 0; // round number
    private Printing printing = new Printing(); // used for all console printouts

    // This Constructor welcomes the player to the game and calls a function for a new game round
    // -- the constructor and some functions use Thread.sleep(mls), which requires an InterruptedException
    public RunBlackjack(int numPlayers) throws InterruptedException {
        printing.welcomeText();
        cardDeck = new CardDeck();

        if (numPlayers == 3) {
            activePlayers[0] = 1; // (1 = active, 0 = out)
            activePlayers[1] = 1;
            activePlayers[2] = 1;
            activePlayers[3] = 1;
        } else if (numPlayers == 2) {
            activePlayers[0] = 1;
            activePlayers[1] = 1;
            activePlayers[2] = 1;
            activePlayers[3] = 0;
            comp3.removePlayerCredits(2000);
        } else if (numPlayers == 1) {
            activePlayers[0] = 1;
            activePlayers[1] = 1;
            activePlayers[2] = 0;
            activePlayers[3] = 0;
            comp2.removePlayerCredits(2000);
            comp3.removePlayerCredits(2000);
        } else if (numPlayers == 0) {
            activePlayers[0] = 1;
            activePlayers[1] = 0;
            activePlayers[2] = 0;
            activePlayers[3] = 0;
            comp1.removePlayerCredits(2000);
            comp2.removePlayerCredits(2000);
            comp3.removePlayerCredits(2000);
        }

        pauseGameForEffect(500); // to prevent the game running too quickly in the console
        startGameRound();
    }

    // This Function runs the start of every new round, dealing the first 2 cards then checking the state of the game
    public void startGameRound() throws InterruptedException {

        setGameRound(getGameRound() + 1);
        removeBankruptPlayers(); // check players have credits and update activePlayers array
        resetTotals(); // wipe player cards and totals
        printing.allPlayerCredits(human, comp1, comp2, comp3);
        cardDeck.wipeDealtCards(); // tracks the cards that have been dealt already
        cardDeck.loadTheDeck();
        cardDeck.shuffleTheDeck();
        pauseGameForEffect(1500);
        playerInput.allPlayerWagers(activePlayers, human, comp1, comp2, comp3); // get wagers from active players
        pauseGameForEffect(1000);
        printing.introduceRound(getGameRound());
        pauseGameForEffect(1000);
        dealStartingCards(); // deal each active player 2 starting cards

        printing.showPlayerCards(human, activePlayers[0]); // print the cards if player is active
        pauseGameForEffect(1000);
        printing.showPlayerCards(comp1, activePlayers[1]);
        pauseGameForEffect(1000);
        printing.showPlayerCards(comp2, activePlayers[2]);
        pauseGameForEffect(1000);
        printing.showPlayerCards(comp3, activePlayers[3]);
        pauseGameForEffect(1000);
        printing.showADealerCard(dealer.getDealerCards());
        pauseGameForEffect(1000);

        offerAceChange(); // checks if any players have an ace and offers them the "change 1 to 11" option
        bank.checkPlayerBlackjack(human); // pay players if they have blackjack on opening 2 cards
        bank.checkPlayerBlackjack(comp1);
        bank.checkPlayerBlackjack(comp2);
        bank.checkPlayerBlackjack(comp3);
        human.makeBust(); // remove players from the round if their card total is bust (22+)
        comp1.makeBust();
        comp2.makeBust();
        comp3.makeBust();

        finishGameRound();
    }

    public void finishGameRound() throws InterruptedException {

        offerDoubleDown(); // check if players can double down and offer option
        pauseGameForEffect(1000);
        playerInput.humanHitStand(cardDeck, human);  // human has to be active for game to be here, get input
        playerInput.compHitStand(activePlayers[1], cardDeck, comp1); // only get active players' input
        playerInput.compHitStand(activePlayers[2], cardDeck, comp2);
        playerInput.compHitStand(activePlayers[3], cardDeck, comp3);
        printing.dealerDealing();
        pauseGameForEffect(1500);
        dealer.drawDealerCards(cardDeck);
        pauseGameForEffect(1500);
        human.makeBust();
        comp1.makeBust();
        comp2.makeBust();
        comp3.makeBust();

        compareCards(); //  compares all remaining players' cards with the dealers and prints the results
        pauseGameForEffect(1000);
        removeBankruptPlayers();

        if (checkIfActive(0)) { // offer to start new round if human player still has credits
            if (playerInput.humanAnotherRound()) { // if player says yes to another round
                startGameRound();
            }
        } else {
            printing.gameOver();
        }
    }

    // Function checks if any active players are able to use a double down and gets their input
    public void offerDoubleDown() {
        if (getBank().canDoubleDown(human) && human.isStillPlaying()) {
            playerInput.humanDoubleDown(human);
        }

        if (getBank().canDoubleDown(comp1) && comp1.isStillPlaying()) {
            playerInput.compDoubleDown(comp1, activePlayers[1]);
        }

        if (getBank().canDoubleDown(comp2) && comp2.isStillPlaying()) {
            playerInput.compDoubleDown(comp2, activePlayers[2]);
        }

        if (getBank().canDoubleDown(comp3) && comp3.isStillPlaying()) {
            playerInput.compDoubleDown(comp3, activePlayers[3]);
        }
    }

    // Function finds if players have an ace and can change it, then gets their input
    public void offerAceChange() {

        if (human.isStillPlaying()) { // no players who already have bust or blackjack are offered
            if (cardDeck.checkIfAce(human.getPlayerCards(), activePlayers[0]) && cardDeck.checkAceCanChange(human)) {
                playerInput.humanChangeAce(cardDeck, cardDeck.fetchAnAce(human.getPlayerCards()), human);
            }
        }
        if (comp1.isStillPlaying()) {
            if (cardDeck.checkIfAce(comp1.getPlayerCards(), activePlayers[1]) && cardDeck.checkAceCanChange(comp1)) {
                playerInput.compChangeAce(cardDeck, cardDeck.fetchAnAce(comp1.getPlayerCards()), comp1);
            }
        }
        if (comp2.isStillPlaying()) {
            if (cardDeck.checkIfAce(comp2.getPlayerCards(), activePlayers[2]) && cardDeck.checkAceCanChange(comp2)) {
                playerInput.compChangeAce(cardDeck, cardDeck.fetchAnAce(comp2.getPlayerCards()), comp2);
            }
        }
        if (comp3.isStillPlaying()) {
            if (cardDeck.checkIfAce(comp3.getPlayerCards(), activePlayers[3]) && cardDeck.checkAceCanChange(comp3)) {
                playerInput.compChangeAce(cardDeck, cardDeck.fetchAnAce(comp3.getPlayerCards()), comp3);
            }
        }
    }

    // Function checks if players have run out of credits and de-activates them (set to 0)
    public void removeBankruptPlayers() {

        if (human.getPlayerCredits() <= 0) {
            activePlayers[0] = 0;
        }
        if (comp1.getPlayerCredits() <= 0) {
            activePlayers[1] = 0;
        }
        if (comp2.getPlayerCredits() <= 0) {
            activePlayers[2] = 0;
        }
        if (comp3.getPlayerCredits() <= 0) {
            activePlayers[3] = 0;
        }
    }

    // Function finds if player's active number is 1 or 0 and returns true/false
    public boolean checkIfActive(int playerNum) {
        boolean checkActive = false;

        if (activePlayers[playerNum] == 0) {
            checkActive = false;
        } else if (activePlayers[playerNum] == 1){
            checkActive = true;
        } else {
            printing.activeCheckError();
        }

        return checkActive;
    }

    // Function gives human and computer players their first two cards of the round
    public void dealStartingCards() {

        Player player = new Player("player");

        for (int i = 0; i < 4; i++) { // loop through all players

            if (i == 0) {
                player = human;
            } else if (i == 1) {
                player = comp1;
            } else if (i == 2) {
                player = comp2;
            } else if (i == 3) {
                player = comp3;
            }

            if (checkIfActive(i)) { // only give cards to active players
                player.loadFirstCard(cardDeck.dealCard());
                player.loadSecondCard(cardDeck.dealCard());
            } else {
                printing.outOfGame(player);
            }
        }

        dealer.getDealerCards()[0] = cardDeck.dealCard(); // give 2 cards to the dealer
        dealer.getDealerCards()[1] = cardDeck.dealCard();
        dealer.addToDealerTotal(dealer.getDealerCards()[0].getValue());
        dealer.addToDealerTotal(dealer.getDealerCards()[1].getValue());
    }

    // Function compares dealer's card total with all remaining players
    public void compareCards() {

        int cards = 0;
        Player player = new Player("player");

        printing.finalDealerCards(dealer.getDealerTotal(), dealer.getDealerCards());
        printing.roundSummaryText();

        for (int i = 0; i < 4; i++) { // check all players

            if (i == 0) {
                player = human;
                cards = player.getCardsTotal();
            } else if (i == 1) {
                player = comp1;
                cards = player.getCardsTotal();
            } else if (i == 2) {
                player = comp2;
                cards = player.getCardsTotal();
            } else if (i == 3) {
                player = comp3;
                cards = player.getCardsTotal();
            }

            if (checkIfActive(i)) { // only players who played in this round
                if (!player.hasBlackjack() && !player.isPlayerBust()) { // only players who need their cards checked

                    if (cards > dealer.getDealerTotal()) { // pay win
                        bank.payPlayer(player);
                        printing.gameOutcomeWon(player.getName(), player.getCurrentWager() * 2, cards);
                    } else if (cards == dealer.getDealerTotal()) { // refund wager
                        bank.refundPlayer(player);
                        printing.gameOutcomeRefunded(player.getName(), player.getCurrentWager(), cards);
                    } else if (dealer.getDealerTotal() > 21) { // pay win
                        bank.payPlayer(player);
                        printing.gameOutcomeWon(player.getName(), player.getCurrentWager() * 2, cards);
                    } else {
                        printing.gameOutcomeLost(player.getName(), player.getCurrentWager(), cards);
                    }

                } else if (player.isPlayerBust()) {
                    printing.gameOutcomeBust(player);
                } else if (player.hasBlackjack()) {
                    printing.gameOutcomeBlackjack(player.getName(), bank.calculatePayout(player.getCurrentWager()));
                }
            }
        }
    }

    // Function calls all players' and dealer's reset functions
    public void resetTotals() {
        human.resetEndRound();
        comp1.resetEndRound();
        comp2.resetEndRound();
        comp3.resetEndRound();
        dealer.resetEndRound();
    }

    // Function stops the game for n milliseconds
    public void pauseGameForEffect(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }


    // GETTERS

    public int getGameRound() {
        return gameRound;
    }

    public Bank getBank() {
        return bank;
    }


    // SETTERS

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

}
