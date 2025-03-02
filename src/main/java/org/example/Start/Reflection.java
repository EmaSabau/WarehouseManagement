package org.example.Start;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class Reflection {
    public static void generateTableFromList(List<?> list, DefaultTableModel tableModel) {
        if (list.isEmpty()) return;
        Object sampleObject = list.get(0);
        Field[] fields = sampleObject.getClass().getDeclaredFields();
        // Create column names array
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        tableModel.setColumnIdentifiers(columnNames);
        // Add data rows
        for (Object obj : list) {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    rowData[i] = fields[i].get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableModel.addRow(rowData);
        }
    }
}
