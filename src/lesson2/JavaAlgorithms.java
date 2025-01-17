package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.util.Arrays;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
//    Трдоемкость: O(N^2)
//    Ресурсоемкость: T(N^2)
    static public String longestCommonSubstring(String first, String second) {
        int[][] matrix = new int[first.length()][second.length()];
        StringBuilder answer = new StringBuilder();
        int max = 0;
        int maxI = 0;
        int diagonal;
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if ((i - 1 >= 0) & (j - 1 >= 0)) {
                        diagonal = matrix[i - 1][j - 1] + 1;
                    } else {
                        diagonal = 1;
                    }

                    if (diagonal > max) {
                        max = diagonal;
                        maxI = i;
                    }

                    matrix[i][j] = diagonal;
                }

            }
        }

        while (max > 0) {
            answer.append(first.charAt(maxI));
            maxI--;
            max--;
        }

        return answer.reverse().toString();
    }

    static public String longestCommonSubstring1(String first, String second) {
        StringBuilder answer = new StringBuilder();
        int max = 0;
        int maxI = 0;
        for (int i = 0; i <= first.length() - 1; i++) {
            for (int j = 0; j <= second.length() - 1; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    int ii = i;
                    int jj = j;
                    int length = 0;
                    while ((ii < first.length()) && (jj < second.length()) && (first.charAt(ii) == second.charAt(jj))) {
                        length++;
                        ii++;
                        jj++;
                    }

                    if (length > max) {
                        max = length;
                        maxI = i;
                    }
                }
            }
        }

        while (max > 0) {
            answer.append(first.charAt(maxI));
            maxI++;
            max--;
        }


        return answer.toString();
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
//    алгоритм Решето Эратосфена
//    Трдоемкость: O(N*log(log(N)))
//    Ресурсоемкость: T(N)
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1)
            return 0;
        int answer = 0;

        int[] arr = new int[limit + 1];
        
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == 0) {
                answer++;
                for (int j = 2 * i; j < arr.length; j += i) {
                    arr[j]--;
                }
            }
        }

        return answer;
    }
}
