package com.mycompany.f1_oop_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends CommonCalls {

    private int role;

    // Constructor to initialize the role
    public Signup(int role) {
        this.role = role;
    }

    public void signupWithGUI(JFrame parentFrame) {
        // Create signup dialog
        JDialog signupDialog = new JDialog(parentFrame, "Signup", true);
        signupDialog.setSize(400, 500); // Adjusted height for new components
        signupDialog.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 140, 190));  // Color as per your request
        JLabel titleLabel = new JLabel("Signup to the System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titlePanel.add(titleLabel);
        signupDialog.add(titlePanel, BorderLayout.NORTH);

        // Center Panel for form
        JPanel formPanel = new JPanel(new GridBagLayout());  // Use GridBagLayout for better control
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill the width

        // Create components
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel balanceLabel = new JLabel("Initial Balance:");
        JTextField balanceField = new JTextField(20);
        JLabel genderLabel = new JLabel("Gender:");

        // Radio buttons for gender
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton otherButton = new JRadioButton("Rather not say");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        JButton signupButton = new JButton("Signup");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel with GridBagConstraints
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; formPanel.add(passwordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(genderLabel, gbc);
        gbc.gridx = 1; formPanel.add(genderPanel, gbc);

        if (role == 1) {
            gbc.gridx = 0; gbc.gridy = 4; formPanel.add(balanceLabel, gbc);
            gbc.gridx = 1; formPanel.add(balanceField, gbc);
        }

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(signupButton, gbc);
        gbc.gridx = 1; formPanel.add(cancelButton, gbc);

        // Add formPanel to the center of the dialog
        signupDialog.add(formPanel, BorderLayout.CENTER);

        // Action listener for signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "Rather not say";

                if (role == 1) {
                    try {
                        double balance = Double.parseDouble(balanceField.getText());
                        UserAccountInfo user = new UserAccountInfo(name, email, password, balance, gender);
                        userHandler.saveData(user);
                        JOptionPane.showMessageDialog(signupDialog, "User account created successfully.");
                        signupDialog.dispose();
                        userMenu.displayMenuWithGui(user);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(signupDialog, "Invalid balance. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (role == 2) {
                    Admin admin = new Admin(name, email, password, gender);
                    adminHandler.saveData(admin);
                    JOptionPane.showMessageDialog(signupDialog, "Admin account created successfully.");
                    signupDialog.dispose();
                    adminMenu.displayMenuWithGui(admin);
                } else {
                    JOptionPane.showMessageDialog(signupDialog, "Invalid role. Signup failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupDialog.dispose();
            }
        });

        signupDialog.setLocationRelativeTo(parentFrame);
        signupDialog.setVisible(true);
    }
}
