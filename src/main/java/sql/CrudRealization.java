package sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudRealization {
    private static final Logger LOGGER = Logger.getLogger(CrudRealization.class);

    public void create(String tableName, String values) {
        PreparedStatement preparedStatement;
        String query;
        String parameters;
        switch (tableName) {
            case "developers":
                parameters = "name, age, sex, salary";
                break;
            case "skills":
                parameters = "language, level";
                break;
            case "projects":
                parameters = "project_name, date, cost";
                break;
            case "customers":
                parameters = "name";
                break;
            case "companies":
                parameters = "name";
                break;
            default:
                throw new IllegalArgumentException();
        }

        query = "INSERT INTO ?(?) VALUES(?);";

        try (Connection connection = Connector.getConnection()) {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, parameters);
            preparedStatement.setString(3, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
    }

    public void read(String tableName) throws SQLException {
        Connection connection = Connector.getConnection();
        ResultSet resultSet;
        String query = "SELECT * FROM " + tableName;
        Statement statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();

        for (int i = 1; i <= columnsNumber; i++) {
            LOGGER.info(resultSetMetaData.getColumnName(i) + " ");
        }

        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                LOGGER.info(resultSet.getString(i) + " ");
            }
        }
        connection.close();
        statement.close();
        resultSet.close();
    }

    public void update(String tableName, String column, String newValue, String condition) {
        String query = "UPDATE " + tableName + " SET " + column + " = " + newValue + " WHERE " + condition;

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
    }

    public void delete(String tableName, String condition) {
        String query = "DELETE FROM " + tableName + " WHERE " + condition;

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
    }
}
