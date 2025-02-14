package com.tasktracker.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONPersistence {

  public static String loadFromFile(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "{}";
    }
  }

}
