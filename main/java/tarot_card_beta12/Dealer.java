package tarot_card_beta12;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public interface Dealer {

    //亂數洗牌
    static void randomShuffle(List<EmptyCard> deck) {
        Collections.shuffle(deck);
    }

    //隨機正逆位
    static void randomPosition(List<EmptyCard> deck) {
        for (EmptyCard card : deck) {
            card.setPosition((int) (Math.random() * 2) > 0);
        }
    }

    //交錯式洗牌
    static void dovetailShuffle(List<EmptyCard> deck) {
        List<EmptyCard> ShuffledDeck = new LinkedList<>();
        //新牌組偶數奇數添加，達到牌組相互交插目的
        for (int i = 0; i < deck.size() / 2; i++) {
            ShuffledDeck.add(i * 2, deck.get(i));
            ShuffledDeck.add(i * 2 + 1, deck.get(i + deck.size() / 2));
        }

        deck.clear();
        deck.addAll(ShuffledDeck);
    }

    //切牌
    static EmptyCard cut(List<EmptyCard> deck) {
        //產生隨機數為要切的位子
        int random = (int) (Math.random() * deck.size() - 1);
        EmptyCard card = deck.get(random);
        //暫用牌組存放切牌以內的牌
        List<EmptyCard> temp = new LinkedList<>(deck.subList(0, random + 1));
        //將切牌後方的牌放置前面
        for (int i = 0; i < deck.size() - 1 - random; i++) {
            deck.set(i, deck.get(random + 1 + i));
        }
        //切牌以內的牌放置後方
        int j = 0;
        for (int i = deck.size() - 1 - random; i < deck.size(); i++) {
            deck.set(i, temp.get(j));
            j++;
        }

        return card;
    }

    //抽幾張牌
    static List<EmptyCard> drawCard(List<EmptyCard> deck, int number) {
        List<EmptyCard> temp = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            int random = (int) (Math.random() * deck.size() - 1);
            temp.add(deck.get(random));
            deck.remove(random);
        }
        return temp;
    }

    //牌庫初始化
    static void resetDeck(List<EmptyCard> deck) {
        deck.sort(new Comparator<EmptyCard>() {
            @Override
            public int compare(EmptyCard o1, EmptyCard o2) {
                return o1.getCardId() - o2.getCardId();
            }
        });
    }
}
