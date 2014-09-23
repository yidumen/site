package com.yidumen.web.framework;

import com.alibaba.appengine.api.ds.DataSourceFactory;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

/**
 *
 * @author 蔡迪旻
 */
public class ACEConnectionProvider implements ConnectionProvider {

    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DataSourceFactory.getDataSource("local").getConnection("yidumen", "yidumen");
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

}
