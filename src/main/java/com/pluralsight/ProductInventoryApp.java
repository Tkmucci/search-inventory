package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductInventoryApp {

    //Declared all my reusable variables at the top of the class
    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner userInput = new Scanner(System.in);
    static int userChoice;
    static String readFileName;

    //Main method
    public static void main(String[] args) {

        //reload the inventory before I run the code
        getInventory();

        //Nice welcome message
        System.out.println("Welcome to the Product Inventory App!");
        //while Loop to keep the app running until the user chooses to exit
        while (true) {
            //Displays the menu and asks the user to enter a choice
            productMenu();

            userChoice = userInput.nextInt();
            userInput.nextLine();

            //switch statement to handle user input
            switch (userChoice) {

                case 1 -> displayInventory();
                case 2 -> lookupProductById();
                case 3 -> searchByPrice();
                case 4 -> addProductOptions();
                case 5 -> {

                    saveInventory();
                    System.out.println("Exiting the app...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice: Try again\n");
            }

            //Pauses the app for a second and waits for the user to press enter before displaying the menu again
            System.out.println("\nPress enter to continue...to the next menu option");
            System.out.println("---------------------------------------------");
            userInput.nextLine();

            System.out.println("\n");
        }
    }

    //Method to do the actual displaying of the inventory
    public static void displayInventory() {

        System.out.println("\nProduct Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }

        for (Product product : inventory) {
            System.out.printf("""
                            %s | %s | %.2f
                            """
                    , product.getId()
                    , product.getName()
                    , product.getPrice());
        }

        System.out.println("\nTotal products: " + inventory.size());

        System.out.println("\n");
        System.out.println("Missing any products? (Y/N)");
        String yesOrNo = userInput.nextLine();

        if (yesOrNo.equalsIgnoreCase("yes") || yesOrNo.equalsIgnoreCase("y")) {

            readMyProductFileToDisplay();
        }
    }

    //Method to populate the inventory with some sample data
    public static void getInventory() {

//        inventory.add(new Product(1, "MacBook", 1000.00));
//        inventory.add(new Product(2, "iPhone", 500.00));
//        inventory.add(new Product(3, "iPad", 200.00));
//        inventory.add(new Product(4, "iPod", 100.00));
//        inventory.add(new Product(5, "Apple Watch", 300.00));

        readFileName = "products.txt";
        // Clear existing inventory before loading new data
        inventory.clear();

        try {
            BufferedReader fileReader = new BufferedReader(
                    new FileReader("src/main/resources/" + readFileName));
            String line;

            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    inventory.add(new Product(id, name, price));
                }
            }
            fileReader.close();
            System.out.println("Inventory loaded: " + inventory.size() + " products");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check the file name and try again.");
        } catch (IOException e) {
            System.out.println("Error reading the file. Please try again.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Method to display the menu options to the user
    public static void productMenu() {

        System.out.print("""
                \n
                What do you want to do?
                    1 - View inventory
                    2 - Look up product by ID No.
                    3 - Find all products by price range
                    4 - Add product
                    5 - Exit
                Enter Command:\s""");
    }

    //Method to add a product to the inventory
    public static void appendProduct() {

        int nextId = getNextId();

        System.out.print("Enter the product name: ");
        String name = userInput.nextLine();

        System.out.print("Enter the product price: ");
        double price = userInput.nextDouble();
        userInput.nextLine();

        inventory.add(new Product(nextId, name, price));
        System.out.println("Product added successfully!");

    }

    //Method to look up a product by ID
    public static void lookupProductById() {

        System.out.print("\nEnter the product ID number: ");
        int userId = userInput.nextInt();
        userInput.nextLine();
        boolean found = false;
        for (Product product : inventory) {
            if (product.getId() == userId) {
                System.out.printf("The product you are looking for is: %d | %s | %.2f%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No product could be found with that ID. Try again.");
        }
    }

    //Method to search for products by price
    public static void searchByPrice() {


        //asks user to enter a min price
        System.out.print("\nEnter a minimum price: ");
        double userMin = userInput.nextDouble();

        //asks user to enter a max price
        System.out.print("Enter a maximum price: ");
        double userMax = userInput.nextDouble();
        userInput.nextLine();

        //creating bool variable to check if found is true or false
        boolean found = false;

        //iterates through product array
        for (Product product : inventory) {
            double price = product.getPrice();

            //price of product has to be greater or equal to user min AND less than or equal to user max

            if (price >= userMin && price <= userMax) {
                found = true;
                System.out.printf("The product(s) in your price range: %d | %s | %.2f%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice());
            }
        }
        if (!found) {
            System.out.println("No product could be found within price range. Try again.");
        }
    }

    //Method to get the next available ID number
    public static int getNextId() {
        int maxId = 0;
        for (Product product : inventory) {
            if (product.getId() > maxId) {
                maxId = product.getId();
            }
        }
        return maxId + 1;
    }

    public static void addProductOptions() {

        System.out.print("""
                What would you like to do?
                    1 - Add a product from a file
                    2 - Add a product manually
                Enter Command:\s""");
        int userChoice = userInput.nextInt();
        userInput.nextLine();

        if (userChoice == 1) {
            readMyProductFileToDisplay();
        } else if (userChoice == 2) {
            appendProduct();
        } else {
            System.out.println("Invalid choice: Try again\n");
            addProductOptions();
        }
    }

//    public static void readProductFileToDisplay() {
//
//        System.out.println("\nEnter the name of the file you want to access: ");
//        readFileName = userInput.nextLine();
//        System.out.println("File name: " + readFileName + " Displaying file contents:\n");
//
//        try {
//
//            BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/" + readFileName));
//            String line;
//
//            while ((line = fileReader.readLine()) != null) {
//
//                System.out.println(line);
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found. Please check the file name and try again.");
//        } catch (IOException e) {
//            System.out.println("Error reading the file. Please try again.");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    //reading the file and adding the products to the inventory
    public static void readMyProductFileToDisplay() {

        System.out.println("\nEnter the name of the file you want to access: ");
        String readFileName = userInput.nextLine();

        int loadedCount = 0;

        // Updated the try catch block to handle if the ID is already in the inventory
        try (

                BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/" + readFileName))) {

            String line;
            //int lineNumber = 0;

            while ((line = fileReader.readLine()) != null) {

                String[] parts = line.split("\\|");

//                // Skip first line
//                lineNumber++;
//
//                if (lineNumber == 1) {
//                    continue;
//                }

                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());

                        // Check if product with this ID already exists
                        boolean exists = false;

                        for (Product product : inventory) {
                            if (product.getId() == id) {
                                exists = true;
                                break;
                            }
                        }

//                        boolean exists = inventory.stream()
//                                .anyMatch(product -> product.getId() == id);

                        if (!exists) {
                            inventory.add(new Product(id, name, price));
                            loadedCount++;
                        } else {
                            System.out.println("Skipping duplicate ID: " + id);
                        }

                        //give the user an option to view the unformatted data and duplicate data
                        // if they choose to view it, display the unformatted data and duplicate data in the file
                        // if they choose not to view it, continue with the next line of the file

                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }

                } else {
                    System.out.println("Skipping unformatted data on line: " + line);
                }
            }

            System.out.println("Successfully loaded " + loadedCount + " products from " + readFileName);

        } catch (FileNotFoundException e) {

            System.out.println("File not found. Please check the file name and try again.");

        } catch (IOException e) {

            System.out.println("Error reading the file. Please try again.");

        }
    }

    //to update the inventory file after adding or removing a product to the inventory
    public static void saveInventory() {
        try (BufferedWriter fileWriter = new BufferedWriter(
                new FileWriter("src/main/resources/" + readFileName))) {

            for (Product product : inventory) {
                fileWriter.write(String.format("%d|%s|%.2f%n"
                        , product.getId()
                        , product.getName()
                        , product.getPrice()
                ));
            }

            System.out.println("Inventory saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
}