import com.tasktracker.repository.JSONPersistence;
import com.tasktracker.service.TaskService;

public class TaskTracker {
  public static void main(String[] args) {
    if (args.length < 1 || args.length > 3) {
      System.out.println("Usage: java TaskTracker [command] [id_task]");
      return;
    }

    TaskService taskService = new TaskService();
    switch (args[0]) {
      case "add":
        taskService.addTask(args[1]);
        break;
      case "update":
        break;
      case "delete":
        break;
      case "mark-in-progress":
        break;
      case "mark-done":
        break;
      case "list":
        System.out.println("function list");
        break;
      default:
        System.out.println("Illegal argument");
    }   
    taskService.listTasks(); 
  }
}
