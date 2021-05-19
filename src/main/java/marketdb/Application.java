package marketdb;


import marketdb.services.ProductReg;
import marketdb.services.Register;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        ProductReg productreg = new ProductReg();
        Scanner scanner = new Scanner(System.in);
        Register register = new Register();

        boolean run = true;
        while (run) {
            menu();
            int userAnswer = scanner.nextInt();
            switch (userAnswer) {
                case 1:
                    System.out.println(productreg.getProducts());
                    break;
                case 2:
                    System.out.println(productreg.getProduct());
                    break;

                case 3:
                    productreg.registerProduct();
                    break;

                case 4:
                    productreg.updateProduct();
                    break;

                case 5:
                    productreg.deleteProduct();
                    break;
                case 6:
                    register.addCart();
                    break;
                case 7:
                    register.transactionTable();
                    break;

                case 8:
                    register.purchaseTable();
                    break;

                case 99:
                   run = false;
                   break;

                default:
                    System.out.println("Invalid option.");

            }
        }
        scanner.close();
        productreg.terminate();
        register.terminate();
    }




    private static void menu() {

    System.out.println("What do you wish to do? ");
    System.out.println("Type 1 to consult the products table.");
    System.out.println("Type 2 to consult a product by name.");
    System.out.println("Type 3 to register a product.");
    System.out.println("Type 4 to update a products information.");
    System.out.println("Type 5 to remove a product from the database.");
    System.out.println("Type 6 to register items in the cart");
    System.out.println("Type 7 to consult the transactions table");
    System.out.println("Type 8 to consult the purchases table");
    System.out.println("Type 99 to exit.");
    }

}


