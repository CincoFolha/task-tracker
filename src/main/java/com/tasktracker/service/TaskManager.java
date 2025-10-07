package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.repository.TaskRepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskManager {

  private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

  private final List<Task> tasks;
  private final TaskRepository repository;

  public TaskManager() {
    this(TaskRepositoryFactory.createFileRepository());
  }

  public TaskManager(TaskRepository repository) {
    if (repository == null) {
      throw new IllegalArgumentException("Repository cannot be null");
    }
    this.repository = repository;
    this.tasks = new ArrayList<>(repository.load());
    LOGGER.log(Level.INFO, "TaskManager initialized with {0} task(s)", tasks.size());
  }

  public void addTask(String description) {
    Task newTask = new Task(description);
    tasks.add(newTask);

    LOGGER.log(Level.INFO, "Task added: ID={0}, Description={1}", new Object[]{newTask.getId(), description});
    System.out.println("Task added successfully (ID: %d)%n", newTask.getId());
  }

  public void updateTask(int id, String newDescription) {
    Task task = findTaskById(id)
      .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    
    String oldDescription = task.getDescription();
    task.setDescription(newDescription);

    LOGGER.log(Level.INFO, "Task {0} updated: ''{1}'' -> ''{2}''", new Object[]{id, oldDescription, newDescription});
    System.out.printf("Task %d updated successfully%n", id);
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

  private Optional<Task> findTaskById(int id) {
    return tasks.stream().filter(task -> task.getId() == id).findFirst();
  }
}
