package com.havrulyk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.String.format;

public class DatabaseConnectionManager {
    public static final String POSTGRESQL_URL_TEMPLATE = "jdbc:postgresql://%s/%s";

    private final String url;
    private final Properties properties;

    public DatabaseConnectionManager(String host, String databaseName, String username, String password) {
        this.url = format(POSTGRESQL_URL_TEMPLATE, host, databaseName);
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }
}
