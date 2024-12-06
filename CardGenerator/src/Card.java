import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Card class to represent a single card
class Card {
    private int number;  // Card number from 1 to 52

    public Card(int number) {
        this.number = number;
    }

    public String getImagePath() {
        return "res/cards/" + number + ".png";  // Image file path based on the card number
    }
}

// Deck class to represent a standard deck of cards
class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);  // Shuffle the deck initially
    }

    public Card drawCard() {
        Random random = new Random();
        return cards.remove(random.nextInt(cards.size()));
    }
}