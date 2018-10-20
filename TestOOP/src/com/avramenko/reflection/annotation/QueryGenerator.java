package com.avramenko.reflection.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class QueryGenerator {

    public String getAll(Class clazz) {
        Annotation classAnnotation = clazz.getAnnotation(Table.class);
        Table tableAnnotation = (Table) classAnnotation;

        if (tableAnnotation == null) {
            throw new IllegalArgumentException("@Table is not present");
        }

        String tableName = tableAnnotation.name().isEmpty() ?
                clazz.getName() : tableAnnotation.name();

        StringJoiner columns = new StringJoiner(", ");
        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ?
                        field.getName() : columnAnnotation.name();
                columns.add(columnName);
            }
        }

        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append(columns.toString());
        stringBuilder.append(" FROM ");
        stringBuilder.append(tableName);
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    public String insert(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);

        if (tableAnnotation == null) {
            throw new IllegalArgumentException("@Table is not present");
        }

        String tableName = tableAnnotation.name().isEmpty() ?
                clazz.getName() : tableAnnotation.name();

        // person_id, person_name, salary
        StringJoiner columns = new StringJoiner(", ", "(", ")");
        StringJoiner values = new StringJoiner(", ", "(", ")");
        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ?
                        field.getName() : columnAnnotation.name();
                columns.add(columnName);
                String value = field.get(object).toString();
                values.add(field.getType() == String.class
                        ? toQuotedString(value) : value);
            }
        }

        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(tableName);
        stringBuilder.append(' ');
        stringBuilder.append(columns.toString());
        stringBuilder.append(" VALUES ");
        stringBuilder.append(values.toString());
        stringBuilder.append(';');

        return stringBuilder.toString();
    }

    public String getById(Class clazz, Object id) {
        Annotation classAnnotation = clazz.getAnnotation(Table.class);
        Table tableAnnotation = (Table) classAnnotation;

        if (tableAnnotation == null) {
            throw new IllegalArgumentException("@Table is not present");
        }

        String tableName = tableAnnotation.name().isEmpty() ?
                clazz.getName() : tableAnnotation.name();

        StringJoiner columns = new StringJoiner(", ");
        String idName = null;
        String idValue = null;
        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                Id idAnnotation = field.getAnnotation(Id.class);
                String columnName = columnAnnotation.name().isEmpty() ?
                        field.getName() : columnAnnotation.name();
                columns.add(columnName);
                if (idAnnotation != null) {
                    if (idName != null) {
                        throw new IllegalArgumentException("The only one column could be ID");
                    }
                    idName = columnName;
                    idValue = field.getType() == String.class
                            ? toQuotedString(id.toString()) : id.toString();
                }
            }
        }

        if (idName == null) {
            throw new IllegalArgumentException("ID is not specified");
        }

        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append(columns.toString());
        stringBuilder.append(" FROM ");
        stringBuilder.append(tableName);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(idName);
        stringBuilder.append(" = ");
        stringBuilder.append(idValue);
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public String delete(Class clazz, Object id) {
        Annotation classAnnotation = clazz.getAnnotation(Table.class);
        Table tableAnnotation = (Table) classAnnotation;

        if (tableAnnotation == null) {
            throw new IllegalArgumentException("@Table is not present");
        }

        String tableName = tableAnnotation.name().isEmpty() ?
                clazz.getName() : tableAnnotation.name();

        String idName = null;
        String idValue = null;
        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                Id idAnnotation = field.getAnnotation(Id.class);
                String columnName = columnAnnotation.name().isEmpty() ?
                        field.getName() : columnAnnotation.name();
                if (idAnnotation != null) {
                    if (idName != null) {
                        throw new IllegalArgumentException("The only one column could be ID");
                    }
                    idName = columnName;
                    idValue = field.getType() == String.class
                            ? toQuotedString(id.toString()) : id.toString();
                }
            }
        }

        if (idName == null) {
            throw new IllegalArgumentException("ID is not specified");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(tableName);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(idName);
        stringBuilder.append(" = ");
        stringBuilder.append(idValue);
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    private String toQuotedString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\'');
        stringBuilder.append(str);
        stringBuilder.append('\'');
        return stringBuilder.toString();

    }
}