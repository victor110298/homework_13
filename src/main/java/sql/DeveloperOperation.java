package sql;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeveloperOperation {
    private String pathToTheQuery = "src/main/resources/queries/";
    private static final Logger LOGGER = Logger.getLogger(DeveloperOperation.class.getName());
    private static final String SELECT_ID = "SELECT * FROM developers WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM developers";
    private static final String INSERT = "INSERT INTO developers(name, age, gender, salary) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE developers SET name = ?, age = ?, gender = ?, salary = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM developers WHERE id = ?";
    private Connection connection;

    public Developer selectById(int id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Developer developer = createDeveloper(resultSet);
            return developer;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
            closeConnection();
        }
        return null;
    }

    public List<Developer> selectAll() throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {
            assert connection != null;
            resultSet = statement.executeQuery(SELECT_ALL);
            List<Developer> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createDeveloper(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
            closeConnection();
        }
        return null;
    }

    public void deleteById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = Connector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
            closeConnection();
        }
    }

    public void insert(Developer object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = Connector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getAge());
            preparedStatement.setString(3, object.getGender());
            preparedStatement.setDouble(4, object.getSalary());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
            closeConnection();

        }
    }

    public void update(Developer object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = Connector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getAge());
            preparedStatement.setString(3, object.getGender());
            preparedStatement.setDouble(4, object.getSalary());
            preparedStatement.setInt(5, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
            closeConnection();
        }
    }

    public List<Developer> getlistOfDevelopersByProject(String projectName) throws SQLException {
        return selectAllByCondition(projectName, pathToTheQuery + "listOfDevelopersByProject.sql");
    }

    public List<Developer> getListOfDevelopersBySkillLevel(String skillLevel) throws SQLException {
        return selectAllByCondition(skillLevel, pathToTheQuery + "listOfMiddleDevelopers.sql");
    }

    public List<Developer> getListOfDevelopersByIndustry(String branchDevelopment) throws SQLException {
        return selectAllByCondition(branchDevelopment, pathToTheQuery + "listOfJavaDevelopers.sql");
    }

    public double getSalaryOfDevelopersByProject(String projectName) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            String sql = null;
            try {
                sql = new Scanner(new File(pathToTheQuery + "salaryOfDevelopersBySeparateProject.sql"))
                        .useDelimiter("\\A").next();
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            }
            preparedStatement.setString(1, projectName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            double salary = resultSet.getDouble(1);
            return salary;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
            closeConnection();
        }
        return 0;
    }

    private List<Developer> selectAllByCondition(String conditionalField, String pathToSql) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            String sql = null;
            try {
                sql = new Scanner(new File(pathToSql)).useDelimiter("\\A").next();
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            }
            preparedStatement.setString(1, conditionalField);
            resultSet = preparedStatement.executeQuery();

            List<Developer> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createDeveloper(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
            closeConnection();
        }
        return null;
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException {
        return new Developer(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("gender"),
                resultSet.getDouble("salary"));
    }

    @SneakyThrows
    public void closeConnection() {
        connection.close();
    }
}
