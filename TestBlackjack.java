
// This class contains test cases as laid out in the project specification
public class TestBlackjack {

    public void runFirstFiveTests() {
        System.out.println(" ");
        System.out.println("The first five tests will be printed to the screen ...");
        System.out.println(" ");
        validHandTest();
        bustHandTest();
        kingAceTest();
        kingQueenAceTest();
        nineAceAceTest();
        System.out.println(" ");
        System.out.println("---------------------------------------------");
        System.out.println("-------- AUTOMATED TESTING COMPLETE ---------");
    }

    public void dealHitStandTests() throws InterruptedException {
        // Given I play a game of blackjack
        // When I am dealt my opening hand
        // Then I have two cards

        // Given I have a valid hand of cards
        // When I choose to ‘hit’
        // Then I receive another card
        // And my score is updated

        // Given I have a valid hand of cards
        // When I choose to ‘stand’
        // Then I receive no further cards
        // And my score is evaluated

        System.out.println(" ");
        System.out.println("To verify the Deal, Hit and Stand functions work ...");
        System.out.println("Please play a game of Blackjack ...");
        RunBlackjack runBlackjack = new RunBlackjack(3); // Run the game to display deal/hit/stand functionality
    }

    public void validHandTest() {
        // Given my score is updated or evaluated
        // When it is 21 or less
        // Then I have a valid hand

        Player testPlayer = new Player("Test"); // create game objects required
        Card fiveCard = new Card(1,5,5); // Five
        Card jackCard = new Card(4,11,10); // Jack
        Card threeCard = new Card(2,3,3); // Three
        System.out.println("----------------------------- Valid Hand Test");
        testPlayer.loadFirstCard(fiveCard); // store cards in player object
        testPlayer.loadSecondCard(jackCard);
        testPlayer.hitPlayerCard(threeCard); // store 3rd card
        System.out.println("Player drew " + fiveCard.displayCard());
        System.out.println("Player drew " + jackCard.displayCard());
        System.out.println("Player drew " + threeCard.displayCard());

        boolean checkForBust = testPlayer.checkForBust(); // check if player has gone bust
        System.out.println(testPlayer.getName() + " player's card total is : " + testPlayer.getCardsTotal());
        System.out.println(testPlayer.getName() + " player's bust value is : " + checkForBust);
        System.out.println(" ");
        System.out.println(" ");

    }

    public void bustHandTest() {
        // Given my score is updated
        // When it is 22 or more
        // Then I am ‘bust’ and do not have a valid hand

        Player testPlayer = new Player("Test"); // create game objects required
        Card tenCard = new Card(3,10,10); // Ten
        Card queenCard = new Card(4,12,10); // Queen
        Card jackCard = new Card(2,11,10); // Jack
        System.out.println("----------------------------- Bust Hand Test");
        testPlayer.loadFirstCard(tenCard); // store cards in player object
        testPlayer.loadSecondCard(queenCard);
        testPlayer.hitPlayerCard(jackCard); // store 3rd card
        System.out.println("Player drew " + tenCard.displayCard());
        System.out.println("Player drew " + queenCard.displayCard());
        System.out.println("Player drew " + jackCard.displayCard());

        boolean checkForBust = testPlayer.checkForBust(); // check if player has gone bust
        System.out.println(testPlayer.getName() + " player's card total is : " + testPlayer.getCardsTotal());
        System.out.println(testPlayer.getName() + " player's bust value is : " + checkForBust);
        System.out.println(" ");
        System.out.println(" ");
    }

    public void kingAceTest() {
        // Given I have a king and an ace
        // When my score is evaluated
        // Then my score is 21

        Player testPlayer = new Player("Test"); // create game objects required
        CardDeck cardDeck = new CardDeck();
        PlayerInput playerInput = new PlayerInput();
        Printing printing = new Printing();
        System.out.println("----------------------------- King & Ace Test");
        Card kingCard = new Card(1,13,10); // King
        Card aceCard = new Card(2,1,1); // Ace
        testPlayer.loadFirstCard(kingCard); // store cards in player object
        testPlayer.loadSecondCard(aceCard);
        printing.showPlayerCards(testPlayer, 1); // pass in player and active status (1/0)

        System.out.println(" "); // spacing
        boolean aceCanChange = cardDeck.checkAceCanChange(testPlayer);
        if (aceCanChange) { // if player has an ace and can change it to 11 without going bust
            playerInput.compChangeAce(cardDeck, aceCard, testPlayer);
        }

        System.out.println("");
        printing.showPlayerCards(testPlayer, 1);
        System.out.println("");
        System.out.println(testPlayer.getName() + " player has a total of " + testPlayer.getCardsTotal());
        System.out.println(" ");
        System.out.println(" ");
    }

    public void kingQueenAceTest() {
        // Given I have a king, a queen, and an ace
        // When my score is evaluated
        // Then my score is 21

        Player testPlayer = new Player("Test"); // create game objects required
        Printing printing = new Printing();

        Card kingCard = new Card(4,13,10); // King
        Card queenCard = new Card(2,12,10); // Queen
        System.out.println("----------------------------- King, Queen & Ace Test");
        testPlayer.loadFirstCard(kingCard); // store cards in player object
        testPlayer.loadSecondCard(queenCard);
        Card aceCard = new Card(3,1,1); // Ace
        testPlayer.hitPlayerCard(aceCard); // store 3rd card

        System.out.println("");
        printing.showPlayerCards(testPlayer, 1); // pass in player and active status (1/0)
        System.out.println("");
        System.out.println(testPlayer.getName() + " player has a total of " + testPlayer.getCardsTotal());
        System.out.println(" ");
        System.out.println(" ");
    }

    public void nineAceAceTest() {
        // Given that I have a nine, an ace, and another ace
        // When my score is evaluated
        // Then my score is 21

        Player testPlayer = new Player("Test Player"); // create game objects required
        CardDeck cardDeck = new CardDeck();
        PlayerInput playerInput = new PlayerInput();
        Printing printing = new Printing();
        System.out.println("----------------------------- Nine, Ace & Ace Test");
        Card nineCard = new Card(1,9,9); // Nine
        Card aceCard1 = new Card(2,1,1); // Ace 1
        testPlayer.loadFirstCard(nineCard); // store cards in player object
        testPlayer.loadSecondCard(aceCard1);
        printing.showPlayerCards(testPlayer, 1); // pass in player and active status (1/0)

        System.out.println(" "); // spacing
        boolean aceCanChange = cardDeck.checkAceCanChange(testPlayer);
        if (aceCanChange) { // if player has an ace and can change it to 11 without going bust
            playerInput.compChangeAce(cardDeck, aceCard1, testPlayer);
        }

        Card aceCard2 = new Card(3,1,1); // Ace 2
        testPlayer.hitPlayerCard(aceCard2); // store 3rd card in player object
        printing.humanHitCard(testPlayer.getCardsTotal(), aceCard2); // show new card

        printing.showPlayerCards(testPlayer, 1); // pass in player and active status (1/0)
    }

}
