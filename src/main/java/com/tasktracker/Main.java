package com.tasktracker;

import com.tasktracker.repository.JSONPersistence;
import com.tasktracker.service.TaskManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
  public static void main(String[] args) {
    if (args.length < 1 || args.length > 3) {
      System.out.println("Usage: ./gradlew run --args=\"[command] [id_task]\"");
      return;
    }
    
    TaskManager taskManager = new TaskManager();

    Map<String, Consumer<String[]>> commands = new HashMap<>();
    commands.put("add", (params) -> taskManager.addTask(params[1]));
    commands.put("update", (params) -> taskManager.updateTask(params[1], params[2]));
    commands.put("delete", (params) -> taskManager.removeTask(params[1]));
    commands.put("list", (params) -> taskManager.listTasks(params));
    commands.put("mark-in-progress", (params) -> taskManager.updateStatus(params[1], "IN-PROGRESS"));
    commands.put("mark-done", (params) -> taskManager.updateStatus(params[1], "DONE"));

    commands.getOrDefault(args[0], (params) -> System.out.println("Illegal argument")).accept(args);
    
    taskManager.exit();
    return;
  }
}
