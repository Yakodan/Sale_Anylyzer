package net.yakodan;

import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        // Блок для генерации записей для файла sales.json :)
//        Random rand = new Random();
//
//        String[] sellersArray = {"43245-sr", "78762-sr", "22945-sr", "96937-sr", "43457-sr"};
//        String[] productsArray = {"53484-p", "35345-p", "66978-p", "83495-p", "18947-p", "23565-p"};
//
//        for(int i=0;i<1000;i++){
//
//            int r = rand.nextInt(90000) + 10000;
//
//            System.out.println("\"" + r + "-s\": {");
//            System.out.println("    \"seller_id\": \"" + sellersArray[rand.nextInt(sellersArray.length)] + "\",");
//            System.out.println("    \"product_id\": \"" + productsArray[rand.nextInt(productsArray.length)] + "\",");
//            System.out.println("    \"sale_amount\": " + (rand.nextInt(15)+1) + ",");
//            System.out.println("    \"date\": \"" + (rand.nextInt(30)+1) + ".11.2023\"");
//            System.out.println("},");
//
//        }

        Products products = new Products(Paths.get("src/main/resources/input/products.json"));
        Sellers sellers = new Sellers(Paths.get("src/main/resources/input/sellers.json"));
        Availability availability = new Availability(Paths.get("src/main/resources/input/availability.json"));
        Sales sales = new Sales(Paths.get("src/main/resources/input/sales.json"));

        Min_Price.analyze(products, sellers, availability, "src/main/resources/output/min_price.json");

        Top5_Dates.analyze(sales, "src/main/resources/output/top5_dates.json");

    }
}