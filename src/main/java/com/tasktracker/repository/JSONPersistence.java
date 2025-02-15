package com.tasktracker.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONPersistence {
  private static final String FILE_PATH = "data/task_data.json";

  public static String readFromJson() {
    try {
      return new String(Files.readAllBytes(Paths.get(FILE_PATH)));
    } catch (IOException e) {
      e.printStackTrace();
      return "{\"taskList\":[]}";
    }
  }
  
  public static void saveToJson(String data) {
    System.out.println(data);
    try {
      Files.write(Paths.get(FILE_PATH), data.getBytes());
      System.out.println("JSON file saved successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
