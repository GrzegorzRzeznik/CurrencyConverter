package frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CurrencyListProvider {

    public static String[] getList() throws FileNotFoundException {

        File file = new File("src/main/resources/currencies");
        ArrayList<String> currencyList = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(file));
        while(scanner.hasNext()){
            currencyList.add(scanner.nextLine());
        }
        return currencyList.toArray(new String[0]);
    }
}
