package com.tasktracker.repository;

import com.tasktracker.model.Task;
import java.util.List;

public interface TaskRepository {
  
  void save(List<Task> tasks);

  List<Task> load();

  boolean exists();

  void clear();

  int count();
}
