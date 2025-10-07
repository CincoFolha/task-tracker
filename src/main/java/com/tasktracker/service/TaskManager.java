package com.tasktracker.service;

import com.tasktracker.repository.FileRepository;
import com.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManager {
  private final List<Task> tasks = new ArrayList<>();

  public TaskManager() {
    String loadData = JSONPersistence.readFromJson();
    
    if (loadData.startsWith("[") && loadData.endsWith("]")) {
      loadData = loadData.substring(1, loadData.length() - 1);
    }

    String[] objects = loadData.split("},");
    if (!objects[0].equals("")) {
      for (int i = 0; i < objects.length; i++) {
        tasks.add(Task.parse(objects[i]));
      }
    }
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
    String json = tasks.stream()
      .map(task -> task.toJSONString())
      .collect(Collectors.joining(",", "[", "]"));
    
    JSONPersistence.saveToJson(json);
  }

  private Optional<Task> findTask(String id) {
    return tasks.stream().filter(task -> task.getId() == Integer.parseInt(id)).findFirst();
  }
}
