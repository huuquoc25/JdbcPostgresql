import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgreSQLJDBC {
    private static PostgreSQLJDBC instance;
    private static HikariDataSource dataSource;

    // Khối static để khởi tạo DataSource
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/CRUD");
        config.setUsername("postgres");
        config.setPassword("huuquoc25");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    private PostgreSQLJDBC() {}

    public static synchronized PostgreSQLJDBC getInstance() {
        if (instance == null) {
            instance = new PostgreSQLJDBC();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Bắt đầu một giao dịch
    public static void beginTransaction(Connection con) throws SQLException {
        con.setAutoCommit(false); // Tắt chế độ tự động commit để bắt đầu transaction
    }

    // Commit giao dịch
    public static void commitTransaction(Connection con) throws SQLException {
        if (con != null) {
            con.commit(); // Commit các thay đổi
            con.setAutoCommit(true); // Bật lại chế độ tự động commit
        }
    }

    // Rollback giao dịch
    public static void rollbackTransaction(Connection con) throws SQLException {
        if (con != null) {
            con.rollback(); // Rollback các thay đổi nếu có lỗi xảy ra
            con.setAutoCommit(true); // Bật lại chế độ tự động commit
        }
    }

    public static void closeResources(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
