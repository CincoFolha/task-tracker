package com.tasktracker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    if (id <= 0) {
      throw new IllegalArgumentException("ID must be a positive integer");
    }
    this.id = id;
    idCounter.updateAndGet(current -> Math.max(current, id));
  }
 
  public void setDescription(String description) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    this.description = description;
    updateTimestamp();
  }

  public void setStatus(TaskStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    this.status = status;
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

  public String toJSON() {
    try {
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro serializing Task to JSON", e);
    }
  }

  public static Task fromJSON(String json) {
    try {
      return objectMapper.readValue(json, Task.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error deserializing Task from JSON", e);
    }
  }

  private static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  public static void resetIdCounter() {
    idCounter.set(0);
  }

  public static int getNextId() {
    return idCounter.get() + 1;
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
