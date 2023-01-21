package com.example.todoapp.entity;

public class ToDoItem {
    private Long id;

    private String description;

    private boolean done;

    public ToDoItem(Long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public ToDoItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
