package net.yakodan;

import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Класс, который находит топ 5 дат по количеству проданных товаров
 * @author Кодинцев Даниил
 */
public class Top5_Dates {

    /**
     * Единственный метод класса. В заданном файле продаж находит топ 5 дат по продажам <br>
     * Выводит в файл по указанному пути даты и соответсвующие им количесвто проданных товаров и список с id продаж
     * @param sales объект класса {@link Sales}. Хранит информацию о продажах
     * @param destination путь, по которому метод записывает всю требуемую информацию
     */
    public static void analyze(Sales sales, String destination){
        HashMap<String, HashMap> dates = new HashMap<>();

        // Проходимся по каждой продаже
        sales.getSales().forEach((saleID, saleMap) -> {
            // Если дата новая, то записываем первичную информацию о ней, а именно кол-во проданных товаров и id продажи
            if(!dates.containsKey(saleMap.get("date"))){
                LinkedHashMap<String, Object> date = new LinkedHashMap<>();

                date.put("sale_amount", saleMap.get("sale_amount"));

                ArrayList<String> saleIDs = new ArrayList<>();
                saleIDs.add(saleID);

                date.put("sale_ids", saleIDs);
                dates.put((String) saleMap.get("date"), date);
            }
            // Если дата уже была, то суммируем количества проданных товаров до этого и в новой продаже
            // Также добавляем id продажи в список
            else{
                HashMap date = dates.get(saleMap.get("date"));

                BigDecimal sale_amount = (BigDecimal) saleMap.get("sale_amount");
                BigDecimal sum = sale_amount.add((BigDecimal) date.get("sale_amount"));
                date.put("sale_amount", sum);

                ArrayList<String> saleIDs = (ArrayList<String>) date.get("sale_ids");
                saleIDs.add(saleID);

                date.put("sale_ids", saleIDs);
                dates.put((String) saleMap.get("date"), date);
            }
        });

        // Создаём свой компаратор по кол-ву продаж и сортируем все даты в новой мапе
        AmountComparator comparator = new AmountComparator(dates);
        TreeMap<String, HashMap> dates_sorted = new TreeMap<>(comparator);

        dates_sorted.putAll(dates);

        // Создаём список сущностей из отсортированной мапы
        List<Map.Entry<String, HashMap>> entryList =
                new ArrayList<>(dates_sorted.entrySet());

        LinkedHashMap<String, HashMap> top5_dates = new LinkedHashMap<>();

        // ВЫбираем первые 5 сущностей из списка и закидываем их в итоговую мапу
        for (Map.Entry<String, HashMap> entry : entryList.subList(0, 5)) {
            top5_dates.put(entry.getKey(), entry.getValue());
        }

        // Записываем результат в json файл по заданному пути
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destination))) {
            Jsoner.serialize(top5_dates, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Вложенный класс. Нужен для сортировки готовых дат по объёму продаж
     */
    static class AmountComparator implements Comparator<String> {

        Map<String, HashMap> base;

        /**
         * Конструктор
         * @param base объект, по которому идёт сравнение
         */
        public AmountComparator(Map<String, HashMap> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            BigDecimal amount = (BigDecimal) base.get(a).get("sale_amount");

            return (-1)*amount.compareTo((BigDecimal) base.get(b).get("sale_amount"));
        }
    }

}
