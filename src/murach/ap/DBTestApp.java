package murach.ap;

import java.sql.*;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DBTestApp {
    public static void main(String args[]) throws SQLException {
        
        //working in the hvs database
        String dbUrl = "jdbc:mysql://localhost:3306/hvs";
        //shouldn't use root, will change to proper credentials once created
        String username;
        String password;

        Connection connection;
        PreparedStatement ps;
        ResultSet rs;

        //defines new scanner object
        Scanner sc = new Scanner(System.in);

        boolean successfulLogin = true;

        do {
            //intro menu which asks for username
            introMenu();
            System.out.print("Username: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();

            try {
                //tries to establish a connection to the database
                connection = DriverManager.getConnection(dbUrl, username, password);
                successfulLogin = true;  
            }
            //when the connection fails
            catch (SQLException e) {
                //displays an error message
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println("Please try again...");
                
                //tries again
                successfulLogin = false;
            }
        } while(!successfulLogin);

        //have to fix, this is a slopping solution with main throwing an exception
        connection = DriverManager.getConnection(dbUrl, username, password);

        int menuOption;

        do {
            mainMenu();
            System.out.print("Enter a number: ");
            try {
                menuOption = sc.nextInt();

                switch(menuOption) {
                    //add item menu
                    case 1:
                    try {
                        //variables used to grab user input
                        String name, upc;
                        double price, discount;

                        addItemMenu();


                        System.out.print("Item name: ");
                        sc.nextLine();
                        name = sc.nextLine();
                        System.out.print("Item upc: ");
                        upc = sc.nextLine();
                        System.out.print("Item price ($xx.xx format): ");
                        price = sc.nextDouble();
                        System.out.print("Item discount percentage (0.xx format): ");
                        discount = sc.nextDouble();

                        Item potentialItem = new Item(upc, name, price, discount);

                        ps = connection.prepareStatement(potentialItem.getInsertIntoDatabaseStatement() );
                        ps.execute();
                    
                    } catch (SQLException f) {
                        System.out.println(f.getMessage());

                    //some issue with the scanner trying to grab data
                    } catch (Exception e) {
                        System.out.println("ERROR: Invalid input! Please try again...");
                        //stop endless looping
                        System.out.println();
                        sc.nextLine();
                    }
                    break;

                    //add store menu
                    case 2:
                    try {
                        //variables used to grab user input
                        String storeNumber;
                        String address;
                        String city;
                        String state;
                        String zip;
                        String phoneNumber;

                        addStoreMenu();
                        
                        System.out.print("Store number: ");
                        sc.nextLine();
                        storeNumber = sc.nextLine();
                        System.out.print("Store street address\n(Don't include city, state, and/or zip code): ");
                        address = sc.nextLine();
                        System.out.print("Store city: ");
                        city = sc.nextLine();
                        System.out.print("Store state (only two letters): ");
                        //turns the state into Uppercase letters
                        state = sc.nextLine().toUpperCase();
                        System.out.print("Store zip code: ");
                        zip = sc.nextLine();
                        System.out.print("Store phone number: ");
                        //removes all the misc. chars when someone types in a phone number and just keeps the digits
                        phoneNumber = sc.nextLine().replaceAll("[^0-9]", ""); 

                        Store potentialStore = new Store(storeNumber, address, city, state, zip, phoneNumber);

                        ps = connection.prepareStatement(potentialStore.getInsertIntoDatabaseStatement() );
                        ps.execute();
                    } catch (SQLException f) {
                        System.out.println(f.getMessage());

                    //some issue with the scanner trying to grab data
                    } catch (Exception e) {
                        System.out.println("ERROR: Invalid input! Please try again...");
                        //stop endless looping
                        System.out.println();
                        sc.nextLine();
                    }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        //exits the program successfully
                        System.exit(0);
                    default:
                        System.out.println("Error: Not a valid option! Please try again...");
                        break;
                }

            //if the input is invalid--i.e., anything besides an int
            } catch(Exception e) {
                System.out.println("ERROR: Invalid input! Please try again...");
                //stop endless looping
                System.out.println();
                sc.nextLine();
            }

            //loops indefinitely until option 5 is entered
        } while(true);




        






      
    }

    public static void introMenu() {
        System.out.println("*************************************");
        System.out.println("Welcome to the HVS Management System.");
        System.out.println("*************************************");
        System.out.println("Please enter your username and password below:");
    }

    public static void mainMenu() {
        System.out.println("*************************************");
        System.out.println("            MAIN MENU                ");
        System.out.println("*************************************");
        System.out.println("Please choose from the following options:");
        System.out.println("1. Add an item to the database");
        System.out.println("2. Add a store to the database");
        System.out.println("3. Add a register to the database");
        System.out.println("4. Add a member to the database");
        System.out.println("5. Exit program");
    }

    public static void addItemMenu() {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("NOTE: Item upc and item name must not already");
        System.out.println("be contained in the database! Otherwise, the ");
        System.out.println("item won't be added...");
        System.out.println("-------------------------------------------");
        System.out.println();
        System.out.println("Enter the following details about the item:");
    }

    public static void addStoreMenu() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("NOTE: The store number and phone number must");
        System.out.println("not already me contained in the database!   ");
        System.out.println("Otherwise, the store won't be added...      ");
        System.out.println("--------------------------------------------");
        System.out.println();
        System.out.println("Enter the following details about the item:");
    }
}