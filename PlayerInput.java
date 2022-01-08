
import java.util.Random;
import java.util.Scanner;

// This class handles all human and computer input in the game: wagers, hit/stand, change ace, double down, play again
public class PlayerInput {

    private Scanner keyboard = new Scanner(System.in); // Scanner deals with human player input
    private Random rdm = new Random(); // Random used for choosing computer wager & choosing double down
    private Printing printing = new Printing();

    // Function handles human hit/stand input
    public void humanHitStand(CardDeck cardDeck, Player human) {

        boolean endOfInput = false; // loops until player enters correct input
        Card tempCard; // stores new card for displaying to player

        if (!human.hasBlackjack()) { // if player has not already won with blackjack, get hit/stand input

            if (human.playedDoubleDown()) { // before entering input loop, check if player doubled down
                tempCard = cardDeck.dealCard();
                human.hitPlayerCard(tempCard); // if yes, give them 1 new card and skip input loop
                printing.humanDoubleDown(human.getCardsTotal(), tempCard);
                endOfInput = true; // skip loop
            }

            while (!endOfInput) {
                printing.chooseHitOrStand();
                String inputChoice = keyboard.nextLine(); // store player's typed input

                if (inputChoice.equalsIgnoreCase("h")) { // if player enters 'h', hit
                    tempCard = cardDeck.dealCard();
                    human.hitPlayerCard(tempCard); // deal card
                    printing.humanHitCard(human.getCardsTotal(), tempCard);

                    if (human.checkForBust()) { // exit loop if player is now bust
                        endOfInput = true;
                    }

                } else if (inputChoice.equalsIgnoreCase("s")) { /// if player enters 's', stand
                    endOfInput = true;
                    human.setPlayerStanding(true); // update player's game state for 'standing'
                    printing.humanStanding(human.getCardsTotal());

                } else { // 'else' used to handle input errors without throwing an exception
                    printing.hitStandInputError();
                }
            }
        }
    }

    // Function handles computer hit/stand decisions
    public void compHitStand(int activePlayer, CardDeck cardDeck, Player comp) {

        boolean awaitingInput = true;
        Card tempCard;

        if (!comp.hasBlackjack()) {

            if (comp.playedDoubleDown()) {
                tempCard = cardDeck.dealCard();
                comp.hitPlayerCard(tempCard);
                printing.compHitCard(comp.getName(), tempCard);
                awaitingInput = false;
            }

            while (awaitingInput) {
                if (activePlayer == 1) { /// check if still active (0 is inactive)
                    int currentTotal = comp.getCardsTotal();

                    if (currentTotal <= 16) { // comps follow same rules as dealer, all draw up to 16
                        tempCard = cardDeck.dealCard();
                        comp.hitPlayerCard(tempCard);
                        printing.compHitCard(comp.getName(), tempCard);

                        if (comp.checkForBust()) {
                            awaitingInput = false;
                        }

                    } else {
                        comp.setPlayerStanding(true);
                        awaitingInput = false;
                    }
                } else {
                    awaitingInput = false; // player is not active, skip input
                }
            }
        }
    }

    // Function handles player input for changing an ace's value
    public void humanChangeAce(CardDeck cardDeck, Card card, Player human) {
        boolean endOfInput = false;

        while (!endOfInput) {
            printing.aceChangeChoice();
            String inputChoice = keyboard.nextLine();

            if (inputChoice.equalsIgnoreCase("y")) {
                cardDeck.changeAce(card, human);

                endOfInput = true;

            } else if (inputChoice.equalsIgnoreCase("n")) {
                endOfInput = true;

            } else {
                printing.aceChoiceError();
            }
        }
    }

    // Function uses cardDeck's changeAce function
    public void compChangeAce(CardDeck cardDeck, Card card, Player comp) {
        cardDeck.changeAce(card, comp);
    }

    // Function checks if players are still active and displays their chosen wagers
    public void allPlayerWagers(int[] activePlayers, Player human, Player comp1, Player comp2, Player comp3) {
        // check if active first
        if (activePlayers[0] == 1) {
            humanWager(human);
        }
        if (activePlayers[1] == 1) {
            comp1.setCurrentWager(compWagers(comp1, comp1.getPlayerCredits())); // put in rdm wager 250-1000
        }
        if (activePlayers[2] == 1) {
            comp2.setCurrentWager(compWagers(comp2, comp2.getPlayerCredits()));
        }
        if (activePlayers[3] == 1) {
            comp3.setCurrentWager(compWagers(comp3, comp3.getPlayerCredits()));
        }
    }

    // Function handles human wager input
    public void humanWager(Player human) {
        boolean endOfInput = false;
        int wagerAmount = 0;
        int creditsAmount = human.getPlayerCredits();

        while (!endOfInput) {
            printing.askForWager();
            String inputWager = keyboard.nextLine(); // get player's typed input

            if (inputWager.equalsIgnoreCase("250")) {
                wagerAmount = 250;
                endOfInput = true;
            } else if (inputWager.equalsIgnoreCase("500")) {
                wagerAmount = 500;
                endOfInput = true;
            } else if (inputWager.equalsIgnoreCase("750")) {
                wagerAmount = 750;
                endOfInput = true;
            } else if (inputWager.equalsIgnoreCase("1000")) {
                wagerAmount = 1000;
                endOfInput = true;
            } else {
                printing.errorWager();

            }
        }

        if (wagerAmount > creditsAmount) { // if player tries to bet more than they have
            wagerAmount = creditsAmount; // set wager to player's credits total
        }

        human.setCurrentWager(wagerAmount);
        human.removePlayerCredits(wagerAmount);
        printing.playerWager(human);
    }

    // Function chooses a random wager for the computer
    public int compWagers(Player player, int playerCredits) {

        boolean wagerFound = false;
        int rdmWager = 0;
        int wager = 0;

        while (!wagerFound) {
            rdmWager = rdm.nextInt(4); // choose a random wager amount
            if (rdmWager == 0) {
                wager = 250;
            } else if (rdmWager == 1) {
                wager = 500;
            } else if (rdmWager == 2) {
                wager = 750;
            } else if (rdmWager == 3) {
                wager = 1000;
            }

            if (wager <= playerCredits) { // don't allow wagers larger than player's credits
                wagerFound = true;
            }
        }
        player.setCurrentWager(wager);
        player.removePlayerCredits(wager);
        printing.compWagerAmount(player.getName(), wager);

        return wager;
    }

    // Function gets double down choice from human player
    public void humanDoubleDown(Player player) {

        boolean endOfInput = false;
        while (!endOfInput) {
            printing.offerDoubleDown();
            String inputDoubleDown = keyboard.nextLine(); // store typed input

            if (inputDoubleDown.equalsIgnoreCase("y")) {
                player.removePlayerCredits(player.getCurrentWager()); // take off another wager from bank credits
                printing.removeCredits(player.getCurrentWager());
                player.setCurrentWager(player.getCurrentWager() *2); // double the player's current wager total
                printing.humanCurrentWager(player.getCurrentWager());
                player.setDoubleDown(true); // update player's game state for double down
                endOfInput = true;
            } else if (inputDoubleDown.equalsIgnoreCase("n")) {
                player.setDoubleDown(false);
                endOfInput = true;
            } else {
                printing.yesOrNoInputError();
            }
        }
    }

    // Function chooses whether computer player doubles down
    public void compDoubleDown(Player player, int activePlayer) {

        int cardTotal = player.getCardsTotal();
        int randomlyRisky = rdm.nextInt(10); // set random total for player's riskiness on this round

        if (activePlayer == 1) { // if player is still in the game (has credits)
            if (cardTotal > 9 && cardTotal < 13) { // consider double down if total is in the mid-range of 1-21

                if (randomlyRisky > 4) { // 50% chance
                    player.removePlayerCredits(player.getCurrentWager()); // take off one more wager from credits
                    player.setCurrentWager(player.getCurrentWager() *2); // double the wager total
                    printing.compDoubleDown(player.getName());
                    player.setDoubleDown(true); // update player state for hit/stand input
                }
            } else {
                player.setDoubleDown(false);
            }
        }
    }

    // Function asks human player if they want to play again
    public boolean humanAnotherRound() {
        boolean playerInputChosen = false;
        boolean playAnotherRound = false;

        while (!playerInputChosen) {

            printing.offerAnotherRound();
            String playerInput = keyboard.nextLine();

            if (playerInput.equalsIgnoreCase("y")) {
                playerInputChosen = true;
                playAnotherRound = true;

            } else if (playerInput.equalsIgnoreCase("n")) {
                printing.goodbye();
                break;
            } else {
                printing.yesOrNoInputError();
            }
        }

        return playAnotherRound;
    }



}
