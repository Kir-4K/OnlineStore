package by.itacademy.utilDatabase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionPool {

    private static DataSource DATA_SOURCE;

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(PropertiesManager.get("db.driver"));
        poolProperties.setUrl(PropertiesManager.get("db.url"));
        poolProperties.setUsername(PropertiesManager.get("db.username"));
        poolProperties.setPassword(PropertiesManager.get("db.password"));
        poolProperties.setMaxActive(Integer.parseInt(PropertiesManager.get("db.pool.size")));
        DATA_SOURCE = new DataSource(poolProperties);
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
