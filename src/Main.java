import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi!");

        Float weight = 0f;
        Float distance = 0f;
        Float pricePerKg = 1.3f;
        Float pricePerKm = 2.3f;

        System.out.println(1.03 - .42);

        //https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
        BigDecimal a = new BigDecimal("1.03");
        BigDecimal b = new BigDecimal(".42");
        System.out.println(" = " + a.subtract(b));

        double f = 0.0;
        for (int i=1; i <= 10; i++) {
            f += 0.1;
        }
        System.out.println(" = " + f);
        //http://neerc.ifmo.ru/wiki/index.php?title=%D0%9F%D1%80%D0%B5%D0%B4%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5_%D0%B2%D0%B5%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D1%85_%D1%87%D0%B8%D1%81%D0%B5%D0%BB


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the weight in kilograms or 'q' for quit: ");
        String inputString = scanner.nextLine();
        System.out.println("String read from console is: " + inputString);
        if (!inputString.toLowerCase().equals("q")) {
            weight = Float.parseFloat(inputString);
        } else {
            System.out.println("\nBye!");
            return;
        }


        System.out.println("Enter the distance in kilometers  or 'q' for quit: ");
        inputString = scanner.nextLine();
        System.out.println("String read from console is: " + inputString + "\n");
        if (!inputString.toLowerCase().equals("q")) {
            distance = Float.parseFloat(inputString);
        } else {
            System.out.println("\nBye!");
            return;
        }

        Float result = weight * pricePerKg + distance * pricePerKm;
        System.out.println("Price: %f" + result);


    }
}
