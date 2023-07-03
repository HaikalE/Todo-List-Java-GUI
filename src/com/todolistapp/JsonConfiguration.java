/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.todolistapp;

/**
 *
 * @author LENOVO
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JsonConfiguration {
    private  JTable table;
    private  String filePath;

    public JsonConfiguration(JTable table, String filePath) {
        this.table = table;
        this.filePath = filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public JsonConfiguration() {
        // Default constructor
    }
    public void saveDataToJson() {
        // Convert the table data to a JSON array
        JsonArray jsonArray = convertTableDataToJsonArray();

        // Convert the JSON array to a formatted JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(jsonArray);

        // Save the JSON string to a file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonOutput);
            System.out.println("JSON data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void fillTableWithData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Date");

        List<Map<String, Object>> dataList = readDataFromJson();
        for (Map<String, Object> data : dataList) {
            String title = (String) data.get("Title");
            String date = (String) data.get("Date");
            model.addRow(new Object[]{title, date});
        }

        table.setModel(model);
    }
    private JsonArray convertTableDataToJsonArray() {
    JsonArray jsonArray = new JsonArray();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int rowCount = model.getRowCount();

    for (int i = 0; i < rowCount; i++) {
        JsonObject jsonObject = new JsonObject();
        for (int j = 0; j < model.getColumnCount(); j++) {
            String columnName = model.getColumnName(j);
            Object cellValue = model.getValueAt(i, j);
            // Check if cellValue is null and assign an empty string instead
            String value = (cellValue != null) ? cellValue.toString() : "";
            jsonObject.addProperty(columnName, value);
        }
        jsonArray.add(jsonObject);
    }

    return jsonArray;
}
    
    public List<Map<String, Object>> readDataFromJson() {
        List<Map<String, Object>> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    if (jsonObject.has("Title") && jsonObject.has("Date")) {
                        String title = jsonObject.get("Title").getAsString();
                        String date = jsonObject.get("Date").getAsString();
                        List<String> tasks = new ArrayList<>();
                        List<Boolean> checklist = new ArrayList<>();

                        if (jsonObject.has("Task") && jsonObject.has("Checklist")) {
                            JsonArray taskArray = jsonObject.getAsJsonArray("Task");
                            JsonArray checklistArray = jsonObject.getAsJsonArray("Checklist");

                            for (JsonElement taskElement : taskArray) {
                                tasks.add(taskElement.getAsString());
                            }

                            for (JsonElement checklistElement : checklistArray) {
                                checklist.add(checklistElement.getAsBoolean());
                            }
                        }

                        Map<String, Object> data = new HashMap<>();
                        data.put("Title", title);
                        data.put("Date", date);
                        data.put("Task", tasks);
                        data.put("Checklist", checklist);
                        dataList.add(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
}
    public void addTask(String title, String newTask) {
    // Read existing data from JSON file
    List<Map<String, Object>> dataList = readDataFromJson();

    // Find the entry with the specified title
    for (Map<String, Object> data : dataList) {
        String existingTitle = (String) data.get("Title");
        if (existingTitle.equals(title)) {
            // Update the existing entry with the new task information
            List<String> existingTasks = (List<String>) data.get("Task");
            List<Boolean> existingChecklist = (List<Boolean>) data.get("Checklist");

            if (existingTasks == null) {
                existingTasks = new ArrayList<>(); // Create a new list if it's null
                data.put("Task", existingTasks); // Update the data map with the new list
            }
            if (existingChecklist == null) {
                existingChecklist = new ArrayList<>(); // Create a new list if it's null
                data.put("Checklist", existingChecklist); // Update the data map with the new list
            }
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.addRow(new Object[]{newTask, existingChecklist}); // Replace checklist with existingChecklist
            existingTasks.add(newTask);
            existingChecklist.add(false); // Set the default value to false

            // Save the updated data to the JSON file
            saveDataToJsonFile(dataList);
            return;
        }
    }

    // If no matching entry is found, create a new entry
    Map<String, Object> newData = new HashMap<>();
    newData.put("Title", title);
    newData.put("Task", Arrays.asList(newTask)); // Create a new list with the new task
    newData.put("Date", ""); // Replace with the appropriate date
    newData.put("Checklist", Arrays.asList(false)); // Create a new list with the default value

    // Add the new data entry to the list
    dataList.add(newData);

    // Save the updated data to the JSON file
    saveDataToJsonFile(dataList);
}


    public void saveDataToJsonFile(List<Map<String, Object>> dataList) {
    // Convert the data list to a JSON array
    JsonArray jsonArray = new JsonArray();
    for (Map<String, Object> data : dataList) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List) {
                JsonArray jsonArrayValue = new JsonArray();
                for (Object element : (List<?>) value) {
                    jsonArrayValue.add(convertToJsonElement(element));
                }
                jsonObject.add(key, jsonArrayValue);
            } else {
                jsonObject.add(key, convertToJsonElement(value));
            }
        }
        jsonArray.add(jsonObject);
    }

    // Convert the JSON array to a formatted JSON string
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String jsonOutput = gson.toJson(jsonArray);

    // Save the JSON string to a file
    try (FileWriter fileWriter = new FileWriter(filePath)) {
        fileWriter.write(jsonOutput);
        System.out.println("JSON data saved successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private JsonElement convertToJsonElement(Object value) {
    Gson gson = new Gson();
    String jsonString = gson.toJson(value);
    return JsonParser.parseString(jsonString);
}



    public void addDataToJson(String title, String date) {
    // Read existing data from JSON file
    List<Map<String, Object>> dataList = readDataFromJson();

    // Find the entry with the specified title
    for (Map<String, Object> data : dataList) {
        String existingTitle = (String) data.get("Title");
        if (existingTitle.equals(title)) {
            // Update the existing entry with the new date information
            data.put("Date", date);

            // Save the updated data to the JSON file
            saveDataToJsonFile(dataList);
            return;
        }
    }

    // If no matching entry is found, create a new entry
    Map<String, Object> newData = new HashMap<>();
    newData.put("Title", title);
    newData.put("Task", new ArrayList<String>());
    newData.put("Date", date);
    newData.put("Checklist", new ArrayList<Boolean>());

    // Add the new data entry to the list
    dataList.add(newData);

    // Save the updated data to the JSON file
    saveDataToJsonFile(dataList);
}




    private JsonArray convertDataListToJsonArray(List<Map<String, Object>> dataList) {
    JsonArray jsonArray = new JsonArray();

    for (Map<String, Object> data : dataList) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.addProperty(key, value != null ? value.toString() : "");
        }
        jsonArray.add(jsonObject);
    }
    
    return jsonArray;
}
    
    public void updateJsonFile() {
    // Read existing data from JSON file
    List<Map<String, Object>> existingDataList = readDataFromJson();

    // Create a new list to hold the modified data
    List<Map<String, Object>> modifiedDataList = new ArrayList<>();

    // Iterate over each row in the table
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int rowCount = model.getRowCount();
    for (int row = 0; row < rowCount; row++) {
        // Create a new map for each row
        Map<String, Object> rowData = new HashMap<>();

        // Add the "Title" and "Date" fields from the table to the map
        rowData.put("Title", model.getValueAt(row, 0));
        rowData.put("Date", model.getValueAt(row, 1));

        // Add the modified map to the new data list
        modifiedDataList.add(rowData);
    }

    // Convert the modified data list to a JSON array
    JsonArray jsonArray = new Gson().toJsonTree(modifiedDataList).getAsJsonArray();

    // Add the modified data back to the existing JSON data list
    for (int i = 0; i < jsonArray.size(); i++) {
        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
        Map<String, Object> existingData = existingDataList.get(i);

        // Add the "Task" and "Checklist" fields from the existing data to the modified JSON object
        jsonObject.add("Task", new Gson().toJsonTree(existingData.get("Task")));
        jsonObject.add("Checklist", new Gson().toJsonTree(existingData.get("Checklist")));
    }

    // Convert the JSON array to a formatted JSON string
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String jsonOutput = gson.toJson(jsonArray);

    // Save the JSON string to the file
    try (FileWriter fileWriter = new FileWriter(filePath)) {
        fileWriter.write(jsonOutput);
        System.out.println("JSON file updated successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void refreshTableData() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear the existing table data

    List<Map<String, Object>> dataList = readDataFromJson();
    for (Map<String, Object> data : dataList) {
        Object[] rowData = {
                data.get("Title"),
                data.get("Date"),
                //"" // Checkbox column placeholder
        };
        model.addRow(rowData);
    }
}
    public void fillTableWithSpecificData(String title) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear existing rows

    List<Map<String, Object>> dataList = readDataFromJson();
    for (Map<String, Object> data : dataList) {
        String existingTitle = (String) data.get("Title");
        if (existingTitle.equals(title)) {
            List<String> tasks = (List<String>) data.get("Task");
            List<Boolean> checklist = (List<Boolean>) data.get("Checklist");

            // Add a new row to the table with task and checklist information
            if (tasks != null && checklist != null && tasks.size() == checklist.size()) {
                for (int i = 0; i < tasks.size(); i++) {
                    model.addRow(new Object[]{tasks.get(i), checklist.get(i)});
                }
            }
            break; // Stop iterating once the specific title is found
        }
    }
}
}