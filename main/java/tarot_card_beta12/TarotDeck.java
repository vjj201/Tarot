package tarot_card_beta12;

import java.util.LinkedList;
import java.util.List;

public class TarotDeck {

    //空牌
    private final List<EmptyCard> deck = new LinkedList<>();

    //建構子
    TarotDeck() {
        for (int i = 0; i < 78; i++) { deck.add(new EmptyCard(i));
        }
    }

    //獲取牌組
    public List<EmptyCard> getDeck() {
        return deck;
    }

    //獲取指定牌
    public EmptyCard getCard(int index) {
        return deck.get(index);
    }
}