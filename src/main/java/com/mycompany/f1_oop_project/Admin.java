package com.mycompany.f1_oop_project;

import java.io.Serializable;
import java.util.*;


public class Admin extends PersonInfo implements Serializable, UniqueID {
    
    private static final long serialVersionUID = 3L;

    private String adminID;
    private String gender;
    private List<UserAccountInfo> user;
    
    public Admin(String name, String email, String password, String gender) {
        super(name, email, password);
        this.user = new ArrayList<>();
        this.adminID = generateUniqueID();
        this.gender=gender;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
    
    public List<UserAccountInfo> fetchAllUsers(){
        user=userHandler.loadAllData();
        return user;
    }
    
    
    public String DisplayAllUsers(){
        fetchAllUsers();
        String s="";
        if (user.isEmpty()) {
            s += "No users to display.";
        }   else {
            System.out.println("User Details:");
            for (UserAccountInfo userInfo : user) {
                s += userInfo.displayAccountInfo(); // Calls the toString() method of UserAccountInfo
                s +="\n----------------------------------------------\n";
            }
        }
        return s;
    }
    // Search user by account number
    public UserAccountInfo searchUserByAccountNumber(String accountNumber) {
        fetchAllUsers();
        for (UserAccountInfo userInfo : user) {
            if (userInfo.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return userInfo;
            }
        }
        return null; // User not found
    }

    // Delete a user
    public boolean deleteUser(String accountNumber) {
        fetchAllUsers();
        Iterator<UserAccountInfo> iterator = user.iterator();
        while (iterator.hasNext()) {
            UserAccountInfo userInfo = iterator.next();
            if (userInfo.getAccountNumber().equals(accountNumber)) {
                iterator.remove(); // Remove the user
                userHandler.writeDataToFile(user); // Save the updated list
                System.out.println("User with account number " + accountNumber + " has been deleted.");
                return true;
            }
        }
        System.out.println("User with account number " + accountNumber + " not found.");
        return false;
    }

    // Display total balance across all accounts
    public double displayTotalBalance() {
        fetchAllUsers();
        double totalBalance = 0;
        for (UserAccountInfo userInfo : user) {
            totalBalance += userInfo.getBalance();
        }
        System.out.println("Total balance across all user accounts: $" + totalBalance);
        return totalBalance;
    }

    // Generate account summary
    public String generateAccountSummary() {
        fetchAllUsers();
        StringBuilder summary = new StringBuilder();
        summary.append("Account Summary Report\n");
        summary.append("=================================\n");
        for (UserAccountInfo userInfo : user) {
            summary.append(userInfo.getName()).append(" - $").append(userInfo.getBalance()).append("\n");
        }
        summary.append("=================================\n");
        summary.append("Total Users: ").append(user.size()).append("\n");
        summary.append("Total Balance: $").append(displayTotalBalance()).append("\n");
        return summary.toString();
    }
    

    @Override
    public String generateUniqueID() {
        String newID;
        do {
            newID = "ADM" + ((int) (Math.random() * 10000));
        } while (adminHandler.getExistingIDs().contains(newID));  // Check uniqueness using FileHandler's list
        
        return newID;
    }

    public void displayAdminInfo() {
        System.out.println("Admin ID: " + adminID);
    }

}


   



    /*
    
       // New functionality for stock management
    private ArrayList<Stock> stocks = new ArrayList<>();

    public void addStock(String stockName, double initialPrice) {
        Stock stock = new Stock(stockName, initialPrice);
        stocks.add(stock);
        new Thread(stock).start();
        System.out.println("Stock added: " + stockName);
    }

    public void buyStock(UserAccountInfo user, String stockName, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getStockName().equalsIgnoreCase(stockName)) {
                double totalPrice = stock.getCurrentPrice() * quantity;
                if (user.getBalance() >= totalPrice) {
                    user.setBalance(user.getBalance() - totalPrice);
                    stock.buyShares(user, quantity);
                    System.out.println("User " + user.getName() + " bought " + quantity + " shares of " + stockName);
                } else {
                    System.out.println("Insufficient balance to buy stock.");
                }
                return;
            }
        }
        System.out.println("Stock not found.");
    }

    public void sellStock(UserAccountInfo user, String stockName, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getStockName().equalsIgnoreCase(stockName)) {
                if (stock.sellShares(user, quantity)) {
                    double totalEarnings = stock.getCurrentPrice() * quantity;
                    user.setBalance(user.getBalance() + totalEarnings);
                    System.out.println("User " + user.getName() + " sold " + quantity + " shares of " + stockName);
                } else {
                    System.out.println("User does not own enough shares to sell.");
                }
                return;
            }
        }
        System.out.println("Stock not found.");
    }

    public void displayStockProgress() {
        System.out.println("\n--- Stock Market Progress ---");
        for (Stock stock : stocks) {
            stock.displayProgress();
        }
    }*/


