package by.it.group473602.ChirskyEvgeny.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!


        int n = one.length() + 1;
        int m = two.length() + 1;

        int[][] D = new int[n + 1][m + 1];

        for (int i = 0; i < n - 1; i++) {
            System.out.print(one.charAt(i) + " ");
        }
        System.out.println();

        for (int i = 0; i < m - 1; i++) {
            System.out.print(two.charAt(i) + " ");
        }
        System.out.println("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                editDistance(D, one, two, i, j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(D[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        n=n-1;
        m=m-1;
        String result = "";
        while (n > 0 || m > 0) {
            char symbol = '#';
            int curr = D[n][m];
            if (n > 0 && m > 0 && D[n - 1][m - 1] < curr) {
                curr = D[n - 1][m - 1];
                symbol = '~';
            }
            if (n > 0 && D[n - 1][m] < curr) {
                curr = D[n - 1][m];
                symbol = '-';
            }
            if (m > 0 && D[n][m - 1] < curr) {
                symbol = '+';
            }
            switch (symbol){
                case '#': {
                    result=String.format("%s,%s", symbol, result);
                    n--;
                    m--;
                }
                break;
                case '~': {
                    result=String.format("%s%s,%s", symbol,two.charAt(m-1), result);
                    n--;
                    m--;
                }
                break;
                case '-': {
                    result=String.format("%s%s,%s", symbol,one.charAt(n-1), result);
                    n--;
                }
                break;
                case '+': {
                    result=String.format("%s%s,%s", symbol,two.charAt(m-1), result);
                    m--;
                }
                break;
            }
        }


        return result;

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }

    private int editDistance(int[][] D, String one, String two, int i, int j) {

        if (i == 0) {
            D[i][j] = j;
        } else if (j == 0) {
            D[i][j] = i;
        } else {
            int cost = (one.charAt(i - 1) != two.charAt(j - 1)) ? 1 : 0;
            int ins = editDistance(D, one, two, i, j - 1) + 1;
            int del = editDistance(D, one, two, i - 1, j) + 1;
            int sub = editDistance(D, one, two, i - 1, j - 1) + cost;
            D[i][j] = min(ins, del, sub);
        }
        return D[i][j];
    }


    private int min(int ins, int del, int sub) {
        if (ins < del) del = ins;
        if (del < sub) sub = del;
        return sub;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group473602/ChirskyEvgeny/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}
