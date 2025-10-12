package com.example.multi_env_jdbc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInfoContributor implements InfoContributor {

    private final DbProperties props;

    public DatabaseInfoContributor(DbProperties props) {
        this.props = props;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> db = new HashMap<>();
        db.put("type", props.getType());
        db.put("url", props.buildJdbcUrl());
        db.put("user", props.getUsername());
        db.put("host", props.getHost());
        db.put("port", props.getPort());
        db.put("name", props.getName());
        builder.withDetail("database", db);
    }
}
