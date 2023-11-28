package net.yakodan;

import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Класс, который находит в заданных файлах минимальную цену для каждого товара среди всех продавцов
 * @author Кодинцев Даниил
 */
public class Min_Price {

    /**
     * Единственный метод класса. Находит минимальную цену для каждого товара и выводит всю информацию в указанный файл
     * @param products объект класса {@link Products}. Хранит информацию о товарах
     * @param sellers объект класса {@link Sellers}. Хранит информацию о продавцах
     * @param availability объект класса {@link Availability}. Хранит информацию о товарах у конкретных продацов
     * @param destination путь, по которому метод записывает всю требуемую информацию
     */
    public static void analyze(Products products, Sellers sellers, Availability availability, String destination){
        HashMap<String, HashMap> min_price = new HashMap<>();

        // Ищем информацию о каждом товаре по их id, лежащих в keySet()
        // Информацию о продукте закидываем в отдельный мап,
        // после чего закидываем уже его в общий мап со всеми продуктами
        for (String productID : products.getProducts().keySet()) {
            LinkedHashMap<String, Object> product = new LinkedHashMap<>(); // Linked для красивого порядка в json файле :)
            BigDecimal min = new BigDecimal(Integer.MAX_VALUE);

            for (HashMap<String, Object> availabilityMap : availability.getAvailability()) {

                // Проверяем id товара и смотрим, ниже ли цена той, которую мы нашли прежде
                // Если да, то обновляем всю информацию о товаре
                if (availabilityMap.get("product_id").equals(productID)) {
                    BigDecimal price = (BigDecimal) availabilityMap.get("price");

                    if (min.compareTo(price) > 0) {
                        min = price;

                        product.put("product_name", products.getProducts().get(productID));

                        product.put("min_price", min);

                        String sellerID = (String) availabilityMap.get("seller_id");
                        product.put("seller_id", sellerID);

                        product.put("seller_name", sellers.getSellers().get(sellerID).get(0));
                        product.put("seller_surname", sellers.getSellers().get(sellerID).get(1));
                    }
                }
            }

            min_price.put(productID, product);
        }

        // Записываем наш мап в json файл по заданному пути
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destination))) {
            Jsoner.serialize(min_price, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
