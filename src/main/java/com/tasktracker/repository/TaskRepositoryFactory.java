package com.tasktracker.repository;

public class TaskRepositoryFactory {
  public static TaskRepository createFileRepository() {
    return new FileRepository();
  }

  public static TaskRepository createFileRepository(String fileName) {
    return new FileRepository(fileName);
  }
}
