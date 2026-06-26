package com.codealpha.tasktracker;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In-memory task operations. Pure logic with no I/O, so it is fully unit-testable.
 */
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public TaskService() {
    }

    /** Rebuild a service from previously stored tasks, continuing the id sequence. */
    public TaskService(List<Task> existing) {
        if (existing != null) {
            tasks.addAll(existing);
            nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        }
    }

    /** Add a new task. The title must not be blank. */
    public Task add(String title) {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Task title must not be blank");
        }
        Task task = new Task(nextId++, title.trim(), false);
        tasks.add(task);
        return task;
    }

    /** Mark a task complete. Returns true if a matching task existed. */
    public boolean complete(int id) {
        Optional<Task> match = tasks.stream().filter(t -> t.getId() == id).findFirst();
        match.ifPresent(t -> t.setDone(true));
        return match.isPresent();
    }

    /** Remove a task by id. Returns true if one was removed. */
    public boolean remove(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    /** Remove all completed tasks and return how many were removed. */
    public int clearCompleted() {
        int before = tasks.size();
        tasks.removeIf(Task::isDone);
        return before - tasks.size();
    }

    public List<Task> all() {
        return Collections.unmodifiableList(new ArrayList<>(tasks));
    }

    public List<Task> pending() {
        return tasks.stream().filter(t -> !t.isDone()).collect(Collectors.toList());
    }

    public List<Task> completed() {
        return tasks.stream().filter(Task::isDone).collect(Collectors.toList());
    }

    public int count() {
        return tasks.size();
    }
}
