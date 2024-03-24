package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {"school21.spring.service.repositories",
        "school21.spring.service.services"})
public class TestApplicationConfig {
    private final String DB_DRIVER_NAME = "org.hsqldb.jdbcDriver";
    private final String DB_URL = "jdbc:hsqldb:mem:testdb";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            "\"user\" (id integer identity primary key, email varchar(80), " +
            "password varchar(40));";

    @Bean(name = "hikari")
    public DataSource getHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DB_DRIVER_NAME);
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setConnectionInitSql(CREATE_TABLE);
        return dataSource;
    }

    @Bean(name = "driverManager")
    public DataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}