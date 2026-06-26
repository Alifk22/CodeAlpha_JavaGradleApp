package com.codealpha.tasktracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void addsTasksWithIncrementingIds() {
        TaskService service = new TaskService();
        Task a = service.add("first");
        Task b = service.add("second");

        assertEquals(1, a.getId());
        assertEquals(2, b.getId());
        assertEquals(2, service.count());
        assertFalse(a.isDone());
    }

    @Test
    void rejectsBlankTitle() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.add("   "));
        assertThrows(IllegalArgumentException.class, () -> service.add(null));
        assertEquals(0, service.count());
    }

    @Test
    void trimsTitleWhitespace() {
        TaskService service = new TaskService();
        Task t = service.add("  buy milk  ");
        assertEquals("buy milk", t.getTitle());
    }

    @Test
    void completesExistingTaskOnly() {
        TaskService service = new TaskService();
        Task t = service.add("ship it");

        assertTrue(service.complete(t.getId()));
        assertFalse(service.complete(999));
        assertTrue(service.all().get(0).isDone());
    }

    @Test
    void separatesPendingAndCompleted() {
        TaskService service = new TaskService();
        service.add("a");
        Task b = service.add("b");
        service.complete(b.getId());

        assertEquals(1, service.pending().size());
        assertEquals(1, service.completed().size());
        assertEquals("a", service.pending().get(0).getTitle());
    }

    @Test
    void clearsCompletedTasks() {
        TaskService service = new TaskService();
        Task a = service.add("a");
        service.add("b");
        service.complete(a.getId());

        int removed = service.clearCompleted();
        assertEquals(1, removed);
        assertEquals(1, service.count());
    }

    @Test
    void continuesIdSequenceFromExistingTasks() {
        TaskService service = new TaskService(List.of(new Task(5, "old", false)));
        Task t = service.add("new");
        assertEquals(6, t.getId());
    }

    @Test
    void removesTaskById() {
        TaskService service = new TaskService();
        Task t = service.add("temp");
        assertTrue(service.remove(t.getId()));
        assertFalse(service.remove(t.getId()));
        assertEquals(0, service.count());
    }
}
