package uz.pdp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String pas = "123";
        String username = "postgres";
        try {
            return DriverManager.getConnection(url, username, pas);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
