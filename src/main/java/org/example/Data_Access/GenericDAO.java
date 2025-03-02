package org.example.Data_Access;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class GenericDAO<T> {
    private final Connection connection;
    public GenericDAO(Connection connection) {
        this.connection = connection;
    }
    public void create(T object) throws SQLException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        String tableName = clazz.getSimpleName();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            columns.append(field.getName()).append(",");
            values.append("'").append(field.get(object)).append("',");
        }
        columns.deleteCharAt(columns.length() - 1);
        values.deleteCharAt(values.length() - 1);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }
    public void update(T object) throws SQLException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        String tableName = clazz.getSimpleName();

        StringBuilder setClause = new StringBuilder();
        String idColumn = null;
        Object idValue = null;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            if (fieldName.equalsIgnoreCase("id")) {
                idColumn = fieldName;
                idValue = fieldValue;
            } else {
                setClause.append(fieldName).append("='").append(fieldValue).append("',");
            }
        }
        setClause.deleteCharAt(setClause.length() - 1);

        if (idColumn == null || idValue == null) {
            throw new IllegalArgumentException("Object must have an 'id' field");
        }

        String query = String.format("UPDATE %s SET %s WHERE %s='%s'", tableName, setClause, idColumn, idValue);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    // Metoda pentru a șterge un obiect din baza de date
    public void delete(int id) throws SQLException {
        Class<T> clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        String tableName = clazz.getSimpleName();
        String query = String.format("DELETE FROM %s WHERE id=?", tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Metoda pentru a găsi un obiect în baza de date
    public T findById(int id, Class<T> clazz) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tableName = clazz.getSimpleName();
        String query = String.format("SELECT * FROM %s WHERE id=?", tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                T object = clazz.getDeclaredConstructor().newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    Method setter = findSetter(clazz, columnName);
                    if (setter != null) {
                        setter.invoke(object, value);
                    }
                }
                return object;
            }
        }
        return null;
    }
    private Method findSetter(Class<?> clazz, String fieldName) {
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(setterName)) {
                return method;
            }
        }
        return null;
    }
}
