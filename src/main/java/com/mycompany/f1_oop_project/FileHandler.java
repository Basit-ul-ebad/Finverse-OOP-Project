package com.mycompany.f1_oop_project;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler<T> {
    private String fileName;
    private List<String> existingIDs = new ArrayList<>(Arrays.asList("0"));

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void saveData(T data) {
        List<T> dataList = loadAllData(); // Load existing data
        dataList.add(data); // Add new data

        writeDataToFile(dataList); // Save updated list to file
        System.out.println("Data saved successfully.");
    }

    public List<T> loadAllData() {
        
        List<T> dataList = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                T data = (T) in.readObject();
                dataList.add(data);
            }
        } catch (EOFException ex) {
            // End of file reached
            System.out.println("All data loaded from file.");
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println("Error reading from file: " + fileName);
            ex.printStackTrace();
        }

        return dataList;
    }

    public void updateData(T updatedData, String identifier) {
        // Load existing data
        List<T> dataList = loadAllData();

        boolean found = false;
        for (int i = 0; i < dataList.size(); i++) {
            System.out.println("size: "+dataList.size());
            T data = dataList.get(i);

            // Check if the identifier matches
            if (data instanceof UserAccountInfo && identifier.equals(((UserAccountInfo) data).getAccountNumber())) {
                dataList.set(i, updatedData); // Replace the old data with the updated data
                found = true;
                break;
            } else if (data instanceof Admin && identifier.equals(((Admin) data).getAdminID())) {
                dataList.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (found) {
            writeDataToFile(dataList); // Save updated data back to the file
            System.out.println("Data updated successfully.");
        } else {
            System.out.println("No matching data found to update.");
        }
    }

    public void writeDataToFile(List<T> dataList) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName, false))) {
            // Overwrite file by setting append to false
            for (T item : dataList) {
                out.writeObject(item); // Write each object individually
            }
        } catch (IOException ex) {
            System.err.println("Error writing to file: " + fileName);
            ex.printStackTrace();
        }
    }

    private void loadExistingIDs() {
        List<T> allData = loadAllData();
        for (T data : allData) {
            if (data instanceof UserAccountInfo) {
                existingIDs.add(((UserAccountInfo) data).getAccountNumber());
            } else if (data instanceof Admin) {
                existingIDs.add(((Admin) data).getAdminID());
            } else if ( data instanceof Stock ){
                existingIDs.add(((Stock) data).getStockID());
            }
        }
    }

    public List<String> getExistingIDs() {
        return existingIDs;
    }

}
