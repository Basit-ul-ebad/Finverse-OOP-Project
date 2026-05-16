package com.mycompany.f1_oop_project;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class F1_OOP_Project {

    private static Signup signUp;
    private static Login logIn;

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("FinTech System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 140, 190));
        JLabel titleLabel = new JLabel("Welcome to the FinTech System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20)); // 2 rows, 2 columns, 20px gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        // Buttons with larger fonts and adjusted padding
        JButton signUpAdminButton = createStyledButton("Sign Up As Admin", new Color(0, 102, 204), Color.WHITE);
        signUpAdminButton.addActionListener(e -> {
            signUp = new Signup(2);
            signUp.signupWithGUI(frame);
        });

        JButton logInAdminButton = createStyledButton("Login As Admin", new Color(0, 153, 76), Color.WHITE);
        logInAdminButton.addActionListener(e -> {
            logIn = new Login(2);
            logIn.loginWithGUI(frame);
        });

        JButton signUpUserButton = createStyledButton("Sign Up As User", new Color(204, 102, 0), Color.WHITE);
        signUpUserButton.addActionListener(e -> {
            signUp = new Signup(1);
            signUp.signupWithGUI(frame);
        });

        JButton logInUserButton = createStyledButton("Login As User", new Color(153, 0, 204), Color.WHITE);
        logInUserButton.addActionListener(e -> {
            logIn = new Login(1);
            logIn.loginWithGUI(frame);
        });

        // Exit Button with dynamic sizing and visible text
        JButton exitButton = createStyledButton("Exit", Color.RED, Color.WHITE);
        exitButton.setPreferredSize(new Dimension(200, 50)); // Extra space for text visibility
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Exiting... Goodbye!");
            System.exit(0);
        });
        
        // Inside the button panel (add the new button)
        JButton addStocksButton = createStyledButton("Add Stocks", new Color(128, 0, 128), Color.WHITE);
        addStocksButton.addActionListener(e -> {
            AddStocksGUI addStocksGUI = new AddStocksGUI();
            addStocksGUI.showAddStocksGUI(frame);
        });

// Add the new button to the button panel
buttonPanel.add(addStocksButton);

        // Add buttons to the button panel
        buttonPanel.add(signUpAdminButton);
        buttonPanel.add(logInAdminButton);
        buttonPanel.add(signUpUserButton);
        buttonPanel.add(logInUserButton);
        buttonPanel.add(addStocksButton);

        // Bottom Panel for Exit Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(exitButton);

        // Add panels to the frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Helper method to create styled buttons
    private static JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        button.setBorder(new RoundedBorder(15)); // Rounded corners
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(10, 20, 10, 20)); // Increased padding

        return button;
    }

    // Custom class to define rounded button border
    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}

