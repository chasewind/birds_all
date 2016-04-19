package com.bird.tx;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TxManagerHelper {

    public static void main(String[] args) {
        new TxManagerHelper().txCall(new TxCallableEvent<String>() {

            @Override
            public String execute() throws RuntimeException {
                return "system";
            }
        });
    }

    public <T> T txCall(TxCallableEvent<T> callableEvent) {
        DataSource dataSource = new DataSource() {

            @Override
            public PrintWriter getLogWriter() throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void setLogWriter(PrintWriter out) throws SQLException {
                // TODO Auto-generated method stub

            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {
                // TODO Auto-generated method stub

            }

            @Override
            public int getLoginTimeout() throws SQLException {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Connection getConnection() throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }

        };
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        def.setReadOnly(false);
        TransactionStatus tranStatus = transactionManager.getTransaction(def);
        try {
            // do something
            T t = callableEvent.execute();
            return t;
        } catch (Exception e) {
            tranStatus.setRollbackOnly();
        } finally {
            if (tranStatus.isRollbackOnly()) {
                transactionManager.rollback(tranStatus);
            } else {
                transactionManager.commit(tranStatus);
            }
        }
        return null;
    }
}
