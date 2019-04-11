package sql;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillOperation {
    private static final Logger LOGGER = Logger.getLogger(SkillOperation.class.getName());
    private static final String SELECT_ID = "SELECT * FROM skills WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM skills";
    private static final String INSERT = "INSERT INTO developers(industry, level) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE developers SET industry = ?, level = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM skills WHERE id = ?";
    private Connection connection;

    public Skill selectById(int id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Skill skill = createSkill(resultSet);
            return skill;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
        }
        return null;
    }

    public List<Skill> selectAll() throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {
            assert connection != null;
            resultSet = statement.executeQuery(SELECT_ALL);
            List<Skill> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createSkill(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
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
        }
    }

    public void insert(Skill object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = Connector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getIndustry());
            preparedStatement.setString(2, object.getLevel());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    public void update(Skill object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = Connector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, object.getIndustry());
            preparedStatement.setString(2, object.getLevel());
            preparedStatement.setInt(3, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    private Skill createSkill(ResultSet resultSet) throws SQLException {
        return new Skill(resultSet.getInt("id"),
                resultSet.getString("industry"),
                resultSet.getString("level"));
    }

    @SneakyThrows
    public void closeConnection() {
        connection.close();
    }
}
