package sql;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Connector {
    private Connector() {
    }

    private static final Logger LOGGER = Logger.getLogger(Connector.class);

    public static Connection getConnection() {
        Properties property = new Properties();
        Connection connection = null;
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/database/properties");
            property.load(fis);
            connection = DriverManager.getConnection(property.getProperty("url"),
                    property.getProperty("user"), property.getProperty("password"));
        } catch (SQLException | IOException e) {
            LOGGER.error(e);
        }
        return connection;
    }
}
