package database;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Connect {

    Connection connection;
    private String url;
    private String driver;
    private String username;
    private String password;
    private String dbname;

    public Connection Conn() {
        connection = null;
        url = "jdbc:mysql://localhost:3306/";
        driver = "com.mysql.jdbc.Driver";
        username = "root";
        password = "";
        dbname = "mydb";
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url + dbname, username, password);
            System.out.println("Connection Successful");


        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return connection;
    }

    public ResultSet getQuery(String query) {
        ResultSet r = null;
        try {
            connection = Conn();

            Statement s = Conn().createStatement();
            r = s.executeQuery(query);

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);

            JOptionPane.showMessageDialog(null, ex);
        }
        return r;
    }

    public boolean setQuery(String query) {

        try {
            connection = Conn();
         
            Statement s = Conn().createStatement();
            s.execute(query);
           
            return true; // query sucessfull
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            System.out.println(ex);
            return false; // query failed
        } finally {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex);
                System.out.println(ex);
            }

        }

    }

    public int setQuery(String query, String column_name) {
        int i = 0;
        try {
            connection = Conn();

            Statement stmt = Conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet res = stmt.getGeneratedKeys();
            while (res.next()) {
                System.out.println("Generated key: " + res.getInt(1));
                i = res.getInt(1);

            }
            return i;

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);

            return i;
        }

    }

     

       
}
