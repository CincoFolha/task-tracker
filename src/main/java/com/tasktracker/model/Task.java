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
    return String.format("{\"id\":\"%d\",\"description\":\"%s\"}", id, description);
  }

  @Override
  public String toString() {
    return String.format("[%d] %s", id, description);
  }
}
