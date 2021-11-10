import java.util.List;
import java.util.Scanner;

public class MainScanner {

    // Singleton Pattern
    private static MainScanner mainScannerInstance;

    private Scanner scanner = new Scanner(System.in);

    private MainScanner(){
    }

    public static MainScanner getSingleInstance() {
        if (mainScannerInstance == null)
            mainScannerInstance = new MainScanner();
        return mainScannerInstance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int validInput(int range){
        int index = scanner.nextInt();
        while (index > range){
            System.out.println("Invalid instruction!");
            index = scanner.nextInt();
        }
        return index;
    }
}
