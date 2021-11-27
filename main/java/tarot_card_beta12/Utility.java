package tarot_card_beta12;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//工具類
public class Utility {
    private final static Scanner scan = new Scanner(System.in);

    //字數限制輸入
    private static String readKeyBoard(int limit) {
        String line;
        while (true) {
            line = scan.next();
            if (line.length() > 1 || line.length() > limit) {
                System.out.print("輸入長度(不可大於" + limit + ")，請重新輸入");
                continue;
            }
            break;
        }
        return line;
    }

    //讀取單個字元
    public static char readChar() {
        String str = readKeyBoard(1);
        return str.charAt(0);
    }

    //讀取確認Y or N
    public static char readConfirm() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.println("選項錯誤，請重新輸入");
            }
        }
        return c;
    }

    //讀取指定範圍的數字
    public static int readInt(String regex) {
        int n;
        String str = "";
        while (scan.hasNext()) {
            str = scan.next();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                break;
            } else {
                System.out.println("選項錯誤，請重新輸入");
            }
        }
        n = Integer.parseInt(str);
        return n;
    }

    //讀取任意鍵
    public static boolean readAny() {
        System.out.println();
        scan.nextLine();
        return scan.hasNextLine();
    }
}