package org.example.repository;

import org.example.repository.dbconstants.PersontableConstant;
import org.example.repository.dbconstants.UsertableConstants;

import java.util.Map;

public class SQLHelper {
    public static final String ID = "id";

    private static final String INSERT_QUERY = "INSERT INTO ";
    private static final String USER_TABLE = "users";
    private static final String PERSON_TABLE = "persons";

    public static final String SQL_GET_PERSONS = "SELECT * FROM " + PERSON_TABLE;

    public static final String SQL_INSERT_PERSON =
            "INSERT INTO " + PERSON_TABLE +
                    " (" + PersontableConstant.NAME.getFieldName() + ", " +
                    PersontableConstant.PHONE.getFieldName() + ", " +
                    PersontableConstant.EMAIL.getFieldName() + ") " +
                    "VALUES (?, ?, ?)";

    public static final String SQL_GET_USER =
            "SELECT " +
                    UsertableConstants.ID.getFieldName() + ", " +
                    UsertableConstants.LOGIN.getFieldName() + ", " +
                    UsertableConstants.PASSWORD.getFieldName() +
                    " FROM " + USER_TABLE +
                    " WHERE " + UsertableConstants.LOGIN.getFieldName() + " = ?" +
                    " AND " + UsertableConstants.PASSWORD.getFieldName() + " = ?";

    public static final String SQL_CHECK_LOGIN =
            "SELECT " + UsertableConstants.LOGIN.getFieldName() +
                    " FROM " + USER_TABLE +
                    " WHERE " + UsertableConstants.LOGIN.getFieldName() + " = ?";

    public static final String SQL_INSERT_USER =
            "INSERT INTO " + USER_TABLE +
                    " (" + UsertableConstants.LOGIN.getFieldName() + ", " +
                    UsertableConstants.PASSWORD.getFieldName() + ") " +
                    "VALUES (?, ?)";

    public static String makeInsertQuery(Map<String, Object> fields, String table) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String column = entry.getKey();
            if (ID.equals(column)) {
                continue;
            }

            if (columns.length() > 0) {
                columns.append(", ");
                values.append(", ");
            }

            columns.append(column);
            values.append("?");
        }

        return INSERT_QUERY + table + " (" + columns + ") VALUES (" + values + ")";
    }
}