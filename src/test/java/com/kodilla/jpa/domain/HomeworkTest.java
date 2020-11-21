package com.kodilla.jpa.domain;

import com.kodilla.jpa.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class HomeworkTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private List<Long> insertExampleData() {
        Task t1 = new Task(null, "task1", Status.RED);
        Subtask st1_1 = new Subtask(null, "subtask1_1", Status.RED, t1);
        Subtask st1_2 = new Subtask(null, "subtask1_2", Status.YELLOW, t1);
        Subtask st1_3 = new Subtask(null, "subtask1_3", Status.YELLOW, t1);

        Person p1 = new Person(null, "fistName1", "LastName1", t1, st1_1);
        Person p2 = new Person(null, "fistName2", "LastName2", t1, st1_2);
        Person p3 = new Person(null, "fistName3", "LastName3", t1, st1_3);
        Person p4 = new Person(null, "fistName4", "LastName4", t1, st1_1);

        t1.getSubtasks().addAll(Arrays.asList(st1_1, st1_2, st1_3));
        t1.getPersons().addAll(Arrays.asList(p1, p2, p3, p4));
        st1_1.getPersons().addAll(Arrays.asList(p1, p4));
        st1_2.getPersons().addAll(Arrays.asList(p2));
        st1_3.getPersons().addAll(Arrays.asList(p3));

        Task t2 = new Task(null, "task2", Status.YELLOW);
        Subtask st2_1 = new Subtask(null, "subtask2_1", Status.RED, t2);
        Subtask st2_2 = new Subtask(null, "subtask2_2", Status.GREEN, t2);
        Subtask st2_3 = new Subtask(null, "subtask3_3", Status.RED, t2);

        Person p5 = new Person(null, "fistName5", "LastName5", t2, st2_1);
        Person p6 = new Person(null, "fistName6", "LastName6", t2, st2_2);
        Person p7 = new Person(null, "fistName7", "LastName7", t2, st2_3);
        Person p8 = new Person(null, "fistName8", "LastName8", t2, st2_1);

        t2.getSubtasks().addAll(Arrays.asList(st2_1, st2_2, st2_3));
        t2.getPersons().addAll(Arrays.asList(p5, p6, p7, p8));
        st2_1.getPersons().addAll(Arrays.asList(p5, p8));
        st2_2.getPersons().addAll(Arrays.asList(p6));
        st2_3.getPersons().addAll(Arrays.asList(p7));


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        entityManager.persist(p4);
        entityManager.persist(p5);
        entityManager.persist(p6);
        entityManager.persist(p7);
        entityManager.persist(p8);

        entityManager.persist(t1);
        entityManager.persist(t2);

        entityManager.persist(st1_1);
        entityManager.persist(st1_2);
        entityManager.persist(st1_3);
        entityManager.persist(st2_1);
        entityManager.persist(st2_2);
        entityManager.persist(st2_3);

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

        return Arrays.asList(t1.getId(), t2.getId());
    }

    @Test
    void homeworkTestOld() {
        List<Long> savesTask = insertExampleData();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for invoices ***");

        List<Task> tasks =
                entityManager.createQuery(
                        "from Task "
                                + " where id in (" + taskIds(savesTask) + ")",
                        Task.class).getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            System.out.println("*** STEP 3 – read the name ***");
            System.out.println(task.getName());

            System.out.println("*** STEP 4 – read the status ***");
            System.out.println(task.getStatus());

            System.out.println("*** STEP 5 – read persons ***");

            for (Person person : task.getPersons()) {
                System.out.println("*** STEP 6 – read the person ***");
                System.out.println(person);
                System.out.println("*** STEP 7 – read first name ***");
                System.out.println(person.getFirstName());
                System.out.println("*** STEP 8 – read last name ***");
                System.out.println(person.getLastName());
            }

            System.out.println("*** STEP 9 – read subtasks ***");
            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("*** STEP 10 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 11 – read the subtask name ***");
                System.out.println(subtask.getName());

                System.out.println("*** STEP 12 – read the subtask status ***");
                System.out.println(subtask.getStatus());
                System.out.println("*** STEP 13 – read subtask persons ***");
                for (Person person : subtask.getPersons()) {
                    System.out.println("*** STEP 14 – read the person ***");
                    System.out.println(person);
                    System.out.println("*** STEP 15 – read first name ***");
                    System.out.println(person.getFirstName());
                    System.out.println("*** STEP 16 – read last name ***");
                    System.out.println(person.getLastName());
                }
            }
        }
        System.out.println("****************** END OF FETCHING *******************");
    }

    @Test
    void homeworkTestNew() {
        List<Long> savesTask = insertExampleData();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for invoices ***");

        TypedQuery<Task> query =
                entityManager.createQuery(
                        "from Task "
                                + " where id in (" + taskIds(savesTask) + ")",
                        Task.class);

        EntityGraph<Task> entityGraph = entityManager.createEntityGraph(Task.class);
        entityGraph.addSubgraph("subtasks").addSubgraph("persons");
        entityGraph.addSubgraph("persons").addAttributeNodes("firstName", "lastName");
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        List<Task> tasks = query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            System.out.println("*** STEP 3 – read the name ***");
            System.out.println(task.getName());

            System.out.println("*** STEP 4 – read the status ***");
            System.out.println(task.getStatus());

            System.out.println("*** STEP 5 – read persons ***");

            for (Person person : task.getPersons()) {
                System.out.println("*** STEP 6 – read the person ***");
                System.out.println(person);
                System.out.println("*** STEP 7 – read first name ***");
                System.out.println(person.getFirstName());
                System.out.println("*** STEP 8 – read last name ***");
                System.out.println(person.getLastName());
            }

            System.out.println("*** STEP 9 – read subtasks ***");
            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("*** STEP 10 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 11 – read the subtask name ***");
                System.out.println(subtask.getName());

                System.out.println("*** STEP 12 – read the subtask status ***");
                System.out.println(subtask.getStatus());
                System.out.println("*** STEP 13 – read subtask persons ***");
                for (Person person : subtask.getPersons()) {
                    System.out.println("*** STEP 14 – read the person ***");
                    System.out.println(person);
                    System.out.println("*** STEP 15 – read first name ***");
                    System.out.println(person.getFirstName());
                    System.out.println("*** STEP 16 – read last name ***");
                    System.out.println(person.getLastName());
                }
            }
        }
        System.out.println("****************** END OF FETCHING *******************");
    }

    private String taskIds(List<Long> taskIds) {
        return taskIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }
}
