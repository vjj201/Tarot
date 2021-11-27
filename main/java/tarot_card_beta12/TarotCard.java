package tarot_card_beta12;

public class TarotCard {

    private int cardId; // 卡牌編號
    private boolean position; //正位逆位
    private String arcana; //阿爾卡納
    private String suit; //花色
    private String cardName; //牌名

    //建構子
    public TarotCard() {}

    public TarotCard(int cardId) {
        this.cardId = cardId;
    }

    public TarotCard(int cardId, String arcana, String suit, String cardName) {
        this.cardId = cardId;
        this.arcana = arcana;
        this.suit = suit;
        this.cardName = cardName;
    }

    //獲取&設置
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public boolean isPosition() {
        return position;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }

    public String getArcana() {
        return arcana;
    }

    public void setArcana(String arcana) {
        this.arcana = arcana;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    //打印
    @Override
    public String toString() {
        return (position ? "正位 " : "逆位 ") + arcana + " " + suit + " " + cardName;
    }
}
