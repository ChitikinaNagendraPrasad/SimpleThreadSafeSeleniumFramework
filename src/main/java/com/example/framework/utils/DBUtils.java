package com.example.framework.utils;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Embedded H2 database (in-memory) for demo purposes.
 * In real projects, you'd connect to your environment DB/replica with secure creds.
 */
public final class DBUtils {
    private DBUtils() {}

    private static JdbcConnectionPool pool;

    public static synchronized void init() {
        if (pool != null) return;
        pool = JdbcConnectionPool.create("jdbc:h2:mem:bankingdemo;DB_CLOSE_DELAY=-1", "sa", "");

        try (Connection con = pool.getConnection();
             Statement st = con.createStatement()) {

            st.execute("CREATE TABLE IF NOT EXISTS login_data (username VARCHAR(50), password VARCHAR(50), expected VARCHAR(120))");
            st.execute("DELETE FROM login_data");

            // Demo data
            st.execute("INSERT INTO login_data VALUES('tomsmith','SuperSecretPassword!','success')");
            st.execute("INSERT INTO login_data VALUES('tomsmith','wrong','invalid')");
            st.execute("INSERT INTO login_data VALUES('wrong','SuperSecretPassword!','invalid')");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to init H2 DB", e);
        }
    }

    public static Object[][] fetchLoginData() {
        init();
        List<Object[]> rows = new ArrayList<>();

        String sql = "SELECT username, password, expected FROM login_data";
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rows.add(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
            return rows.toArray(new Object[0][]);
        } catch (SQLException e) {
            throw new RuntimeException("DB query failed", e);
        }
    }
}
