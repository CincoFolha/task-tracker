package com.tasktracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Task {
  private static int idCounter = 0;
  private int id;
  private String description;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task(String description) {
    this.id = ++idCounter;
    this.description = description;
    this.status = "TODO";
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public String getCreatedAt() {
    return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public String getUpdatedAt() {
    return updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); 
  }

  public void setId(int newId) {
    this.id = newId;
  }
 
  public void setDescription(String newDescription) {
    this.description = newDescription;
    this.updatedAt = LocalDateTime.now();
  }

  public void setStatus(String newStatus) {
    this.status = newStatus;
    this.updatedAt = LocalDateTime.now();
  }

  public void setCreatedAt(LocalDateTime time) {
    this.createdAt = time;
  }

  public void setUpdatedAt(LocalDateTime time) {
    this.updatedAt = time;
  }

  public String toJSONString() {
    return String.format("{\"id\":\"%d\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}", 
        id, description, status, getCreatedAt(), getUpdatedAt());
  }

  public static Task parse(String json) {
    json = json.replace("{", "").replace("}", "").replace("\"", "");
    
    String[] keyValuePairs = json.split(",\\s*");
    Map<String, String> jsonMap = new HashMap<>();

    for (String pair : keyValuePairs) {
      String[] keyValue = pair.split(":\\s*", 2);
      if (keyValue.length == 2) {
        jsonMap.put(keyValue[0].trim(), keyValue[1].trim());
      }
    }

    int id = Integer.parseInt(jsonMap.get("id"));
    String description = jsonMap.get("description");
    String statusString = jsonMap.get("status");
    String createdAtStr = jsonMap.get("createdAt");
    String updatedAtStr = jsonMap.get("updatedAt");

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    Task task = new Task(description);
    task.setId(id);
    task.setStatus(statusString);
    task.setCreatedAt(LocalDateTime.parse(createdAtStr, formatter));
    task.setUpdatedAt(LocalDateTime.parse(updatedAtStr, formatter));

    idCounter = Math.max(idCounter, id);
    
    return task;
  }

  @Override
  public String toString() {
    return String.format("Id: %d, Description: %s, Status: %s, CreateAt: %s, UpdateAt: %s", 
        id, description, status, 
        createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 
        updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
  }
}
