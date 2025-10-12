package com.example.multi_env_jdbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final DbProperties dbProperties;
    private final JdbcTemplate jdbcTemplate;

    public StartupRunner(DbProperties dbProperties, JdbcTemplate jdbcTemplate) {
        this.dbProperties = dbProperties;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Active DB type: " + dbProperties.getType());
        System.out.println("Using JDBC URL: " + dbProperties.buildJdbcUrl());

        if ("h2".equalsIgnoreCase(dbProperties.getType())) {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS INFO_TABLE (" +
                    "id INT PRIMARY KEY, " +
                    "info VARCHAR(255))");

            jdbcTemplate.execute("MERGE INTO INFO_TABLE (id, info) KEY(id) " +
                    "VALUES (1, 'Initialized by StartupRunner')");
        }

        jdbcTemplate.query("SELECT * FROM INFO_TABLE", rs -> {
            System.out.println("Row: id=" + rs.getInt("id") + ", info=" + rs.getString("info"));
        });
    }
}
