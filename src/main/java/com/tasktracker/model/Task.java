package com.tasktracker.model;

public class Task {
  private static int idCounter = 0;
  private int id;
  private String description;

  public Task(String description) {
    this.id = ++idCounter;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  public String toJSONString() {
    StringBuilder jsonString = new StringBuilder();
    jsonString.append("{\"id\":\"");
    jsonString.append(id);
    jsonString.append("\",\"description\":\"");
    jsonString.append(description);
    jsonString.append("\"}");
    return jsonString.toString();
  }

  @Override
  public String toString() {
    return "[" + id + "] " + description;
  }
}
