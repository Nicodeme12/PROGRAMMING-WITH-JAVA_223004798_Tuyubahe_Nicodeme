package Supermarket;
import java.util.Scanner;
public class supermarketbills{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double totalBill = 0;
        
        System.out.print("Enter the number of different items: ");
        int numberOfItems = scanner.nextInt();
        
        // Use an array to store item details for the receipt
        String[] itemNames = new String[numberOfItems];
        double[] itemPrices = new double[numberOfItems];
        int[] itemQuantities = new int[numberOfItems];
        double[] itemSubtotals = new double[numberOfItems];
        
        // Loop to process each item
        for (int i = 0; i < numberOfItems; i++) {
            System.out.println("\n--- Item " + (i + 1) + " ---");
            System.out.print("Enter item name: ");
            itemNames[i] = scanner.next();
            
            System.out.print("Enter price per unit: ");
            itemPrices[i] = scanner.nextDouble();
            
            System.out.print("Enter quantity purchased: ");
            itemQuantities[i] = scanner.nextInt();
            
            // Calculate subtotal and add to total bill
            itemSubtotals[i] = itemPrices[i] * itemQuantities[i];
            totalBill += itemSubtotals[i];
        }
        
        // Conditional statement for discount
        double discount = 0;
        if (totalBill > 50000) {
            discount = totalBill * 0.05;
        }
        
        double finalAmount = totalBill - discount;
        
        // Print the receipt
        System.out.println("\n=====================================");
        System.out.println("          SUPERMARKET RECEIPT");
        System.out.println("=====================================");
        System.out.printf("%-15s %-5s %-10s %-10s\n", "Item", "Qty", "Price", "Subtotal");
        System.out.println("-------------------------------------");
        
        for (int i = 0; i < numberOfItems; i++) {
            System.out.printf("%-15s %-5d %-10.2f %-10.2f\n", 
                itemNames[i], itemQuantities[i], itemPrices[i], itemSubtotals[i]);
        }
        
        System.out.println("-------------------------------------");
        System.out.printf("Grand Total before Discount: %.2f\n", totalBill);
        if (discount > 0) {
            System.out.printf("Discount (5%):               -%.2f\n", discount);
        }
        System.out.println("-------------------------------------");
        System.out.printf("Final Amount Payable:        %.2f\n", finalAmount);
        System.out.println("=====================================");
        
        scanner.close();
    }
}
		
	