package com.avramenko.reflection.annotation;

@Table(name = "Persons")
public class Person {

    Person(int id,
           String name,
           double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Id
    @Column(name = "person_id")
    int id;

    @Column(name = "person_name")
    String name;

    @Column
    double salary;
}
