
import java.util.Random;

// This class creates and maintains the game's card deck
public class CardDeck {

    private Card[] deckOfCards = new Card[52];
    private int[] dealtCards = new int[52]; // stores the deckOfCards index positions of cards that have already been dealt
    private int dealtIndex = 0; // the dealtCards array's own index position
    private Random rdm = new Random(); // used when shuffling the deck
    private Printing printing = new Printing(); //  class for printing game info to the console

    // Function creates and stores cards in the card deck
    public void loadTheDeck() {

        // Values for each card
        int cardType = 1; // range 1-4 (e.g. 1 = "Spades")
        int cardName = 1; // range 1-13 (e.g. 1 = "One")
        int cardValue = 1; // range 1-10 (e.g. 1 = "1")

        // Using an 'if block', catch situations where name/type/value need to be incremented or reset
        // 'Name' increments after every iteration of the 'for' loop but resets every 13 names (#1 Ace > #13 King)
        // 'Type' changes 4 times in total for Spades (#1), Clubs (#2), Hearts (#3) and Diamonds (#4)
        // 'Value' is the same as Name unless Name is a Jack (#11), Queen (#12) or King (#13), when Value = 10

        for (int indexPos = 0; indexPos < 52; indexPos++) { // 52 cards in array

            deckOfCards[indexPos] = new Card(cardType, cardName, cardValue); // load values into each card

            if (cardName == 13) { // if card is a king
                cardType++; // only when resetting name, increment to the next card type
                cardName = 1; // reset name to starting position for next set of cards
                cardValue = 1; // reset value (for new set of cards)

            } else if (cardName >= 10 && cardName <= 12) { // if card is a ten, a jack or a queen
                cardName++; // increment card name for next card
                cardValue = 10; // the value is 10 for these cards

            } else if (cardName < 10) {
                cardName++; // increment card name for next card
                cardValue = cardName; // card value is the same as name (e.g. name 2 == value 2)

            } else {
                printing.cardDeckError();
            }
        }
    }

    // Function shuffles the deck by swapping cards throughout the array
    public void shuffleTheDeck() {
        for (int i = 0; i < deckOfCards.length; i++) {
            int randomIndexToSwap = rdm.nextInt(deckOfCards.length); // choose random position in deck
            Card temp = deckOfCards[randomIndexToSwap]; // store copy of the chosen Card
            deckOfCards[randomIndexToSwap] = deckOfCards[i]; // over-write the random card's position with card in 'i'
            deckOfCards[i] = temp; // save random card in 'i' position
        }
        printing.shuffleDeck();
    }

    // Function finds and returns a new (un-dealt) card from the deck
    public Card dealCard() {
        boolean notChosenYet = true;
        int dealerPick = 0;

        while (notChosenYet) { // pick random position in card deck and check if already dealt
            notChosenYet = checkIfDealtCard(dealerPick = rdm.nextInt(deckOfCards.length));
        }

        storeDealtCard(dealerPick); // store latest card's location in pickedCards[] array

        return deckOfCards[dealerPick];
    }

    // Function compares a chosen card's position to those stored in dealtCards and returns true/false
    public boolean checkIfDealtCard(int cardPosition) {
        boolean cardIsAlreadyPicked = false;

        for (int i = 0; i < dealtCards.length; i++) {
            if (dealtCards[i] == cardPosition) {
                cardIsAlreadyPicked = true;
                break;
            }
        }
        return cardIsAlreadyPicked;
    }

    // Function stores the position of the card just dealt
    public void storeDealtCard(int pickedCard) {
        dealtCards[dealtIndex] = pickedCard;
        dealtIndex++; // increment to next empty position
    }

    // Function clears the array of card positions by overwriting old array with new one
    public void wipeDealtCards() {
        int[] newPickedArray = new int[52];
        this.dealtCards = newPickedArray; // wipe array
        dealtIndex = 0; // reset array's index position
    }

    // Function checks player's cards for a card with the value 1 or 11
    public boolean checkIfAce(Card[] playerCards, int active) {
        boolean aceFound = false;

        if (active == 1) { // only checks active players
            for (int i = 0; i < playerCards.length; i++) {
                if (playerCards[i].getValue() == 1 || playerCards[i].getValue() == 11) {
                    aceFound = true;
                }
            }
        }

        return aceFound;
    }

    // Function returns true if changing an ace from 1 to 11 won't make the player bust
    public boolean checkAceCanChange(Player player) {
      boolean aceCanChange = false;
        int cardTotal = player.getCardsTotal();
        int newAceTotal = cardTotal + 10;

        if (newAceTotal < 22) {
            aceCanChange = true;
        } else {
            aceCanChange = false;
        }

        return aceCanChange;
    }

    // Function returns true if dealer can change an ace without going bust
    public boolean checkAceCanChangeDealer(Dealer dealer) {
      boolean aceCanChange = false;
        int cardTotal = dealer.getDealerTotal();
        int newAceTotal = cardTotal + 10;

        if (newAceTotal < 22) {
            aceCanChange = true;
        } else {
            aceCanChange = false;
        }
        
        return aceCanChange;
    }

    // Function returns an ace from a given deck
    public Card fetchAnAce(Card[] playerCards) {
        Card aceCard = new Card(0,0,0);

        for (int i = 0; i < playerCards.length; i++) {
            if (playerCards[i].getValue() == 1 || playerCards[i].getValue() == 11) {
                aceCard = playerCards[i];
            }
        }
        return aceCard;
    }

    // Function checks the ace's current value and flips it
    public void changeAce(Card ace, Player player) {
        int aceValue = ace.getValue();

        if (aceValue == 1) {
            ace.setValue(11);
            player.setCardsTotal(player.getCardsTotal()+10); // update cards total
        } else {
            ace.setValue(1);
            player.setCardsTotal(player.getCardsTotal()-10);
        }
        printing.aceChanged(player.getName(), player.getCardsTotal());
    }

    // Function checks value of a dealer's ace and flips it
    public void changeAceDealer(Card ace, Dealer dealer) {
        int aceValue = ace.getValue();

        if (aceValue == 1) {
            ace.setValue(11);
            dealer.setDealerTotal(dealer.getDealerTotal()+10);
        } else {
            ace.setValue(1);
            dealer.setDealerTotal(dealer.getDealerTotal()-10);
        }
        printing.aceChanged("Dealer", dealer.getDealerTotal());
    }

}
