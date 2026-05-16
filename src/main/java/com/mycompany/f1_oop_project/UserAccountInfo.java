package com.mycompany.f1_oop_project;

import static com.mycompany.f1_oop_project.CommonCalls.userHandler;
import java.io.Serializable;
import java.util.Random;
import java.util.*;

public class UserAccountInfo extends PersonInfo implements Serializable, UniqueID {
    private static final long serialVersionUID = 2L;

    private String accountNumber;
    private double balance;
    private String gender;
    private Map<Stock, Integer> stockMap;

    // The FileHandler will be used to check for unique IDs

    public UserAccountInfo(String name, String email, String password, double balance, String gender) {
        super(name, email, password);
        this.balance = balance;// Initialize FileHandler to get access to existing IDs
        this.accountNumber = generateUniqueID(); // Generate a unique account number
        this.gender = gender;
        this.stockMap = new HashMap<>();

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String generateUniqueID() {
        String newAccountNumber;
        Random random = new Random();
        
        // Fetch existing IDs from FileHandler
        do {
            newAccountNumber = Integer.toHexString(random.nextInt(0xFFFFFFF));
            newAccountNumber = String.format("%7s", newAccountNumber).replace(' ', '0');
        } while (userHandler.getExistingIDs().contains(newAccountNumber));  // Check uniqueness using FileHandler's list
        
        return newAccountNumber.toUpperCase();
    }

    public String displayAccountInfo() {
        return "| Account Holder: " + getName()+"      |"+
                "\nAccount Number: " + accountNumber+" |"+
                "\nBalance: $" + balance +"       |";
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount); // Update the balance
        // Update the user data in the file
        userHandler.updateData(this, getAccountNumber()); // 'this' refers to the current object being updated
        System.out.println("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew: $" + amount);
            // Update the user data in the file
            userHandler.updateData(this, getAccountNumber()); // 'this' refers to the current object being updated
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }
    
    public void buyStock(Stock stock, int quantity) {
        double totalPrice = stock.getCurrentPrice() * quantity;
        if (withdraw(totalPrice)) {
            stockMap.put(stock, stockMap.getOrDefault(stock, 0) + quantity);
            System.out.println("Purchased " + quantity + " shares of " + stock.getStockName() + "\n New balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid quantity.");
        }
    }
    public void sellStock(Stock stock, int quantity) {
        if (stockMap.containsKey(stock) && stockMap.get(stock) >= quantity && quantity > 0) {
            double totalSalePrice = stock.getCurrentPrice() * quantity;
            stockMap.put(stock, stockMap.get(stock) - quantity);
            if (stockMap.get(stock) == 0) {
                stockMap.remove(stock);
            }
            deposit(totalSalePrice);
            System.out.println("Sold " + quantity + " shares of " + stock.getStockName() + ". New balance: $" + balance);
        } else {
            System.out.println("Insufficient shares or invalid quantity.");
        }
    }

    public void displayPortfolio() {
        System.out.println("Portfolio for account: " + accountNumber);
        for (Map.Entry<Stock, Integer> entry : stockMap.entrySet()) {
            System.out.println("Stock: " + entry.getKey().getStockName() + " | Quantity: " + entry.getValue() + " | Current Price: $" + entry.getKey().getCurrentPrice());
        }
        System.out.println("Total Balance: $" + balance);
    }

//    public boolean transfer(UserAccountInfo recipient, double amount) {
//        if (this.withdraw(amount)) { // Withdraw from the sender
//            recipient.deposit(amount); // Deposit to the recipient
//            System.out.println("Transferred: $" + amount + " to " + recipient.getName());
//            return true;
//        } else {
//            System.out.println("Transfer failed: Unable to withdraw from sender.");
//            return false;
//        }
//    }
}

