package net.yakodan;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Вспомогательный класс, хранящий в себе информацию о продажах
 */
public class Sales {

    private final HashMap<String, HashMap> sales;

    /**
     * Конструктор класса. Считывает файл и закидывает данные из него в соответствующий объект
     * @param path путь к считываемому файлу
     */
    public Sales(Path path){
        try(Reader reader = Files.newBufferedReader(path)){
            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

            HashMap<String, HashMap> sales = new HashMap<>();

            parser.forEach((key, value) -> sales.put(key, (HashMap) value));

            this.sales = sales;

        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, HashMap> getSales() {
        return sales;
    }
}
