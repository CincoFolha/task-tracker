import com.tasktracker.repository.JSONPersistence;
import com.tasktracker.service.TaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TaskTracker {
  public static void main(String[] args) {
    if (args.length < 1 || args.length > 3) {
      System.out.println("Usage: java TaskTracker [command] [id_task]");
      return;
    }
    
    System.out.println("begin");
    TaskService taskService = new TaskService();

    Map<String, Consumer<String[]>> commands = new HashMap<>();
    commands.put("add", (params) -> taskService.addTask(params[1]));
    commands.put("update", (params) -> taskService.updateTask(params[1], params[2]));
    commands.put("delete", (params) -> taskService.removeTask(params[1]));
    commands.put("list", (params) -> taskService.listTasks());
    commands.put("mark-in-progress", (params) -> System.out.println("in progress"));
    commands.put("mark-done", (params) -> System.out.println("in progress"));

    commands.getOrDefault(args[0], (params) -> System.out.println("Illegal argument")).accept(args);
    
    System.out.println("exit");
    taskService.exit();
    return;
  }
}
