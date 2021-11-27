package tarot_card_beta12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarotDao {
    private static final String URL = "jdbc:mysql://localhost:3306/TarotBeta?" +
            "useSSL=true&useUnicode=yes&characterEncoding=UTF-8" +
            "&serverTimezone=Asia/Taipei&allowPublicKeyRetrieval=true";
    private Connection con;
    private PreparedStatement ps;

    //資料庫連線
    public boolean getConnection(String user, String password) {
        boolean flag = false;
        try {
            con = DriverManager.getConnection(URL, user, password);
            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    //連線關閉
    public void closeResource() {
        try {
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("連線關閉");
    }

    //創建主資料表單
    public void createMainTable() throws Exception {
        String sql = "create table if not exists waite_tarot ( " +
                "id int primary key auto_increment, " +
                "arcana varchar(5), " +
                "suit varchar(5), " +
                "card_name varchar(4) " +
                ");";
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    //創建抽牌歷史紀錄
    public void createOrderTable() throws Exception {
        String sql = "create table if not exists order_tarot ( " +
                "order_id int, " +
                "id int, " +
                "position boolean " +
                ");";
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    //刪除資料表單
    public void dropMainTable() throws Exception {
        String sql = "drop table if exists waite_tarot;";
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    //刪除抽牌歷史紀錄
    public void dropOrderTable() throws Exception {
        String sql = "drop table if exists order_tarot;";
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    //獲取歷史紀錄最新編號
    public int getOrderId() {
        String sql = "select max(order_id) from order_tarot;";
        int orderId = 0;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderId;
    }


    //讀取文字檔
    private List<String[]> readText(String src) {
        List<String[]> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //新增主要基礎資料
    public void insertData() throws Exception {
        String sql = "insert into waite_tarot(arcana, suit, card_name) values(?, ?, ?);";
        ps = con.prepareStatement(sql);

        List<String[]> list = readText("Tarot.txt");
        for(String[] data : list) {
            ps.setString(1,data[0]);
            ps.setString(2,data[1]);
            ps.setString(3,data[2]);

            ps.executeUpdate();
        }
    }

    //新增歷史紀錄資料
    public void insertOrderData(List<EmptyCard> cards, int orderId) throws Exception {
        String sql = "insert into order_tarot(order_id, id, position) values(?, ?, ?);";
        ps = con.prepareStatement(sql);

        for(EmptyCard card : cards) {
            ps.setInt(1, orderId);
            ps.setInt(2,card.getCardId());
            ps.setBoolean(3,card.isPosition());

            ps.executeUpdate();
        }
    }

    //搜尋卡片資料
    public TarotCard searchData(int index) throws Exception {
        String sql = "select * from waite_tarot where id = ?;";
        TarotCard card = null;

        ps = con.prepareStatement(sql);
        ps.setInt(1, index);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            int cardId = rs.getInt(1);
            String arcana = rs.getString(2);
            String suit = rs.getString(3);
            String cardName = rs.getString(4);
            card = new TarotCard(cardId, arcana, suit, cardName);
        }

        return card;
    }

    //查看所有歷史資料
    public void lookAllData() throws Exception {
        String sql = "select order_id, position, arcana, suit, card_name\n" +
                "from order_tarot\n" +
                "join waite_tarot on waite_tarot.id = order_tarot.id\n" +
                "order by order_id;";

        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int count = 0;
        while(rs.next()) {
            int orderId = rs.getInt(1);
            boolean position = rs.getBoolean(2);
            String arcana = rs.getString(3);
            String suit = rs.getString(4);
            String cardName = rs.getString(5);

            if(count != orderId) {
                count++;
                System.out.println("\n歷史紀錄單號" + orderId);
            }
            System.out.println((position ? "正位" : "逆位") + " " + arcana + " " + suit + " " + cardName);
        }

    }

    //搜尋單筆歷史資料
    public void lookData(int index) throws Exception {
        String sql = "select order_id, position, arcana, suit, card_name\n" +
                "from order_tarot\n" +
                "join waite_tarot on waite_tarot.id = order_tarot.id\n" +
                "having order_id = ?\n" +
                "order by order_id;";

        ps = con.prepareStatement(sql);
        ps.setInt(1, index);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            int orderId = rs.getInt(1);
            boolean position = rs.getBoolean(2);
            String arcana = rs.getString(3);
            String suit = rs.getString(4);
            String cardName = rs.getString(5);
            System.out.println("\n歷史紀錄第" + orderId + "筆資料");
            System.out.println((position ? "正位" : "逆位") + " " + arcana + " " + suit + " " + cardName);
        }  else {
            System.out.println("查無歷史紀錄");
        }

        while(rs.next()) {
            boolean position = rs.getBoolean(2);
            String arcana = rs.getString(3);
            String suit = rs.getString(4);
            String cardName = rs.getString(5);
            System.out.println((position ? "正位" : "逆位") + " " + arcana + " " + suit + " " + cardName);
        }
    }


    //查看所有牌
    public void searchAll() throws Exception {
        String sql = "select * from waite_tarot;";

        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int cardId = rs.getInt(1);
            String arcana = rs.getString(2);
            String suit = rs.getString(3);
            String cardName = rs.getString(4);
            System.out.println(cardId + " " + arcana + " " + suit + " " + cardName);
        }
    }
}