package com.tasktracker.service;

import com.tasktracker.repository.TaskRepositoryFactory;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManager {
  private final List<Task> tasks;
  private final TaskRepository repository;

  public TaskManager() {
    this.repository = TaskRepositoryFactory.createFileRepository();
    this.tasks = repository.load();
  }

  public void addTask(String description) {
    Task newTask = new Task(description);
    tasks.add(newTask);
    System.out.println("Task added successfully: (ID: " + newTask.getId() + ")");
  }

  public void updateTask(String id, String newDescription) {
    Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    task.setDescription(newDescription);
  }
  
  public void removeTask(String id) {
    tasks.removeIf(task -> task.getId() == Integer.parseInt(id));
  }

  public void updateStatus(String id, String newStatus) {
    Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    task.setStatus(Task.TaskStatus.valueOf(newStatus));
  }
  
  public void listTasks(String[] params) {
    if (params.length < 2) {
      tasks.forEach(System.out::println);
      return;
    }
    String filterStatusString = params[1].toUpperCase();
    Task.TaskStatus filterStatus = Task.TaskStatus.fromString(filterStatusString);

    tasks.stream()
      .filter(task -> task.getStatus().name().equalsIgnoreCase(filterStatusString))
      .forEach(System.out::println);
  }


  public void exit() { 
    repository.save(tasks);
  }

  private Optional<Task> findTask(String id) {
    return tasks.stream().filter(task -> task.getId() == Integer.parseInt(id)).findFirst();
  }
}
