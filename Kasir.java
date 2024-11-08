
import java.util.Scanner;

public class Kasir {
    public static void main(String[] args) {
         int[] denominations = {100, 50, 20, 10, 5, 2, 1};
        // Ввод цены и купюры
        System.out.print("Введите цену: ");
        int price = 1;

        System.out.print("Введите купюру: ");
        int payment = 100;

        int change = payment - price;

        if (change < 0) {
            System.out.println("Недостаточно средств!");
        } else {
            System.out.println("Сдача: " + change);

            for (int denomination : denominations) {
                int count = change / denomination;
                if (count > 0) {
                    System.out.println(denomination + " x " + count);
                }
                change %= denomination;
            }
        }

        //scanner.close();
    }
} 
