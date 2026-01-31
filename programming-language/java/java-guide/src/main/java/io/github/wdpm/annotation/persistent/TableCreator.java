package io.github.wdpm.annotation.persistent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用反射的运行时注解处理，被处理的注解必须为@Retention(RetentionPolicy.RUNTIME)。
 *
 * <p>
 * usage: args = io.github.wdpm.annotation.persistent.Member
 *
 * @author evan
 * @date 2020/5/2
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("0 arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> cl      = Class.forName(className);
            DBTable  dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                System.out.println("No DBTable annotations in class " + className);
                continue;
            }

            // handle tableName
            String tableName = dbTable.name();
            // If the name is empty, use the Class name:
            if (tableName.length() < 1)
                tableName = cl.getName().toUpperCase();

            // handle field annotation
            List<String> columnDefs = new ArrayList<>();
            for (Field field : cl.getDeclaredFields()) {
                String       columnName;
                Annotation[] anns       = field.getDeclaredAnnotations();
                if (anns.length < 1)
                    continue; // Not a db table column

                // warning: here use anns[0] to check for simplicity of presentation
                // better practice: for loop anns array
                if (anns[0] instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) anns[0];
                    // Use field name if name not specified
                    if (sInt.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sInt.name();
                    columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
                }
                if (anns[0] instanceof SQLString) {
                    SQLString sString = (SQLString) anns[0];
                    // Use field name if name not specified.
                    if (sString.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sString.name();
                    columnDefs.add(columnName + " VARCHAR(" +
                            sString.value() + ")" +
                            getConstraints(sString.constraints()));
                }
            }

            // concat final SQL
            StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");;
            for (String columnDef : columnDefs)
                createCommand.append("\n ").append(columnDef).append(",");
            // Remove trailing comma
            String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";

            System.out.println("Table Creation SQL for " + className + " is:\n" + tableCreate);
        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
