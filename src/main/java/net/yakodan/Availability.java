package net.yakodan;

import com.github.cliftonlabs.json_simple.JsonArray;
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
 * Вспомогательный класс, хранящий в себе информацию о товаре у конкретного продавца
 */
public class Availability {
    
    private final ArrayList<HashMap> availability;

    /**
     * Конструктор класса. Считывает файл и закидывает данные из него в соответствующий объект
     * @param path путь к считываемому файлу
     */
    public Availability(Path path){
        try(Reader reader = Files.newBufferedReader(path)){
            JsonArray parser = (JsonArray) Jsoner.deserialize(reader);

            ArrayList<HashMap> availability = new ArrayList();

            parser.forEach(map -> availability.add((HashMap) map));

            this.availability = availability;

        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<HashMap> getAvailability() {
        return availability;
    }
}
