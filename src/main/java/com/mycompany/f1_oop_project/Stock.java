package com.mycompany.f1_oop_project;

import static com.mycompany.f1_oop_project.CommonCalls.adminHandler;
import java.io.Serializable;
import java.util.Random;
// Stock class with multithreading
public class Stock extends CommonCalls implements Runnable, Serializable, UniqueID{
    
    private static final long serialVersionUID = 4L;
    
    private String stockID;
    private String stockName;
    private double currentPrice;
    private Random random;
    

    public Stock(String stockName, double initialPrice) {
        this.stockName = stockName;
        this.currentPrice = initialPrice;
        this.random = new Random();
        stockID = generateUniqueID();
    }

    public String getStockID() {
        return stockID;
    }
    

    public String getStockName() {
        return stockName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void displayProgress() {
        System.out.println("Stock: " + stockName + " | Current Price: $" + currentPrice);
    }
    
    @Override
    public String generateUniqueID() {
        String newID;
        do {
            newID = "STC" + ((int) (Math.random() * 10000));
        } while (stockHandler.getExistingIDs().contains(newID));  // Check uniqueness using FileHandler's list
        
        return newID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // Update price every 2 seconds
                synchronized (this) {
                    double change = (random.nextDouble() - 0.5) * 10; // Random change between -5 and +5
                    currentPrice = Math.max(1, currentPrice + change); // Ensure price does not go below 1
                }
            } catch (InterruptedException ex) {
                System.err.println("Stock update thread interrupted.");
            }
        }
    }

}

