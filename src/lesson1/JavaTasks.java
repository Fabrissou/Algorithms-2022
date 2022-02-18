package lesson1;

import kotlin.NotImplementedError;
import kotlin.Pair;
import lesson7.knapsack.Fill;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
//    данные хранятся в отсортированном виде внутри TreeMap<названиеУлицы, TreeMap<номерУлицы, TreeSet<имяФамилия>>>
//    в первом цикле данная Map'а заполняется данными, а во втором цикле данные записываются в файл
//    Трдоемкость: O(N*log(N))
//    Ресурсоемкость: T(N)
    static public void sortAddresses(String inputName, String outputName) throws Exception {
        try {
            Map<String, TreeMap<Integer, TreeSet<String>>> data = new TreeMap<>();
            File outputFile = new File(outputName);
            PrintWriter printWriter = new PrintWriter(outputFile, StandardCharsets.UTF_8);

            List<String> lines = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8);
            for(String line: lines){
                String[] name = line.split("\\s-\\s");
                String[] street = name[1].split("\\s");
                Integer k = Integer.parseInt(street[1]);

                if (data.containsKey(street[0])) {
                    if (data.get(street[0]).containsKey(k)) {
                        data.get(street[0]).get(k).add(name[0]);
                    } else {
                        data.get(street[0]).put(k, new TreeSet<>(Collections.singleton(name[0])));
                    }
                } else {
                    data.put(street[0], new TreeMap<>());
                    data.get(street[0]).put(k, new TreeSet<>(Collections.singleton(name[0])));
                }
            }

            data.forEach((streetName, streetNumber) -> {
                streetNumber.forEach((num, names) -> {
                    String str = names.toString();
                    StringBuilder stringBuilder = new StringBuilder(streetName);
                    stringBuilder.append(" ");
                    stringBuilder.append(num);
                    stringBuilder.append(" - ");
                    stringBuilder.append(str, 1, str.length() - 1);
                    printWriter.println(stringBuilder);
                });
            });
            printWriter.close();

        } catch (IOException e) {
            Exception IllegalArgumentException = null;
            throw IllegalArgumentException;
        }
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
//    Трдоемкость: O(N*log(N))
//    Ресурсоемкость: T(N) (создается лист)
    static public void sortTemperatures(String inputName, String outputName) {
        try {
            File file = new File(inputName);
            PrintWriter printWriter = new PrintWriter(outputName, StandardCharsets.UTF_8);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<Double> list = new ArrayList<>();

            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(Double.parseDouble(line));
                line = bufferedReader.readLine();
            }

            Collections.sort(list);

            for(Double d: list){
                printWriter.println(d);
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
