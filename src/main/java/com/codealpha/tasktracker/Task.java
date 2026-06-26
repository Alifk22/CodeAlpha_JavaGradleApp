package com.codealpha.tasktracker;

import java.util.Objects;

/**
 * A single to-do item. Plain data object, also used as the JSON shape on disk.
 */
public class Task {

    private int id;
    private String title;
    private boolean done;

    /** No-args constructor required by Gson for deserialization. */
    public Task() {
    }

    public Task(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id && done == task.done && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, done);
    }

    @Override
    public String toString() {
        return String.format("[%s] #%d %s", done ? "x" : " ", id, title);
    }
}
