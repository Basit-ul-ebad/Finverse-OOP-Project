package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserMenu extends CommonCalls implements DisplayMenu {

    @Override
    public void displayMenuWithGui(Object object) {
        if (object instanceof UserAccountInfo) {
            UserAccountInfo user = (UserAccountInfo) object;
            displayUserMenuWithGUI(user);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid object type", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayUserMenuWithGUI(UserAccountInfo user) {
        JFrame userMenuFrame = new JFrame("User Menu");
        userMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userMenuFrame.setSize(500, 500); // Increase the size for better visibility
        userMenuFrame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 140, 190));  // Title panel color
        JLabel titleLabel = new JLabel("Welcome to Your Account Menu", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        userMenuFrame.add(titlePanel, BorderLayout.NORTH);

        // Center Panel for the menu options
        JPanel menuPanel = new JPanel(new GridLayout(7, 1, 10, 10));  // Add spacing between the components
        menuPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Hello, " + user.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(45, 140, 190)); // Matching theme color

        JLabel balanceLabel = new JLabel("Your Balance: $" + user.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balanceLabel.setForeground(new Color(45, 140, 190)); // Matching theme color

        // Buttons
        JButton depositButton = new JButton("Deposit Money");
        JButton withdrawButton = new JButton("Withdraw Money");
        JButton transferButton = new JButton("Transfer Money");
        JButton stockMenuButton = new JButton("Stock Market Menu"); // New button for Stock Menu
        JButton exitButton = new JButton("Exit");

        depositButton.setBackground(new Color(45, 140, 190));  // Button background color
        depositButton.setForeground(new Color(0));              // Button text color

        withdrawButton.setBackground(new Color(45, 140, 190)); // Button background color
        withdrawButton.setForeground(new Color(0));             // Button text color

        transferButton.setBackground(new Color(45, 140, 190)); // Button background color
        transferButton.setForeground(new Color(0));             // Button text color

        stockMenuButton.setBackground(new Color(45, 140, 190)); // Button background color
        stockMenuButton.setForeground(new Color(0));            // Button text color

        exitButton.setBackground(new Color(45, 140, 190));     // Button background color
        exitButton.setForeground(new Color(0));

        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        stockMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the menu panel
        menuPanel.add(welcomeLabel);
        menuPanel.add(balanceLabel);
        menuPanel.add(depositButton);
        menuPanel.add(withdrawButton);
        menuPanel.add(transferButton);
        menuPanel.add(stockMenuButton); // Add Stock Market Menu button
        menuPanel.add(exitButton);

        userMenuFrame.add(menuPanel, BorderLayout.CENTER);

        // Action listener for depositButton
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = showCustomInputDialog(userMenuFrame, "Enter amount to deposit:");
                if (amountStr != null && !amountStr.trim().isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        user.deposit(amount);
                        balanceLabel.setText("Your Balance: $" + user.getBalance());

                        // Show message without icon using PLAIN_MESSAGE
                        JOptionPane.showMessageDialog(userMenuFrame, "Deposited: $" + amount, "Success", JOptionPane.PLAIN_MESSAGE);
                    } catch (NumberFormatException ex) {
                        // Show error message without icon using PLAIN_MESSAGE
                        JOptionPane.showMessageDialog(userMenuFrame, "Invalid amount entered.", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        // Action listener for withdrawButton
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = showCustomInputDialog(userMenuFrame, "Enter amount to withdraw:");
                if (amountStr != null && !amountStr.trim().isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        if (user.withdraw(amount)) {
                            balanceLabel.setText("Your Balance: $" + user.getBalance());
                            // Remove icon by using PLAIN_MESSAGE
                            JOptionPane.showMessageDialog(userMenuFrame, "Withdrawn: $" + amount, "Success", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            // Remove icon by using PLAIN_MESSAGE
                            JOptionPane.showMessageDialog(userMenuFrame, "Insufficient balance.", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        // Remove icon by using PLAIN_MESSAGE
                        JOptionPane.showMessageDialog(userMenuFrame, "Invalid amount entered.", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        // Action listener for transferButton
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipientName = showCustomInputDialog(userMenuFrame, "Enter recipient's name:");
                if (recipientName != null && !recipientName.trim().isEmpty()) {
                    UserAccountInfo recipient = findUserByName(recipientName);
                    if (recipient != null) {
                        String amountStr = showCustomInputDialog(userMenuFrame, "Enter amount to transfer:");
                        if (amountStr != null && !amountStr.trim().isEmpty()) {
                            try {
                                double amount = Double.parseDouble(amountStr);
                                if (user.withdraw(amount)) {
                                    recipient.deposit(amount);
                                    balanceLabel.setText("Your Balance: $" + user.getBalance());
                                    // Remove icon by using PLAIN_MESSAGE
                                    JOptionPane.showMessageDialog(userMenuFrame, "Transferred: $" + amount + " to " + recipient.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    // Remove icon by using PLAIN_MESSAGE
                                    JOptionPane.showMessageDialog(userMenuFrame, "Insufficient balance.", "Error", JOptionPane.PLAIN_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                // Remove icon by using PLAIN_MESSAGE
                                JOptionPane.showMessageDialog(userMenuFrame, "Invalid amount entered.", "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    } else {
                        // Remove icon by using PLAIN_MESSAGE
                        JOptionPane.showMessageDialog(userMenuFrame, "Recipient not found.", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        // Action listener for stockMenuButton
        stockMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockMenu stockMenu = new StockMenu();
                stockMenu.displayMenuWithGui(user);
            }
        });

        // Action listener for exitButton
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMenuFrame.dispose();
            }
        });

        // Show the user menu window
        userMenuFrame.setVisible(true);
    }

    private static UserAccountInfo findUserByName(String name) {
        List<UserAccountInfo> users = userHandler.loadAllData();
        for (UserAccountInfo user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    // Custom input dialog with "OK" and "Cancel" buttons
    private static String showCustomInputDialog(JFrame parentFrame, String message) {
        final String[] result = new String[1]; // Used to store user input

        JDialog dialog = new JDialog(parentFrame, "Enter Amount", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 150);

        JLabel label = new JLabel(message);
        JTextField textField = new JTextField(20);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");  // Cancel Button

        // Action when OK button is clicked
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = textField.getText(); // Store the result
                dialog.setVisible(false); // Close the dialog
            }
        });

        // Action when Cancel button is clicked
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = null; // Null to indicate cancellation
                dialog.setVisible(false); // Close the dialog
            }
        });

        // Add components to the dialog
        dialog.add(label);
        dialog.add(textField);
        dialog.add(okButton);
        dialog.add(cancelButton);  // Adding cancel button to the layout

        dialog.setLocationRelativeTo(parentFrame); // Center dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        return result[0]; // Return the user input (or null if canceled)
    }
}
