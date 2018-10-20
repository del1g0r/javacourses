package com.avramenko.reflection.annotation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryGeneratorTest {

    @Test
    public void testGetAll() {
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedSql = "SELECT person_id, person_name, salary FROM Persons;";

        String actualSql = queryGenerator.getAll(Person.class);

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void testInsert() throws IllegalAccessException {
        QueryGenerator queryGenerator = new QueryGenerator();
        Person person = new Person(1, "Bill", 1100);
        String expectedSql = "INSERT INTO Persons (person_id, person_name, salary) VALUES (1, 'Bill', 1100.0);";

        String actualSql = queryGenerator.insert(person);

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void testGetById() {
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedSql = "SELECT person_id, person_name, salary FROM Persons WHERE person_id = 100;";

        String actualSql = queryGenerator.getById(Person.class, 100);

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void testDelete() {
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedSql = "DELETE FROM Persons WHERE person_id = 100;";

        String actualSql = queryGenerator.delete(Person.class, 100);

        assertEquals(expectedSql, actualSql);
    }