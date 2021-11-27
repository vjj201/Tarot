package tarot_card_beta12;

public class EmptyCard {

    private int cardId; // 卡牌編號
    private boolean position; //正位逆位

    //建構子
    public EmptyCard(int cardId) {
            this.cardId = cardId;
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

    //字串
    @Override
    public String toString() {
        return cardId + " " + position;
    }
}
