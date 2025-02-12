package com.tasktracker.model;

public class Task {
  private static int idCounter = 0;
  private int id;
  private String description;

  public Task(String decription) {
    this.id = ++idCounter;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "[" + id + "] " + description;
  }
}
