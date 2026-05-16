package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminMenu extends CommonCalls implements DisplayMenu {

    @Override
    public void displayMenuWithGui(Object object) {
        if (object instanceof Admin) {
            Admin admin = (Admin) object;

            // Create JFrame for admin menu
            JFrame adminFrame = new JFrame("Admin Menu");
            adminFrame.setSize(500, 500);
            adminFrame.setLayout(new BorderLayout());
            adminFrame.getContentPane().setBackground(new Color(248, 248, 248)); // Light Gray background

            // Title Panel
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(new Color(45, 140, 190)); // Title color
            JLabel welcomeLabel = new JLabel("Welcome, Admin " + admin.getName(), JLabel.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            welcomeLabel.setForeground(Color.WHITE);
            titlePanel.add(welcomeLabel);
            adminFrame.add(titlePanel, BorderLayout.NORTH);

            // Button Panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 buttons with spacing
            buttonPanel.setBackground(new Color(248, 248, 248)); // Light Gray background

            JButton viewUsersButton = createStyledButton("View All Users", new Color(72, 133, 237), Color.BLACK);
            JButton deleteUserButton = createStyledButton("Delete User", new Color(255, 87, 34), Color.BLACK);
            JButton totalBalanceButton = createStyledButton("Display Total Balance", new Color(40, 180, 99), Color.BLACK);
            JButton accountSummaryButton = createStyledButton("Generate Account Summary", new Color(255, 193, 7), Color.BLACK);
            JButton exitButton = createStyledButton("Exit", new Color(217, 83, 79), Color.BLACK);

            // Add buttons to button panel
            buttonPanel.add(viewUsersButton);
            buttonPanel.add(deleteUserButton);
            buttonPanel.add(totalBalanceButton);
            buttonPanel.add(accountSummaryButton);
            buttonPanel.add(exitButton);

            // Add button panel to the center of the frame
            adminFrame.add(buttonPanel, BorderLayout.CENTER);

            // Action listener for "View All Users"
            viewUsersButton.addActionListener(e -> {
                String userData = admin.DisplayAllUsers();
                JTextArea textArea = new JTextArea(userData);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for scroll pane

                // Display users in a modal dialog
                JOptionPane.showMessageDialog(adminFrame, scrollPane, "All Users", JOptionPane.INFORMATION_MESSAGE);
            });

            // Action listener for "Delete User"
            deleteUserButton.addActionListener(e -> {
                String accountNumber = JOptionPane.showInputDialog(adminFrame, "Enter Account Number to Delete:");
                if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                    boolean deleted = admin.deleteUser(accountNumber);
                    if (deleted) {
                        JOptionPane.showMessageDialog(adminFrame, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Action listener for "Display Total Balance"
            totalBalanceButton.addActionListener(e -> {
                double totalBalance = admin.displayTotalBalance();
                JOptionPane.showMessageDialog(adminFrame, "Total Balance across all user accounts: $" + totalBalance, "Total Balance", JOptionPane.INFORMATION_MESSAGE);
            });

            // Action listener for "Generate Account Summary"
            accountSummaryButton.addActionListener(e -> {
                String summary = admin.generateAccountSummary();
                JTextArea textArea = new JTextArea(summary);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for scroll pane

                // Display summary in a modal dialog
                JOptionPane.showMessageDialog(adminFrame, scrollPane, "Account Summary", JOptionPane.INFORMATION_MESSAGE);
            });

            // Action listener for "Exit"
            exitButton.addActionListener(e -> adminFrame.dispose());

            // // Configure and display the frame
            adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adminFrame.setLocationRelativeTo(null);
            adminFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid object type.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor); // Ensure contrast with button background
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Subtle border
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(10, 20, 10, 20));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}