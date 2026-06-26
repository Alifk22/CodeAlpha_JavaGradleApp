package com.codealpha.tasktracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRepositoryTest {

    @Test
    void returnsEmptyListWhenFileMissing(@TempDir Path dir) throws IOException {
        TaskRepository repo = new TaskRepository(dir.resolve("missing.json"));
        assertTrue(repo.load().isEmpty());
    }

    @Test
    void savesAndLoadsTasksRoundTrip(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("tasks.json");
        TaskRepository repo = new TaskRepository(file);

        TaskService service = new TaskService();
        service.add("write tests");
        Task second = service.add("run ci");
        service.complete(second.getId());

        repo.save(service.all());
        assertTrue(Files.exists(file));

        List<Task> loaded = repo.load();
        assertEquals(2, loaded.size());
        assertEquals("write tests", loaded.get(0).getTitle());
        assertTrue(loaded.get(1).isDone());
    }

    @Test
    void writesPrettyPrintedJson(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("tasks.json");
        TaskRepository repo = new TaskRepository(file);
        repo.save(List.of(new Task(1, "demo", false)));

        String json = Files.readString(file);
        assertTrue(json.contains("\"title\": \"demo\""));
        assertTrue(json.contains("\n"), "expected multi-line pretty JSON");
    }
}
