package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockMenu extends CommonCalls implements DisplayMenu {

    private JFrame frame;
    private JTextArea stockDisplayArea;
    private UserAccountInfo user;
    private List<Stock> stocks;

    @Override
    public void displayMenuWithGui(Object object) {
        if (object instanceof UserAccountInfo) {
            this.user = (UserAccountInfo) object;
            this.stocks = stockHandler.loadAllData();
            createAndShowGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid user account info.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Stock Market Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Stock display area
        stockDisplayArea = new JTextArea();
        stockDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(stockDisplayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        JButton viewStockButton = new JButton("View Stocks");
        JButton buyStockButton = new JButton("Buy Shares");
        JButton sellStockButton = new JButton("Sell Shares");
        JButton viewBalanceButton = new JButton("View Balance");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(viewStockButton);
        buttonPanel.add(buyStockButton);
        buttonPanel.add(sellStockButton);
        buttonPanel.add(viewBalanceButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        viewStockButton.addActionListener(e -> startStockThreads());
        buyStockButton.addActionListener(e -> buyStock());
        sellStockButton.addActionListener(e -> sellStock());
        viewBalanceButton.addActionListener(e -> viewBalance());
        exitButton.addActionListener(e -> frame.dispose());

        frame.add(panel);
        frame.setVisible(true);
    }

    private void startStockThreads() {
        if (stocks != null && !stocks.isEmpty()) {
            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(2000); // Refresh every 2 seconds

                        // Update stock prices
                        StringBuilder stockData = new StringBuilder("Live Stock Prices:\n");
                        for (Stock stock : stocks) {
                            stockData.append("Stock: ").append(stock.getStockName())
                                    .append(" | Current Price: $").append(stock.getCurrentPrice()).append("\n");
                        }

                        stockDisplayArea.setText(stockData.toString());
                    }
                } catch (InterruptedException e) {
                    stockDisplayArea.append("\nStock update interrupted.");
                }
            }).start();
        } else {
            JOptionPane.showMessageDialog(frame, "No stocks available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buyStock() {
        String stockName = JOptionPane.showInputDialog(frame, "Enter the name of the stock:", "Buy Shares", JOptionPane.PLAIN_MESSAGE);
        if (stockName != null) {
            for (Stock stock : stocks) {
                if (stock.getStockName().equalsIgnoreCase(stockName)) {
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter the number of shares to buy:", "Buy Shares", JOptionPane.PLAIN_MESSAGE);
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        user.buyStock(stock, quantity);
                        JOptionPane.showMessageDialog(frame, "Successfully bought " + quantity + " shares of " + stockName + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame, "Invalid quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Stock not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sellStock() {
        String stockName = JOptionPane.showInputDialog(frame, "Enter the name of the stock:", "Sell Shares", JOptionPane.PLAIN_MESSAGE);
        if (stockName != null) {
            for (Stock stock : stocks) {
                if (stock.getStockName().equalsIgnoreCase(stockName)) {
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter the number of shares to sell:", "Sell Shares", JOptionPane.PLAIN_MESSAGE);
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        user.sellStock(stock, quantity);
                        JOptionPane.showMessageDialog(frame, "Successfully sold " + quantity + " shares of " + stockName + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame, "Invalid quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Stock not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBalance() {
        JOptionPane.showMessageDialog(frame, "Account Balance: $" + user.getBalance(), "Account Balance", JOptionPane.INFORMATION_MESSAGE);
    }
}
