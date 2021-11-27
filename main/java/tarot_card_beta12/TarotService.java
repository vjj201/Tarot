package tarot_card_beta12;

import java.util.List;
import java.util.Scanner;

public class TarotService {
    private final TarotDeck tarot = new TarotDeck();
    private final TarotDao dao = new TarotDao();
    private final Scanner scanner = new Scanner(System.in);
    private int count = 0; //當前抽牌次數

    //資料庫登入
    public void login() {
        for(;;) {
            System.out.println("請輸入您的資料庫帳號");
            String user = scanner.next();
            System.out.println("請輸入您的資料庫密碼");
            String password = scanner.next();

            if(dao.getConnection(user, password)) {
                try {
                    dao.createMainTable();
                    dao.createOrderTable();
                    dao.insertData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("連線成功，資料加載完成\n");
                return;
            } else {
                System.out.println("登入失敗，麻煩重新輸入\n");
            }
        }
    }

    //登出
    public void logout() {
        dao.closeResource();
    }

    //刪庫跑路
    public void deleteMainData() {
        try {
            dao.dropMainTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //刪除所有歷史紀錄
    public void deleteOrderData() {
        try {
            dao.dropOrderTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //建立歷史資料庫
    public void createOrderTable() {
        try {
            dao.createOrderTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //打亂牌庫
    public void prepareDeck() {
        Dealer.randomShuffle(tarot.getDeck());
        Dealer.randomPosition(tarot.getDeck());
        System.out.println("牌庫準備完成\n");
    }

    //洗牌
    public void shuffleDeck() {
        count++;
        Dealer.dovetailShuffle(tarot.getDeck());
        Dealer.randomPosition(tarot.getDeck());
        System.out.println("第" + count + "次洗牌完成\n");
    }

    //切牌
    public void getCutCard() {
        EmptyCard cutCard = Dealer.cut(tarot.getDeck());
        int n = cutCard.getCardId() + 1;
        TarotCard card = null;
        try {
            card = dao.searchData(n);
            card.setPosition(cutCard.isPosition());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("您切的牌是第" + n + "張\n" + card + "\n");
    }

    //抽牌
    public void getDrewCar() {
        //洗牌次數歸零
        count = 0;
        //判斷抽取張數
        for(;;) {
            System.out.println("請輸入要抽取的張數");
            String str = scanner.next();
            int number;

            try {
                number = Integer.parseInt(str);
                if(number <= 0 || number > 78) {
                    System.out.println("輸入張數超出範圍\n");
                    continue;
                }

            } catch (RuntimeException e) {
                System.out.println("麻煩輸入數字\n");
                continue;
            }

            List<EmptyCard> cards = Dealer.drawCard(tarot.getDeck(), number);
            //抽取記錄存檔
            int orderId = dao.getOrderId() + 1;
            try {
                dao.insertOrderData(cards , orderId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("編號:" + orderId + "的紀錄已為您保存，以下是您抽取的牌\n");
            //展示牌
            for(EmptyCard card : cards) {
                try {
                    TarotCard tarot = dao.searchData(card.getCardId());
                    tarot.setPosition(card.isPosition());
                    System.out.println(tarot);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println();
            break;
        }
    }

    //查看所有記錄
    public void lookAllData() {
        try {
            dao.lookAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查看特定紀錄
    public void lookData() {
        int index;

        for(;;) {
            System.out.println("請輸入要查看的歷史紀錄單號");
            String str = scanner.next();


            try {
                index = Integer.parseInt(str);
            } catch (RuntimeException e) {
                System.out.println("麻煩輸入數字\n");
                continue;
            }

            try {
                dao.lookData(index);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }
    }

    //牌組重制
    public void resetDeck() {
        Dealer.resetDeck(tarot.getDeck());
    }
}
