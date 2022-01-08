
import java.util.Scanner;

// This class creates an instance of the Blackjack game
public class Play {

    public static void main(String[] args) throws InterruptedException {

        Scanner keyboard = new Scanner(System.in); // to read player input
        Printing printing = new Printing(); // prints game info to screen
        boolean inputComplete = false; // used while player chooses play or test
        boolean getMoreInput = false; // used while choosing number of players
        String inputChoice; // stores player input
        int numberOfPlayers = 0;

        while (!inputComplete) {
            printing.gameMenu();
            inputChoice = keyboard.nextLine(); // store player's typed input

            if (inputChoice.equalsIgnoreCase("p")) { // if player enters 'p' run game
                inputComplete = true;
                getMoreInput = true; // check how many players

            } else if (inputChoice.equalsIgnoreCase("t")) { // if 't', run tests
                TestBlackjack testBlackjack = new TestBlackjack();
                testBlackjack.runFirstFiveTests(); // run 5 of the automated tests
                Thread.sleep(2000); // pause after automated tests
                testBlackjack.dealHitStandTests(); // invite tester to play the game & verify the other 3 tests
                inputComplete = true;
            } else {
                printing.errorMenu();
            }
        }

        while (getMoreInput) {
            printing.askNumberOfPlayers();
            inputChoice = keyboard.nextLine(); // store player's typed input

            if (inputChoice.equalsIgnoreCase("0")){
                numberOfPlayers = 0;
                getMoreInput = false;
            } else if (inputChoice.equalsIgnoreCase("1")){
                numberOfPlayers = 1;
                getMoreInput = false;
            } else if (inputChoice.equalsIgnoreCase("2")){
                numberOfPlayers = 2;
                getMoreInput = false;
            } else if (inputChoice.equalsIgnoreCase("3")){
                numberOfPlayers = 3;
                getMoreInput = false;
            } else {
                printing.errorPlayerNumbers();
            }
        }

        RunBlackjack runBlackjack = new RunBlackjack(numberOfPlayers);
    }
}
