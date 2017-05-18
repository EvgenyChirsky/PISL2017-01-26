package by.it.group473602.ChirskyEvgeny.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
            - за сам массив отрезков - сортировка на месте
            - рекурсионные вызовы должны проводится на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска,
        помните при реализации, что поиск множественный
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    //отрезок
    private class Segment implements Comparable {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            //подумайте, что должен возвращать компаратор отрезков
            Segment entry = (Segment) o;
            if (this.start > entry.start) {
                return -1;
            }
            if (this.start <= entry.start) {
                return 1;
            }
            return 0;
        }
    }

    public void qsort(Segment[] segments, int left, int right) {
        if (left < right) {
            int m = partition(segments, left, right);
            qsort(segments, left, m - 1);
            left = m + 1;
        }
    }

    public int partition(Segment[] segments, int left, int right) {
        Segment x = segments[left];
        int j = left;
        for (int i = left + 1; i < right; i++) {
            if (segments[i].compareTo(x) == -1) {
                j = j + 1;
                Segment swap = segments[j];
                segments[j] = segments[i];
                segments[i] = swap;
            }
        }
        Segment swap = segments[left];
        segments[left] = segments[j];
        segments[j] = swap;
        return j;
    }

    public void binarySearch(Segment[] segments, int[] points, int[] result) {
        for (int i = 0; i < points.length; i++) {
            int left = 0;
            int right = segments.length - 1;

            while (left <= right) {
                int middle = (left + right) / 2;
                if (segments[middle].start <= points[i] && segments[middle].stop >= points[i]) {
                    result[i] = middle + 1;
                    break;
                } else if (segments[middle].start < points[i]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор

        qsort(segments, 0, segments.length - 1);

        binarySearch(segments, points, result);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group473602/ChirskyEvgeny/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

}
