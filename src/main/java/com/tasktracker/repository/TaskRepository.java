package com.tasktracker.repository;

public interface TaskRepository {
  
  void save(List<Task> tasks);

  List<Task> load();

  boolean exists();

  void clear();

  int count();
}
