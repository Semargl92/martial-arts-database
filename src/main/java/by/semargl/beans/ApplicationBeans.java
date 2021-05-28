package by.semargl.beans;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
public class ApplicationBeans {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
        //select * from users where user id = ?
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
        // select * from users where user id = :userId
    }

    @Bean
    public DataSource hikariDataSource(by.semargl.beans.DatabaseProperties databaseProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl(databaseProperties.getUrl());
        hikariDataSource.setUsername(databaseProperties.getLogin());
        hikariDataSource.setPassword(databaseProperties.getPassword());
        hikariDataSource.setDriverClassName(databaseProperties.getDriverName());
        hikariDataSource.setMaximumPoolSize(10);

        return hikariDataSource;
    }
}
