package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"edu.school21.sockets.repositories",
        "edu.school21.sockets.services",
        "edu.school21.sockets.server"})
public class SocketsApplicationConfig {
    @Value("${db.driver.name}")
    private String driverName;

    @Value("${db.url}")
    private String jdbcUrl;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean(name = "hikariDataSource")
    DataSource createHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
