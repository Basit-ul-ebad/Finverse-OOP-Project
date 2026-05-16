package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends CommonCalls {

    private int role;

    public Login(int role) {
        this.role = role;
    }

    public void loginWithGUI(JFrame parentFrame) {
        // Create a new frame for login
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setSize(450, 400);
        loginFrame.setLayout(new BorderLayout()); // Using BorderLayout to add title panel and login panel
        loginFrame.setLocationRelativeTo(null); // Center frame on screen

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 140, 190)); // Set background color
        JLabel titleLabel = new JLabel("Welcome to the FinTech System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        // Adding Title Panel at the top
        loginFrame.add(titlePanel, BorderLayout.NORTH);

        // Main Panel for login form
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE); // Set the background of the login panel

        // GridBagConstraints for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing around components

        // Create components for email and password
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        // Create buttons without icons
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        // Set Tooltips
        emailField.setToolTipText("Enter your email address");
        passwordField.setToolTipText("Enter your password");

        // Set Font and Colors for text fields
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBackground(new Color(245, 245, 245));

        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(245, 245, 245));

        // Layout the components in login panel
        gbc.gridx = 0; // Position on x-axis (columns)
        gbc.gridy = 0; // Position on y-axis (rows)
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Add Login and Cancel buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span both columns
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(cancelButton, gbc);

        // Add login panel to frame
        loginFrame.add(loginPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (role == 1) {
                    UserHandler userHandler = new UserHandler();
                    UserAccountInfo user = findUserByEmailAndPassword(email, password, userHandler);
                    if (user != null) {
                        JOptionPane.showMessageDialog(loginFrame, "Login successful! Welcome, " + user.getName());
                        userMenu.displayMenuWithGui(user);
                        loginFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (role == 2) {
                    AdminHandler adminHandler = new AdminHandler();
                    Admin admin = findAdminByEmailAndPassword(email, password, adminHandler);
                    if (admin != null) {
                        JOptionPane.showMessageDialog(loginFrame, "Login successful! Welcome, Admin " + admin.getName());
                        adminMenu.displayMenuWithGui(admin);
                        loginFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid role.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
            }
        });

        // Show login frame
        loginFrame.setVisible(true);
    }

    // Utility methods for finding users and admins
    private static UserAccountInfo findUserByEmailAndPassword(String email, String password, UserHandler userHandler) {
        List<UserAccountInfo> users = userHandler.loadAllData();
        for (UserAccountInfo user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static Admin findAdminByEmailAndPassword(String email, String password, AdminHandler adminHandler) {
        List<Admin> admins = adminHandler.loadAllData();
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }
}
