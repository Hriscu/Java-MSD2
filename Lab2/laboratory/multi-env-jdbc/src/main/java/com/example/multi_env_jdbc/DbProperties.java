package com.example.multi_env_jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.datasource")
public class DbProperties {

    private String type;
    private String host;
    private Integer port;
    private String name;
    private String username;
    private String password;
    private String url;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String buildJdbcUrl() {
        if (url != null && !url.isEmpty()) {
            return url;
        }
        if ("h2".equalsIgnoreCase(type)) {
            return "jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
        } else if ("postgres".equalsIgnoreCase(type)) {
            return "jdbc:postgresql://" + host + ":" + port + "/" + name;
        } else {
            throw new IllegalArgumentException("Unsupported DB type: " + type);
        }
    }

}
