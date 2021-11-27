package tarot_card_beta12;

public class TarotUI {
    private final TarotService service = new TarotService();

    //主選單
    public void welcomeToMenu() {
        service.login();

        System.out.println("歡迎來到，塔羅抽牌應用beta1.2");

        for (; ; ) {
            System.out.println("--------<介面 選單>--------");
            System.out.println("        (1)開始占卜        ");
            System.out.println("        (2)查看紀錄        ");
            System.out.println("        (3)清除資料        ");
            System.out.println("        (4)退出程式        ");
            System.out.println("       請輸入對應數字       ");

            int menu = Utility.readInt("[1-4]");
            switch (menu) {
                case 1:
                    System.out.println("         即將開始占卜       \n");
                    startDivination();
                    System.out.println("\n");
                    break;
                case 2:
                    System.out.println("       查看先前歷史紀錄      \n");
                    startReadData();
                    System.out.println("\n");
                    break;
                case 3:
                    System.out.println("       選擇要清除的資料      \n");
                    startDeleteData();
                    System.out.println("\n");
                    break;
                case 4:
                    System.out.println("      是否要退出(Y/N)      ");
                    char confirm = Utility.readConfirm();
                    if (confirm == 'Y') {
                        System.out.println("     系統結束，下次再見      \n");
                        service.logout();
                        return;
                    } else {
                        System.out.println("         返回選單           \n");
                    }
            }
        }
    }

    //占卜頁面
    private void startDivination() {
        service.prepareDeck();
        System.out.println("       進入抽牌選單      \n");
        System.out.println("心裡想著要詢問的問題，可能是小小的煩惱、近期的心事、在意的他人");
        System.out.println("經過任意次的洗牌和一次的切牌後，就可以根據問題開始進行抽牌了唷\n");

        for (; ; ) {
            System.out.println("--------<占卜 選單>--------");
            System.out.println("(1)洗牌(2)切牌(3)抽牌(4)返回");
            System.out.println("       請輸入對應數字       ");

            int menu = Utility.readInt("[1-4]");
            switch (menu) {
                case 1:
                    System.out.println("          進行洗牌         \n");
                    service.shuffleDeck();
                    System.out.println("            完成           \n");
                    break;
                case 2:
                    System.out.println("          進行切牌         \n");
                    service.getCutCard();
                    System.out.println("            完成           \n");
                    break;
                case 3:
                    System.out.println("          進行抽牌         \n");
                    service.getDrewCar();
                    System.out.println("    完成，輸入任意鍵繼續     \n");

                    Utility.readAny();
                    System.out.println("本次抽牌紀錄，可以到歷史紀錄中查看");
                    System.out.println("若要重新占卜也可以從主選單再次進入\n");

                case 4:
                    System.out.println("         返回選單           ");
                    service.resetDeck();
                    return;
            }
        }
    }

    //歷史紀錄頁面
    public void startReadData() {
        System.out.println("         進入紀錄選單        \n");

        for (; ; ) {
            System.out.println("--------<紀錄  選單>--------");
            System.out.println(" (1)搜尋一筆(2)全部資料(3)返回 ");
            System.out.println("       請輸入對應數字       ");

            int menu = Utility.readInt("[1-3]");
            switch (menu) {
                case 1:
                    System.out.println("          進行搜尋         \n");
                    service.lookData();
                    System.out.println("\n            完成           \n");
                    break;
                case 2:
                    System.out.println("          列出資料         \n");
                    service.lookAllData();
                    System.out.println("\n            完成           \n");
                    break;
                case 3:
                    System.out.println("         返回選單           ");
                    return;
            }
        }
    }

    //刪除頁面
    public void startDeleteData() {
        System.out.println("        進入清除選單        \n");

        for(;;) {
            System.out.println("--------<刪除  選單>--------");
            System.out.println("(1)歷史資料(2)移除全部(3)返回");
            System.out.println("       請輸入對應數字       ");

            int menu = Utility.readInt("[1-3]");
            switch (menu) {
                case 1:
                    System.out.println("      清空所有紀錄      ");
                    System.out.println("要執行刪除嗎，要確定內(Y/N)\n");

                    char confirmA = Utility.readConfirm();
                    if (confirmA == 'Y') {
                        service.deleteOrderData();
                        service.createOrderTable();
                        System.out.println("\n            完成           \n");
                    } else {
                        System.out.println("\n            返回           \n");
                    }

                    break;
                case 2:
                    System.out.println("     警告將清除所有東東!   ");
                    System.out.println("程式會結束執行，是否繼續(Y/N)\n");

                    char confirmB = Utility.readConfirm();
                    if (confirmB == 'Y') {
                        service.deleteOrderData();
                        service.deleteMainData();
                        service.logout();
                        System.out.println("清除完成，系統關閉，再見");
                        System.exit(0);
                    }

                    System.out.println("            返回           \n");
                    break;
                case 3:
                    System.out.println("         返回選單           ");
                    return;
            }
        }
    }
}
