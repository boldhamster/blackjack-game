
// This class stores the dealer's cards, their card total, and handles new cards
public class Dealer {

    private Card[] dealerCards;
    private int dealerTotal;

    // Constructor
    public Dealer() {
        dealerTotal = 0;
        dealerCards = new Card[2];
    }

    // Function draws new cards for dealer's hand until dealer stands or is bust
    public void drawDealerCards(CardDeck cardDeck) {
        boolean cardsPicked = false;

        boolean dealerHasAce = cardDeck.checkIfAce(getDealerCards(), 1);
        boolean dealerCanChangeAce = cardDeck.checkAceCanChangeDealer(this);

        if (dealerHasAce && dealerCanChangeAce) { // check if dealer can change ace to improve their score
            cardDeck.changeAceDealer(cardDeck.fetchAnAce(getDealerCards()), this);
        }

        while (!cardsPicked) {

            if (getDealerTotal() < 17) { // dealer always picks up to 16
                newDealerCard(cardDeck.dealCard()); // deal card
            } else {
                cardsPicked = true; // dealer stands or is bust
            }
        }
    }

    // Function adds a new card to the dealer's hand
    public void newDealerCard(Card newCard) {

        // first check if new card will make dealer bust and if an ace change can save them from that
        int newValue = newCard.getValue();
        if (newValue + dealerTotal > 21) {
            for (Card dealerCard : dealerCards) {
                if (dealerCard.getValue() == 11) { // check if player has an ace that's been changed to 11
                    dealerCard.setValue(1);
                    break; // only do it to 1 ace
                }
            }
        }

        // create a new, bigger array for adding new card
        int oldArrayLength = dealerCards.length;
        Card[] newCardArray = new Card[oldArrayLength + 1]; // +1 as new array should be 1 card bigger

        for (int i = 0; i < oldArrayLength; i++) { // load old cards into new array
            newCardArray[i] = dealerCards[i];
        }

        newCardArray[newCardArray.length-1] = newCard; // store newest card in last position of new card array
        dealerTotal += newCard.getValue(); // update dealer's card total
        this.dealerCards = newCardArray; // store new array
    }

    // Function resets dealer's cards and total
    public void resetEndRound() {
        this.dealerCards = new Card[2];
        this.dealerTotal = 0;
    }


    // GETTERS

    public Card[] getDealerCards() {
        return dealerCards;
    }

    public int getDealerTotal() {
        return dealerTotal;
    }


    // SETTERS

    public void setDealerTotal(int dealerTotal) {
        this.dealerTotal = dealerTotal;
    }

    public void addToDealerTotal(int dealerTotal) {
        this.dealerTotal += dealerTotal;
    }

}
