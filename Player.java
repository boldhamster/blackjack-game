
// This class contains information about each Player, as well as functions for handling cards and going bust
public class Player {

    private String name = "";
    private int playerCredits = 2000; // credits in bank
    private int currentWager = 0; // current wager (250/500/750/1000)
    private Card[] playerCards = new Card[2];; // current hand
    private int cardsTotal = 0; // current total
    private boolean playerStanding = false; // gameplay states
    private boolean playerIsBust = false;
    private boolean doubleDown = false;
    private boolean blackjack = false;

    // Constructor
    public Player(String name) {
        this.name = name;
    }

    // Function adds first card of the round to player's hand
    public void loadFirstCard(Card newCard) {
        playerCards[0] = newCard;
        this.cardsTotal += newCard.getValue();
    }

    // Function adds second card of the round to player's hand
    public void loadSecondCard(Card newCard) {
        playerCards[1] = newCard;
        this.cardsTotal += newCard.getValue();
    }

    // Function handles adding card after a player chooses 'hit'
    public void hitPlayerCard(Card newCard) {

        int newValue = newCard.getValue();
        if (newValue + cardsTotal > 21) { // first check if new card will make player bust
            for (Card playerCard : playerCards) { // search player cards
                if (playerCard.getValue() == 11) { // look for an ace that's been changed to 11
                    playerCard.setValue(1); // if found, change its value to 1 to avoid bust
                    break; // only do it to 1 ace
                }
            }
        }

        if (newValue == 1) { // if new card is an ace
            if ((newValue + 10 + cardsTotal) < 22) { // now check if new card could be changed to 11
                newCard.setValue(11);
            }
        }

        // create a new, bigger array for adding new card
        int oldArrayLength = playerCards.length;
        Card[] newCardArray = new Card[oldArrayLength + 1]; // +1 as new array should be 1 card bigger

        for (int i = 0; i < oldArrayLength; i++) { // load old cards into new array
            newCardArray[i] = playerCards[i];
        }

        newCardArray[newCardArray.length-1] = newCard; // store newest card in last position of new card array
        cardsTotal += newCard.getValue(); // update player's card total
        this.playerCards = newCardArray; // store new array
    }

    // Function checks if card total is more/less than 21
    public boolean checkForBust() {
        boolean bust = false;
        if (getCardsTotal() > 21) {
            bust = true;
        }
        return bust;
    }

    // Function updates player's game state for bust to true
    public void makeBust() {
        if (checkForBust()) {
            setPlayerIsBust(true);
        }
    }

    // Function resets all player variables (except credits) for next round
    public void resetEndRound() {
        this.playerCards = new Card[2];
        this.cardsTotal = 0;
        this.currentWager = 0;
        this.playerStanding = false;
        this.playerIsBust = false;
        this.doubleDown = false;
        this.blackjack = false;
    }


    // GETTERS

    public String getName() {
        return name;
    }

    public int getCurrentWager() {
        return currentWager;
    }

    public int getPlayerCredits() {
        return playerCredits;
    }

    public int getCardsTotal() {
        return cardsTotal;
    }

    public Card[] getPlayerCards() {
        return playerCards;
    }

    public boolean isPlayerStanding() {
        return playerStanding;
    }

    public boolean isPlayerBust() {
        return playerIsBust;
    }

    public boolean isStillPlaying() {
        if (isPlayerStanding() || isPlayerBust()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean playedDoubleDown() {
        return doubleDown;
    }

    public boolean hasBlackjack() {
        return blackjack;
    }


    // SETTERS

    public void setPlayerStanding(boolean playerStanding) {
        this.playerStanding = playerStanding;
    }

    public void setPlayerIsBust(boolean playerIsBust) {
        this.playerIsBust = playerIsBust;
    }

    public void setCurrentWager(int currentWager) {
        this.currentWager = currentWager;
    }

    public void setCardsTotal(int cardsTotal) {
        this.cardsTotal = cardsTotal;
    }

    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public void setBlackjack(boolean blackjack) {
        this.blackjack = blackjack;
    }

    public void addPlayerCredits(int addCredits) {
        this.playerCredits += addCredits;
    }

    public void removePlayerCredits(int removeCredits) {
        this.playerCredits -= removeCredits;
    }

}
