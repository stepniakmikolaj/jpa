package com.kodilla.jpa.domain;

import com.kodilla.jpa.enums.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Status status;
    @OneToMany(targetEntity = Person.class, mappedBy = "task")
    private Set<Person> persons = new HashSet<>();
    @OneToMany(targetEntity = Subtask.class, mappedBy = "task")
    private Set<Subtask> subtasks = new HashSet<>();

    public Task() {
    }

    public Task(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Set<Subtask> getSubtasks() {
        return subtasks;
    }
}
