package com.tasktracker.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONPersistence {
  private static final String FILE_PATH = "listTask.json";

  public static String readFromJson() {
    try {
      File jsonFile = new File(FILE_PATH);
      jsonFile.createNewFile();
      return new String(Files.readAllBytes(jsonFile.toPath()));
    } catch (IOException e) {
      return "[]";
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
