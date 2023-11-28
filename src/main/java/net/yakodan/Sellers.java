package net.yakodan;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Вспомогательный класс, хранящий в себе информацию о продавцах
 */
public class Sellers {

    private final HashMap<String, ArrayList> sellers;

    /**
     * Конструктор класса. Считывает файл и закидывает данные из него в соответствующий объект
     * @param path путь к считываемому файлу
     */
    public Sellers(Path path){
        try(Reader reader = Files.newBufferedReader(path)){
            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

            HashMap<String, ArrayList> sellers = new HashMap<>();

            parser.forEach((key, value) -> sellers.put(key, (ArrayList) value));

            this.sellers = sellers;

        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, ArrayList> getSellers() {
        return sellers;
    }
}
