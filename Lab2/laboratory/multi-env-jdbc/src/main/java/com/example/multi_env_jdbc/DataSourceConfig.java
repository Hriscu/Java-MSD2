package com.example.multi_env_jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DbProperties props;

    @Bean
    @Profile("dev")
    @ConditionalOnExpression("'${app.datasource.type}' == 'h2'")
    public DataSource h2DataSource() {
        String url = props.buildJdbcUrl();
        return DataSourceBuilder.create()
                .url(url)
                .username(props.getUsername())
                .password(props.getPassword())
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    @Profile("prod")
    @ConditionalOnExpression("'${app.datasource.type}' == 'postgres'")
    public DataSource postgresDataSource() {
        String url = props.buildJdbcUrl();
        return DataSourceBuilder.create()
                .url(url)
                .username(props.getUsername())
                .password(props.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
