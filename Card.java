
// This class holds each card's information and functions for displaying them
public class Card {

    // Card values stored as ints for simpler comparison checks
    private int type; // (1-4) Spades, Clubs, Hearts, Diamonds
    private int name; // (1-13) One - Ten, Jack, Queen, King
    private int value; // 1-11 (aces start at 1, but can be changed to 11)

    // Constructor
    public Card(int type, int name, int value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    // returns card info for printing
    public String displayCard() {
        return "The " + nameToString(name) + " of " + typeToString(type) + " [Value: " + value + "]";
    }

    // convert int card type to a String for printing
    public String typeToString(int type) {

        String stringType = "";

        if (type == 1) {
            stringType = "Spades";
        } else if (type == 2) {
            stringType = "Clubs";
        } else if (type == 3) {
            stringType = "Hearts";
        } else if (type == 4) {
            stringType = "Diamonds";
        } else {
            stringType = "ERROR";
        }

        return stringType;
    }

    // convert int card name to a String for printing
    public String nameToString(int name) {

        String stringName = "";

        if (name == 1) {
            stringName = "Ace";
        } else if (name == 2) {
            stringName = "Two";
        } else if (name == 3) {
            stringName = "Three";
        } else if (name == 4) {
            stringName = "Four";
        }  else if (name == 5) {
            stringName = "Five";
        }  else if (name == 6) {
            stringName = "Six";
        }  else if (name == 7) {
            stringName = "Seven";
        }  else if (name == 8) {
            stringName = "Eight";
        }  else if (name == 9) {
            stringName = "Nine";
        }  else if (name == 10) {
            stringName = "Ten";
        }  else if (name == 11) {
            stringName = "Jack";
        }  else if (name == 12) {
            stringName = "Queen";
        }  else if (name == 13) {
            stringName = "King";
        } else {
            stringName = "ERROR";
        }

        return stringName;
    }

    // GETTERS ===================

    public int getValue() {
        return this.value;
    }

    // SETTERS ====================

    public void setValue(int value) {
        this.value = value;
    }

}
