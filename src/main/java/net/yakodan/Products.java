package net.yakodan;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashMap;
/**
 * Вспомогательный класс, хранящий в себе информацию о товарах
 */
public class Products {

    private final HashMap<String, String> products;

    /**
     * Конструктор класса. Считывает файл и закидывает данные из него в соответствующий объект
     * @param path путь к считываемому файлу
     */
    public Products (Path path){
        try(Reader reader = Files.newBufferedReader(path)){
            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

            HashMap<String,String> products = new HashMap<>();

            products.putAll((HashMap) parser);

            this.products = products;

        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, String> getProducts() {
        return products;
    }
}
