package com.ccsu.cs530.successcentral.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private DataSource ds;
    private Connection con;
    private PreparedStatement pr;
    private InitialContext ic;

    public static Connection getConnection() {
        try {
            return ((DataSource) InitialContext.doLookup("java:/SCDB")).getConnection();
        } catch (Exception e) {
            System.out.println("Hey CCSU team. Could not connect to DB");
            return null;
        }
    }

    public void open() throws NamingException, SQLException {
        ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/SCDB");
        con = ds.getConnection();
    }

    public void close() throws SQLException {
        if (pr != null){
            pr.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public int executeUpdateCommand(String command) throws SQLException {
        pr = con.prepareStatement(command);
        int i = pr.executeUpdate();
        return i;
    }

    public ResultSet executeQueryCommand(String command) throws SQLException {
        pr = con.prepareStatement(command);
        ResultSet rs = pr.executeQuery();
        return rs;
    }

}
