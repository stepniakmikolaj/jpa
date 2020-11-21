package com.kodilla.jpa.domain;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "subtask_id")
    private Subtask subtask;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, Task task, Subtask subtask) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.task = task;
        this.subtask = subtask;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Task getTask() {
        return task;
    }

    public Subtask getSubtask() {
        return subtask;
    }
}
