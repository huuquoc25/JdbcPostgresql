import java.sql.*;

public class PostgreSQLJDBC {
    public Connection connection;
    public static Connection con() {
        // Thông tin kết nối
        String url = "jdbc:postgresql://localhost:5432/CRUD"; // Thay 'yourDatabase' bằng tên database của bạn
        String user = "postgres"; // Thay 'yourUsername' bằng tên người dùng của bạn
        String password = "huuquoc25"; // Thay 'yourPassword' bằng mật khẩu của bạn

        // Kết nối đến cơ sở dữ liệu
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con; // Trả về kết nối
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
