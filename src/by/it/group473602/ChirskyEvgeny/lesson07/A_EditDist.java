package by.it.group473602.ChirskyEvgeny.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeMath.min;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/

public class A_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int n = one.length()+1;
        int m = two.length()+1;

        int[][] D = new int[n+1][m+1];

        for (int i = 0; i < n-1; i++) {
            System.out.print(one.charAt(i) + " ");
        }
        System.out.println();

        for (int i = 0; i < m-1; i++) {
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

        return D[n-1][m-1];
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
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}

