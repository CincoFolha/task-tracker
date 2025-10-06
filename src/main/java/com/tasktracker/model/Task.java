package com.tasktracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class Task {

  private static final AtomicInteger idCounter = new AtomicInteger(0);
  private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final ObjectMapper objectMapper = createObjectMapper();
  
  private int id;
  private String description;
  private TaskStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task() {
  }

  public Task(String description) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.id = idCounter.incrementAndGet();
    this.description = description;
    this.status = TaskStatus.TODO;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public String getCreatedAt() {
    return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public String getUpdatedAt() {
    return updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); 
  }

  public void setId(int id) {
    this.id = id;
  }
 
  public void setDescription(String newDescription) {
    this.description = newDescription;
    updateTimestamp();
  }

  public void setStatus(TaskStatus newStatus) {
    this.status = newStatus;
    updateTimestamp();
  }

  public void setCreatedAt(LocalDateTime time) {
    this.createdAt = time;
  }

  public void setUpdatedAt(LocalDateTime time) {
    this.updatedAt = time;
  }

  private void updateTimestamp() {
    this.updatedAt = LocalDateTime.now();
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

    Task task = new Task(description);
    task.setId(id);
    task.setStatus(TaskStatus.fromString(statusString));
    task.setCreatedAt(LocalDateTime.parse(createdAtStr, ISO_FORMATTER));
    task.setUpdatedAt(LocalDateTime.parse(updatedAtStr, ISO_FORMATTER));

    idCounter.updateAndGet(currentId -> Math.max(currentId, id));
    
    return task;
  }

  @Override
  public String toString() {
    return String.format("Id: %d, Description: %s, Status: %s, CreateAt: %s, UpdateAt: %s", 
        id, description, status, 
        createdAt.format(DISPLAY_FORMATTER), 
        updatedAt.format(DISPLAY_FORMATTER));
  }

  public enum TaskStatus {
    TODO("TODO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String value;

    TaskStatus(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static TaskStatus fromString(String value) {
      for (TaskStatus status : TaskStatus.values()) {
        if (status.name().equalsIgnoreCase(value)) {
          return status;
        }
      }
      return null;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
