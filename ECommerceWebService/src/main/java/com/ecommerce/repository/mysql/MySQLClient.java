package com.ecommerce.repository.mysql;

import com.ecommerce.model.config.ConfigModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ngoclt2
 */
public class MySQLClient {

    private String _host;
    private int _port;
    private String _user;
    private String _password;
    private String _dbName;
    private String _url;
    private int _maxActive;
    private int _maxIdle;
    private int _connectionTimeout;
    private int _idleTimeout;
    private int _maxLifetime;

    private HikariDataSource _dataSource;

    public MySQLClient() {
        _init();
    }

    private void _init() {
        _host = ConfigModel.INSTANCE.getString(MySQLClient.class.getSimpleName(), "host", "");
        _port = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "port", 0);
        _dbName = ConfigModel.INSTANCE.getString(MySQLClient.class.getSimpleName(), "dbname", "");
        _user = ConfigModel.INSTANCE.getString(MySQLClient.class.getSimpleName(), "user", "");
        _password = ConfigModel.INSTANCE.getString(MySQLClient.class.getSimpleName(), "password", "");
        _url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&user=%s&password=%s",
                _host, _port, _dbName, _user, _password);
        _maxActive = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "maxActive", 20);
        _maxIdle = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "maxIdle", 20);
        _connectionTimeout = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "connectionTimeout", 5000);
        _idleTimeout = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "idleTimeout", 300000);
        _maxLifetime = ConfigModel.INSTANCE.getInt(MySQLClient.class.getSimpleName(), "maxLifetime", 900000);
        setUpPooledDataSource();
    }

    private void setUpPooledDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(_url);
        config.setUsername(_user);
        config.setPassword(_password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // Pool configuration
        config.setConnectionTimeout(_connectionTimeout);
        config.setIdleTimeout(_idleTimeout);
        config.setMaxLifetime(_maxLifetime);
        config.setMaximumPoolSize(_maxActive);
        _dataSource = new HikariDataSource(config);

    }

    private HikariPool getPool() {
        try {
            Field field = _dataSource.getClass().getDeclaredField("pool");
            field.setAccessible(true);
            return (HikariPool) field.get(_dataSource);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return _dataSource.getConnection();
        } catch (SQLException ex) {

        }
        return null;
    }

    public void releaseConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
        }
    }

    public void releaseConnection(Connection conn, PreparedStatement stmt) {
        safeClose(stmt);
        safeClose(conn);
    }

    public void releaseConnection(Connection conn, ResultSet rs) {
        safeClose(rs);
        safeClose(conn);
    }

    public void releaseConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        safeClose(rs);
        safeClose(stmt);
        safeClose(conn);
    }

    private void safeClose(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }

    private void safeClose(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
        }
    }

    private void safeClose(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
        }
    }
}
