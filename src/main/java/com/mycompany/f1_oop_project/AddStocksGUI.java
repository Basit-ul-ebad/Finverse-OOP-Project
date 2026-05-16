package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddStocksGUI {
    public void showAddStocksGUI(JFrame parentFrame) {
        // Create a new frame for adding stocks
        JFrame addStocksFrame = new JFrame("Add Stocks");
        addStocksFrame.setSize(400, 300);
        addStocksFrame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 140, 190));
        JLabel titleLabel = new JLabel("Add New Stock");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Stock Name:");
        JTextField nameField = new JTextField();

        JLabel priceLabel = new JLabel("Stock Price:");
        JTextField priceField = new JTextField();

        JButton addButton = new JButton("Add Stock");
        JButton cancelButton = new JButton("Cancel");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        // Add Panels to Frame
        addStocksFrame.add(titlePanel, BorderLayout.NORTH);
        addStocksFrame.add(formPanel, BorderLayout.CENTER);
        addStocksFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String priceText = priceField.getText();

            if (name.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(addStocksFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                Stock stock = new Stock(name, price);
                StockHandler stockHandler = new StockHandler();
                stockHandler.saveData(stock);

                JOptionPane.showMessageDialog(addStocksFrame, "Stock added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                addStocksFrame.dispose(); // Close AddStocksGUI frame
                parentFrame.setVisible(true); // Show parent frame
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addStocksFrame, "Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            addStocksFrame.dispose(); // Close AddStocksGUI frame
            parentFrame.setVisible(true); // Show parent frame
        });

        // Hide parent frame and show AddStocksGUI frame
        parentFrame.setVisible(false);
        addStocksFrame.setVisible(true);
    }
}
