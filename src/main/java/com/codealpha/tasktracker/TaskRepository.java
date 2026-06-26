package com.codealpha.tasktracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and saves the task list as JSON on disk, using Gson.
 */
public class TaskRepository {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Task>>() { }.getType();

    private final Path file;

    public TaskRepository(Path file) {
        this.file = file;
    }

    /** Read tasks from disk, or an empty list if the file does not exist yet. */
    public List<Task> load() throws IOException {
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        String json = Files.readString(file);
        List<Task> tasks = GSON.fromJson(json, LIST_TYPE);
        return tasks != null ? tasks : new ArrayList<>();
    }

    /** Write the task list to disk as pretty-printed JSON, creating parent dirs. */
    public void save(List<Task> tasks) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        Files.writeString(file, GSON.toJson(tasks, LIST_TYPE));
    }
}
