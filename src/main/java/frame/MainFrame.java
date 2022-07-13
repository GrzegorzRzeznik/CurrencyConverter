package frame;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainFrame extends JFrame {

    private final Converter converter;
    private final JPanel panel1 = new JPanel();
    private final JPanel panel2 = new JPanel();
    private final JPanel panel3 = new JPanel();
    private final JComboBox<String> currencyChoice1;
    private final JComboBox<String> currencyChoice2;

    public MainFrame(String[] currencies, Converter converter) {
        this.converter = converter;
        setLayout(new GridLayout());
        currencyChoice1 = new JComboBox<>(currencies);
        currencyChoice2 = new JComboBox<>(currencies);
        setFocusable(true);
        addPanels();
    }

    private void addPanels() {
        panel1.add(new Label("amount:   "));
        panel1.add(new Label("from:"));
        panel1.add(new Label("to:"));
        JFormattedTextField amountOfCurrency = new JFormattedTextField(createNumberFormat());
        amountOfCurrency.setColumns(7);
        amountOfCurrency.setHorizontalAlignment(0);
        amountOfCurrency.setEditable(true);
        panel2.add(amountOfCurrency);
        panel2.add(currencyChoice1);
        panel2.add(currencyChoice2);
        JButton convertButton = new JButton("Convert");

        panel3.add(convertButton);
        add(panel1);
        add(panel2);
        JTextField result = new JTextField("result");
        result.setColumns(7);
        result.setEditable(false);
        result.setHorizontalAlignment(0);
        add(result);
        add(panel3);

        convertButton.addActionListener(e -> {
            if (amountOfCurrency.getValue() == null){
                result.setText("0");
            }else {
                try {
                    result.setText(converter.convert(Double.parseDouble(amountOfCurrency.getText()),
                            currencyChoice1.getSelectedItem().toString(),
                            currencyChoice2.getSelectedItem().toString()));
                } catch (CurrencyApiException cae) {
                    JOptionPane.showMessageDialog(MainFrame.this, cae.getMessage());
                    cae.printStackTrace();
                }
            }
        });
    }


    private NumberFormat createNumberFormat() {
        NumberFormat format = DecimalFormat.getInstance();
        format.setGroupingUsed(false);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.DOWN);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        return format;
    }
}
