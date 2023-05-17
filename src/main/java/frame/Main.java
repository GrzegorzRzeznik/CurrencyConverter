package frame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter(args[0]);
        try{
            MainFrame frame = new MainFrame(CurrencyListProvider.getList(), converter);
            frame.setTitle("Currency Converter");
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(300, 200));
            frame.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
