package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class StoreApp {
    public static HashMap<Integer, Product> inventory =
            new HashMap<Integer, Product>();

    public static void main(String[] args) {

        loadInventory();
        Scanner scanner = new Scanner(System.in);

        String goAgain;
        do {
            System.out.print("What item # are you interested in? ");
            String input = scanner.nextLine();

            int id = Integer.parseInt(input);
            try {
                Product matchedProduct = inventory.get(id);
                System.out.printf("We carry %s and the price is $%.2f%n",
                        matchedProduct.getName(), matchedProduct.getPrice());

            } catch (NullPointerException e) {
                System.out.println("We don't carry that product");
            }

            System.out.print("Do you want to search again? (yes/no): ");
            goAgain = scanner.nextLine();

        } while (goAgain.equalsIgnoreCase("yes"));
    }

    private static void loadInventory() {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/inventory.csv"));
            bufReader.readLine();

            String input;
            while ((input = bufReader.readLine()) != null) {
                String[] tokens = input.split("\\|");

                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                float price = Float.parseFloat(tokens[2]);
                Product product = new Product(id, name, price);

                inventory.put(id, product);
            }
            bufReader.close();

        } catch (IOException e) {
            System.out.println("ERROR: Invalid IO Stream " + e.getMessage());
        }
    }
}
