package com.tasktracker.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class JSONPersistence {

  private static final String FILE_PATH = "listTask.json";

  public static String readFromJson() {
    Path path = Paths.get(FILE_PATH);
    try {
      if (!Files.exists(path)) {
        Files.createFile(path);
        return "[]";
      }

      String content = Files.readString(path, StandardCharsets.UTF_8);
      return content.isBlank() ? "[]" : content;
    } catch (IOException e) {
      e.printStackTrace();
      return "[]";
    }
  }
  
  public static void saveToJson(String data) {
    try {
      Files.write(Paths.get(FILE_PATH), data.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
