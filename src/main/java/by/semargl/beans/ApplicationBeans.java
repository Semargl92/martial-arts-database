package by.semargl.beans;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

//@Configuration
public class ApplicationBeans {

   /* @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource hikariDataSource(DatabaseProperties databaseProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl(databaseProperties.getUrl());
        hikariDataSource.setUsername(databaseProperties.getLogin());
        hikariDataSource.setPassword(databaseProperties.getPassword());
        hikariDataSource.setDriverClassName(databaseProperties.getDriverName());
        hikariDataSource.setMaximumPoolSize(10);

        return hikariDataSource;
    }*/
}
