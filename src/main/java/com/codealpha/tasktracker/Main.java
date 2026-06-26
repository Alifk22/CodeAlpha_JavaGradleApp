package com.codealpha.tasktracker;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Command-line entry point for the task tracker.
 *
 * <pre>
 *   add &lt;title...&gt;   add a new task
 *   list             list all tasks
 *   done &lt;id&gt;        mark a task complete
 *   clear            remove completed tasks
 * </pre>
 *
 * Tasks persist to {@code ~/.codealpha-tasks.json} between runs.
 */
public final class Main {

    private static final Path DATA_FILE =
            Paths.get(System.getProperty("user.home"), ".codealpha-tasks.json");

    private Main() {
    }

    public static void main(String[] args) {
        try {
            run(args, new TaskRepository(DATA_FILE));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not read or write the task file: " + e.getMessage());
            System.exit(1);
        }
    }

    /** Dispatch a single command against the given repository. */
    static void run(String[] args, TaskRepository repo) throws IOException {
        TaskService service = new TaskService(repo.load());
        String command = args.length > 0 ? args[0].toLowerCase() : "help";

        switch (command) {
            case "add":
                String title = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                Task created = service.add(title);
                repo.save(service.all());
                System.out.println("Added " + created);
                break;

            case "done":
                if (args.length < 2 || !StringUtils.isNumeric(args[1])) {
                    System.out.println("Usage: done <id>");
                    return;
                }
                int id = Integer.parseInt(args[1]);
                if (service.complete(id)) {
                    repo.save(service.all());
                    System.out.println("Completed task #" + id);
                } else {
                    System.out.println("No task with id " + id);
                }
                break;

            case "clear":
                int removed = service.clearCompleted();
                repo.save(service.all());
                System.out.println("Removed " + removed + " completed task(s)");
                break;

            case "list":
                printTasks(service.all());
                break;

            default:
                printHelp();
        }
    }

    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet. Add one with: add <title>");
            return;
        }
        System.out.println("Tasks:");
        for (Task t : tasks) {
            System.out.println("  " + t);
        }
    }

    private static void printHelp() {
        System.out.println("CodeAlpha Task Tracker");
        System.out.println("Commands:");
        System.out.println("  add <title>   add a new task");
        System.out.println("  list          list all tasks");
        System.out.println("  done <id>     mark a task complete");
        System.out.println("  clear         remove completed tasks");
    }
}
